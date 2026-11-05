package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetStatCommand implements CommandExecutor {

    private static final int MIN_PERCENT = -20;
    private static final int MAX_PERCENT = 60;

    private final JavaPlugin plugin;

    public SetStatCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.").color(NamedTextColor.RED));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(Component.text("Usage: /setstat <percentage>").color(NamedTextColor.RED));
            return true;
        }

        int percent;
        try {
            percent = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("Percentage must be a whole number between -20 and +60.")
                    .color(NamedTextColor.RED));
            return true;
        }

        if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
            player.sendMessage(Component.text("Percentage must be between -20 and +60.")
                    .color(NamedTextColor.RED));
            return true;
        }

        ItemStack held = player.getInventory().getItemInMainHand();

        if (!SwordStatReroll.isSword(held)) {
            player.sendMessage(Component.text("You must hold a sword in your main hand to set its stats.")
                    .color(NamedTextColor.RED));
            return true;
        }

        SwordStatReroll.applyStat(held, percent, plugin);

        double hp = SwordStatReroll.percentToHp(percent);
        String sign = percent >= 0 ? "+" : "";
        String hpSign = hp >= 0 ? "+" : "";
        double roundedHp = Math.round(hp * 100.0) / 100.0;

        player.sendMessage(Component.text("Sword stats set to " + sign + percent + "% (" + hpSign + roundedHp + " HP).")
                .color(NamedTextColor.GOLD));
        return true;
    }
}
