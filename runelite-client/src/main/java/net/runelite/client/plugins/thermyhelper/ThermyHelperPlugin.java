package net.runelite.client.plugins.thermyhelper;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static net.runelite.api.NpcID.THERMONUCLEAR_SMOKE_DEVIL;

@PluginDescriptor(
        name = "Thermy Tile Highlighter",
        description = "Highlights tiles 9 spaces away from the Thermonuclear Smoke Devil",
        tags = {"npc", "highlight", "thermonuclear", "smoke", "devil"}
)

public class ThermyHelperPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ThermyHelperOverlay overlay;

    @Inject
    private ThermyHelperConfig config;

    @Provides
    ThermyHelperConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ThermyHelperConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
    }

    public NPC getThermonuclearSmokeDevil()
    {
        for (NPC npc : client.getNpcs())
        {
            if (npc.getId() == THERMONUCLEAR_SMOKE_DEVIL)
            {
                return npc;
            }
        }
        return null;
    }

    public List<WorldPoint> get9TileRadius(WorldPoint npcLocation) {
        List<WorldPoint> tileList = new ArrayList<>();
        int radius = 9;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                // Calculate euclidean distance
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == radius) {
                    tileList.add(new WorldPoint(npcLocation.getX() + dx, npcLocation.getY() + dy, npcLocation.getPlane()));
                }
            }
        }

        return tileList;
    }
}
