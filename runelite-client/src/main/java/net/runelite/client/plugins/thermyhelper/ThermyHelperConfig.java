package net.runelite.client.plugins.thermyhelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import java.awt.Color;

@ConfigGroup("Thermyhelper")
public interface ThermyHelperConfig extends Config {
    @ConfigItem(
            keyName = "tileColor",
            name = "Tile Color",
            description = "The color of the highlighted tiles."
    )
    default Color tileColor() {
        return Color.GREEN; // Default tile color
    }

    @ConfigItem(
            keyName = "tileWidth",
            name = "Tile Width",
            description = "The width of the tile outline."
    )
    default int tileWidth() {
        return 1; // Default stroke width
    }

    @ConfigItem(
            keyName = "transparency",
            name = "Tile Transparency",
            description = "Transparency of the highlighted tile (0-255)."
    )
    default int transparency() {
        return 100; // Default transparency (semi-transparent)
    }

}
