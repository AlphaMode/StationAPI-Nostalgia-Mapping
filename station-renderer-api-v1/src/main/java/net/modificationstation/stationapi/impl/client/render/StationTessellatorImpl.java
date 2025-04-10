package net.modificationstation.stationapi.impl.client.render;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.GlAllocationUtils;
import net.modificationstation.stationapi.api.client.render.StationTessellator;
import net.modificationstation.stationapi.mixin.render.client.TessellatorAccessor;
import org.joml.Vector4f;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static net.modificationstation.stationapi.impl.client.render.RendererManager.LOGGER;

public class StationTessellatorImpl implements StationTessellator {

    private final Tessellator self;
    private final TessellatorAccessor access;
    private final int[] fastVertexData = new int[32];
    private final Vector4f damageUV = new Vector4f();

    public StationTessellatorImpl(Tessellator tessellator) {
        self = tessellator;
        access = (TessellatorAccessor) tessellator;
    }

    @Override
    public void ensureBufferCapacity(int criticalCapacity) {
        if (access.stationapi$getBufferPosition() >= access.stationapi$getBufferSize() - criticalCapacity) {
            LOGGER.info("Tessellator is nearing its maximum capacity. Increasing the buffer size from {} to {}", access.stationapi$getBufferSize(), access.stationapi$getBufferSize() * 2);
            access.stationapi$setBufferSize(access.stationapi$getBufferSize() * 2);
            access.stationapi$setBuffer(Arrays.copyOf(access.stationapi$getBuffer(), access.stationapi$getBufferSize()));
            ByteBuffer newBuffer = GlAllocationUtils.allocateByteBuffer(access.stationapi$getBufferSize() * 4);
            access.stationapi$setByteBuffer(newBuffer);
            access.stationapi$setIntBuffer(newBuffer.asIntBuffer());
            access.stationapi$setFloatBuffer(newBuffer.asFloatBuffer());
        }
    }
}
