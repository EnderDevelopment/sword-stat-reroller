# Sword Stat Reroller

A Minecraft plugin that adds stat rerolling for swords, allowing players to apply a random or specific percentage modifier from -20% to +60%. Each 1% corresponds to an additional 0.05 HP (with negative percentages resulting in a reduction of 0.05 HP per 1%), and the current percentage value is displayed on the sword's lore.

## Install

1. Download the `plugin.jar` file from the release.
2. Place the downloaded `.jar` file into your server's `plugins` folder.
3. Restart your server (or use a plugin manager to load the plugin).

## Use

### Random Reroll

Hold the sword you want to reroll in your main hand, then run:

```
/reroll
```

This will assign a random percentage between -20% and +60% to the sword, update its lore to show the new stat, and send you a message with the result.

### Set Specific Stat

Hold the sword you want to modify in your main hand, then run:

```
/setstat <percentage>
```

Replace `<percentage>` with a whole number between -20 and +60 (for example: `/setstat +25` or `/setstat -10`). This will set the sword's stat to exactly that percentage, update its lore accordingly, and confirm the change.

### What You See

After applying a stat, the sword's lore displays:
- A "Stat Reroll" line in dark purple.
- The percentage modification (e.g., "+25% Damage/HP") in green if positive or red if negative.
- The equivalent HP value (e.g., "+1.25 HP") in the same color.

The plugin works with any sword material (wooden, stone, iron, gold, diamond, netherite).

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/1e2542ae-2cc3-4a92-808d-3dd1d919f336?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=1e2542ae-2cc3-4a92-808d-3dd1d919f336
