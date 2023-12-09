package org.fishydarwin.lsystree.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.fishydarwin.lsystree.LSysTree;
import org.fishydarwin.lsystree.model.interpreter.LSysProgram;
import org.fishydarwin.lsystree.repository.LSysProgramRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LSysLoadCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendRichMessage("<red>Please run this command as a player.");
            return true;
        }

        if (args.length < 1) {
            sender.sendRichMessage("<red>Invalid use. Please try /" + label + " (file)");
            return true;
        }

        try {

            File file = new File(LSysTree.getInstance().getDataFolder(), args[0] + ".tree");
            LSysProgram program = new LSysProgram(file);

            Player player = (Player) sender;

            LSysProgramRepository.setLoadedProgram(player, program);
            sender.sendPlainMessage("Loaded L-system program: " + args[0] + ".tree");

        } catch (Exception ex) {
            sender.sendRichMessage("<red>Error: " + ex.getMessage());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        File[] files = LSysTree.getInstance().getDataFolder().listFiles();
        if (files == null) return new ArrayList<>();
        return Arrays.stream(files)
                .filter((file) -> file.getName().endsWith(".tree"))
                .map((file) -> file.getName().replaceAll(".tree$", ""))
                .collect(Collectors.toList());
    }

}
