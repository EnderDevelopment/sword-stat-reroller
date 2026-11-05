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

public class RerollCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public RerollCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.").color(NamedTextColor.RED));
            return true;
        }

        ItemStack held = player.getInventory().getItemInMainHand();

        if (!SwordStatReroll.isSword(held)) {
            player.sendMessage(Component.text("You must hold a sword in your main hand to reroll its stats.")
                    .color(NamedTextColor.RED));
            return true;
        }

        int percent = SwordStatReroll.rollPercent();
        SwordStatReroll.applyStat(held, percent, plugin);

        double hp = SwordStatReroll.percentToHp(percent);
        String sign = percent >= 0 ? "+" : "";
        String hpSign = hp >= 0 ? "+" : "";
        double roundedHp = Math.round(hp * 100.0) / 100.0;

        player.sendMessage(Component.text("Rerolled sword stats!")
                .color(NamedTextColor.GOLD));
        player.sendMessage(Component.text(sign + percent + "% (" + hpSign + roundedHp + " HP)")
                .color(percent >= 0 ? NamedTextColor.GREEN : NamedTextColor.RED));
        return true;
    }
}
