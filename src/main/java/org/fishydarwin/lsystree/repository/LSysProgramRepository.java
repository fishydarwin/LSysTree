package org.fishydarwin.lsystree.repository;

import org.bukkit.entity.Player;
import org.fishydarwin.lsystree.model.interpreter.LSysProgram;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LSysProgramRepository {

    private final static Map<UUID, LSysProgram> playerLoadedPrograms = new HashMap<>();

    public static void setLoadedProgram(Player player, LSysProgram program) {
        playerLoadedPrograms.put(player.getUniqueId(), program);
    }

    public static LSysProgram getLoadedProgram(Player player) {
        return playerLoadedPrograms.get(player.getUniqueId());
    }

    public static boolean hasLoadedProgram(Player player) {
        return playerLoadedPrograms.containsKey(player.getUniqueId());
    }

}
