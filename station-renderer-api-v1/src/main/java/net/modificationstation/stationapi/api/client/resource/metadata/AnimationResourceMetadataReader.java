package net.modificationstation.stationapi.api.client.resource.metadata;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.resource.metadata.ResourceMetadataReader;
import net.modificationstation.stationapi.api.util.JsonHelper;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

@Environment(value=EnvType.CLIENT)
public class AnimationResourceMetadataReader
implements ResourceMetadataReader<AnimationResourceMetadata> {
    @Override
    public AnimationResourceMetadata fromJson(JsonObject jsonObject) {
        List<AnimationFrameResourceMetadata> frames = new ArrayList<>();
        int frametime = JsonHelper.getInt(jsonObject, "frametime", 1);
        if (frametime != 1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, frametime, "Invalid default frame time");
        }
        if (jsonObject.has("frames")) {
            try {
                JsonArray frameElements = JsonHelper.getArray(jsonObject, "frames");
                for (int i = 0; i < frameElements.size(); ++i) {
                    JsonElement frameElement = frameElements.get(i);
                    AnimationFrameResourceMetadata frame = this.readFrameMetadata(i, frameElement);
                    if (frame == null) continue;
                    frames.add(frame);
                }
            }
            catch (ClassCastException classCastException) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonObject.get("frames"), classCastException);
            }
        }
        int width = JsonHelper.getInt(jsonObject, "width", -1);
        int height = JsonHelper.getInt(jsonObject, "height", -1);
        if (width != -1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, width, "Invalid width");
        }
        if (height != -1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, height, "Invalid height");
        }
        boolean interpolate = JsonHelper.getBoolean(jsonObject, "interpolate", false);
        return new AnimationResourceMetadata(frames, width, height, frametime, interpolate);
    }

    private AnimationFrameResourceMetadata readFrameMetadata(int frame, JsonElement json) {
        if (json.isJsonPrimitive()) {
            return new AnimationFrameResourceMetadata(JsonHelper.asInt(json, "frames[" + frame + "]"));
        }
        if (json.isJsonObject()) {
            JsonObject jsonObject = JsonHelper.asObject(json, "frames[" + frame + "]");
            int time = JsonHelper.getInt(jsonObject, "time", -1);
            if (jsonObject.has("time")) {
                Validate.inclusiveBetween(1L, Integer.MAX_VALUE, time, "Invalid frame time");
            }
            int index = JsonHelper.getInt(jsonObject, "index");
            Validate.inclusiveBetween(0L, Integer.MAX_VALUE, index, "Invalid frame index");
            return new AnimationFrameResourceMetadata(index, time);
        }
        return null;
    }

    @Override
    public String getKey() {
        return "animation";
    }
}
