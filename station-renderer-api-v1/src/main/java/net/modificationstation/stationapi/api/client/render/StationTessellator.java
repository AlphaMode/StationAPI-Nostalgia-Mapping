package net.modificationstation.stationapi.api.client.render;

import net.modificationstation.stationapi.api.util.Util;

public interface StationTessellator {

    default void ensureBufferCapacity(int criticalCapacity) {
        Util.assertImpl();
    }
}
