package org.fishydarwin.lsystree.minecraft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.LSysCompilationException;
import org.fishydarwin.lsystree.model.exception.RecursionLimitException;
import org.fishydarwin.lsystree.model.exception.UnknownAtomException;
import org.fishydarwin.lsystree.model.interpreter.LSysProgram;
import org.fishydarwin.lsystree.repository.LSysProgramRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LSysToolListener implements Listener {

    private final Map<UUID, Location> currentSelectedLocation = new HashMap<>();

    @EventHandler
    public void onBoneUse(PlayerInteractEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.BONE) return;

        event.setCancelled(true);

        if (!LSysProgramRepository.hasLoadedProgram(event.getPlayer())) {
            event.getPlayer().sendRichMessage("<red>You do not have an L-system program loaded.");
            return;
        }

        if (event.getAction().isRightClick()) {
            if (currentSelectedLocation.containsKey(event.getPlayer().getUniqueId())) {
                LSysProgram program = LSysProgramRepository.getLoadedProgram(event.getPlayer());
                event.getPlayer().sendPlainMessage("Running program (n = " + program.getIteration() + ")...");
                try {
                    program.startTrace(currentSelectedLocation.get(event.getPlayer().getUniqueId()));
                    program.nextIteration();
                } catch (Exception ex) {
                    event.getPlayer().sendRichMessage("Error evaluating: " + ex.getMessage());
                }
            }
        }

        if (event.getAction().isLeftClick()) {
            if (event.getPlayer().isSneaking()) {
                LSysProgram program = LSysProgramRepository.getLoadedProgram(event.getPlayer());
                event.getPlayer().sendPlainMessage("Reset current tree to axiom iteration (n = 0).");
                program.resetIterations();
                return;
            }
            if (event.getClickedBlock() == null) return;
            currentSelectedLocation.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
            LSysProgramRepository.setLoadedProgram(event.getPlayer(),
                    new LSysProgram(LSysProgramRepository.getLoadedProgram(event.getPlayer())));
            event.getPlayer().sendPlainMessage("Set new tree growth location.");
        }
    }

}
