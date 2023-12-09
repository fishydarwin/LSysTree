package org.fishydarwin.lsystree.model.turtle;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.util.Map;

public class LSysModifiedBlocksWrapper {
    private final Map<Location, BlockData> data;
    public LSysModifiedBlocksWrapper(Map<Location, BlockData> data) {
        this.data = data;
    }
    public Map<Location, BlockData> getData() {
        return data;
    }
}
