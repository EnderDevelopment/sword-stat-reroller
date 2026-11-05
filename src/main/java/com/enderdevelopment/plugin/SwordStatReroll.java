package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles the stat rerolling logic for swords.
 *
 * Percentage range: -20% to +60% (integer values).
 * Each 1% corresponds to 0.05 HP (or damage) modification.
 * Negative percentages reduce HP, positive percentages increase it.
 */
public final class SwordStatReroll {

    private static final int MIN_PERCENT = -20;
    private static final int MAX_PERCENT = 60;
    /** HP per 1%: 0.05 HP per 1% */
    private static final double HP_PER_PERCENT = 0.05;

    private static final String STAT_PERCENT_KEY = "sword_stat_percent";

    /** Check whether the given item is a sword. */
    public static boolean isSword(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        String typeName = item.getType().name();
        return typeName.endsWith("_SWORD");
    }

    /** Get the stored stat percentage from an item's persistent data. Returns null when absent. */
    public static Integer getStoredPercent(ItemStack item, JavaPlugin plugin) {
        if (item == null || item.hasItemMeta() == false) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        NamespacedKey key = new NamespacedKey(plugin, STAT_PERCENT_KEY);
        return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    /**
     * Roll a random percentage in the range [-20, +60].
     */
    public static int rollPercent() {
        return ThreadLocalRandom.current().nextInt(MIN_PERCENT, MAX_PERCENT + 1);
    }

    /**
     * Validate and clamp a user-provided percentage within the range.
     */
    public static int clampPercent(int percent) {
        return Math.max(MIN_PERCENT, Math.min(MAX_PERCENT, percent));
    }

    /**
     * Compute the HP/damage bonus/penalty for a given percentage.
     * Each 1% equals 0.05 HP.
     */
    public static double percentToHp(int percent) {
        return percent * HP_PER_PERCENT;
    }

    /**
     * Apply a stat percent to a sword item, updating its lore and persistent data.
     * Returns true on success.
     */
    public static boolean applyStat(ItemStack item, int percent, JavaPlugin plugin) {
        if (!isSword(item)) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        NamespacedKey key = new NamespacedKey(plugin, STAT_PERCENT_KEY);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, percent);

        double hp = percentToHp(percent);

        // Build the lore.
        List<Component> lore = new ArrayList<>();
        NamedTextColor color = percent >= 0 ? NamedTextColor.GREEN : NamedTextColor.RED;
        String sign = percent >= 0 ? "+" : "";
        double roundedHp = Math.round(hp * 100.0) / 100.0;
        String hpSign = hp >= 0 ? "+" : "";

        lore.add(Component.text("Stat Reroll").color(NamedTextColor.DARK_PURPLE));
        lore.add(Component.text(sign + percent + "% Damage/HP").color(color));
        lore.add(Component.text(hpSign + roundedHp + " HP").color(color));

        meta.lore(lore);
        item.setItemMeta(meta);
        return true;
    }
}
