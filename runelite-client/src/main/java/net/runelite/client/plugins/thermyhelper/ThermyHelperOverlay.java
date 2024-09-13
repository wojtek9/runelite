package net.runelite.client.plugins.thermyhelper;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class ThermyHelperOverlay extends Overlay {
    private final Client client;
    private final ThermyHelperPlugin plugin;
    private final ThermyHelperConfig config;

    @Inject
    private ModelOutlineRenderer modelOutlineRenderer;

    @Inject
    public ThermyHelperOverlay(Client client, ThermyHelperPlugin plugin, ThermyHelperConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        NPC smokeDevil = plugin.getThermonuclearSmokeDevil();

        if (smokeDevil != null)
        {
            WorldPoint npcLocation = smokeDevil.getWorldLocation();
            List<WorldPoint> tileList = plugin.get9TileRadius(npcLocation);

            for (WorldPoint tile : tileList)
            {
                // Convert WorldPoint to LocalPoint
                net.runelite.api.coords.LocalPoint localTile = net.runelite.api.coords.LocalPoint.fromWorld(client, tile);
                if (localTile != null)
                {
                    // Get user config
                    Color color = config.tileColor();
                    int width = config.tileWidth();
                    int transparency = config.transparency();
                    // Highlight the tile
                    drawTile(graphics, localTile, color, width, transparency);
                }
            }
        }

        return null;
    }

    private void drawTile(Graphics2D graphics, net.runelite.api.coords.LocalPoint point, Color color, int strokeWidth, int alpha)
    {
        Polygon poly = Perspective.getCanvasTilePoly(client, point);
        if (poly != null)
        {
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            graphics.setStroke(new BasicStroke(strokeWidth));
            graphics.draw(poly);
            graphics.fill(poly);
        }
    }
}
