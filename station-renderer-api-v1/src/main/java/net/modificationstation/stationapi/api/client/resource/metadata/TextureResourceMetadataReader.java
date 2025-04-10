package net.modificationstation.stationapi.api.client.resource.metadata;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.resource.metadata.ResourceMetadataReader;
import net.modificationstation.stationapi.api.util.JsonHelper;

@Environment(EnvType.CLIENT)
public class TextureResourceMetadataReader implements ResourceMetadataReader<TextureResourceMetadata> {
   public TextureResourceMetadata fromJson(JsonObject jsonObject) {
      boolean blur = JsonHelper.getBoolean(jsonObject, "blur", false);
      boolean clamp = JsonHelper.getBoolean(jsonObject, "clamp", false);
      return new TextureResourceMetadata(blur, clamp);
   }

   public String getKey() {
      return "texture";
   }
}
