package org.fishydarwin.lsystree;

import org.bukkit.plugin.java.JavaPlugin;
import org.fishydarwin.lsystree.minecraft.commands.LSysLoadCommand;
import org.fishydarwin.lsystree.minecraft.listeners.LSysToolListener;
import org.fishydarwin.lsystree.model.instructions.real.*;
import org.fishydarwin.lsystree.model.instructions.shape.CylinderInstruction;
import org.fishydarwin.lsystree.model.instructions.shape.SphereInstruction;
import org.fishydarwin.lsystree.model.instructions.util.LineWidthInstruction;
import org.fishydarwin.lsystree.model.instructions.util.MaterialInstruction;
import org.fishydarwin.lsystree.model.instructions.util.RandomInstruction;
import org.fishydarwin.lsystree.model.instructions.vector.RotateXYInstruction;
import org.fishydarwin.lsystree.model.instructions.vector.RotateXZInstruction;
import org.fishydarwin.lsystree.model.instructions.vector.RotateZYInstruction;
import org.fishydarwin.lsystree.model.instructions.vector.TranslateInstruction;
import org.fishydarwin.lsystree.registry.LSysInstructionTypeRegistry;

public final class LSysTree extends JavaPlugin {

    private static LSysTree plugin;

    public static LSysTree getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        // Register built-in instructions
        LSysInstructionTypeRegistry.registerInstruction(AddInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(SubInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(MulInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(DivInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(ModInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(AbsInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(SqrtInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(SinInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(CosInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(TanInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(CtgInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(LnInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(FloorInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(CeilInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(PowInstruction.class);

        LSysInstructionTypeRegistry.registerInstruction(TranslateInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(RotateZYInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(RotateXZInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(RotateXYInstruction.class);

        LSysInstructionTypeRegistry.registerInstruction(RandomInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(MaterialInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(LineWidthInstruction.class);

        LSysInstructionTypeRegistry.registerInstruction(CylinderInstruction.class);
        LSysInstructionTypeRegistry.registerInstruction(SphereInstruction.class);

        // Register commands
        LSysLoadCommand lSysLoadCommand = new LSysLoadCommand();
        getCommand("lsysload").setExecutor(lSysLoadCommand);
        getCommand("lsysload").setTabCompleter(lSysLoadCommand);

        // Register event handler
        getServer().getPluginManager().registerEvents(new LSysToolListener(), this);

    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

}
