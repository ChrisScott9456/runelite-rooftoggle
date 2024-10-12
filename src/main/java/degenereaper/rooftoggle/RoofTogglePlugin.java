package degenereaper.rooftoggle;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.ComponentID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Roof Toggle",
        description = "Allows use of a keybind for toggling roofs on and off without needing to open the in-game settings menu.",
        tags = {"roof", "toggle", "keybind"}
)
public class RoofTogglePlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private KeyManager keyManager;

    @Inject
    private RoofToggleKeyListener inputListener;

    private boolean typing;

    protected boolean isTyping() {
        return typing;
    }

    protected void setTyping(boolean val) {
        typing = val;
    }

    @Provides
    RoofToggleConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(RoofToggleConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        typing = false;
        keyManager.registerKeyListener(inputListener);
    }

    @Override
    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(inputListener);
    }

    boolean chatboxFocused()
    {
        Widget chatboxParent = client.getWidget(ComponentID.CHATBOX_PARENT);
        if (chatboxParent == null || chatboxParent.getOnKeyListener() == null)
        {
            return false;
        }

        // If the search box on the world map is open and focused, ~keypress_permit blocks the keypress
        Widget worldMapSearch = client.getWidget(ComponentID.WORLD_MAP_SEARCH);
        if (worldMapSearch != null && client.getVarcIntValue(VarClientInt.WORLD_MAP_SEARCH_FOCUSED) == 1)
        {
            return false;
        }

        // The report interface blocks input due to 162:54 being hidden, however player/npc dialog and
        // options do this too, and so we can't disable remapping just due to 162:54 being hidden.
        Widget report = client.getWidget(ComponentID.REPORT_ABUSE_PARENT);
        if (report != null)
        {
            return false;
        }

        return true;
    }
}
