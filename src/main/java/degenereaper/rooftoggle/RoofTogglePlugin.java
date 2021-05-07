package degenereaper.rooftoggle;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
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

    private boolean currentlyTyping;

    protected boolean getCurrentlyTyping() {
        return currentlyTyping;
    }

    protected void setCurrentlyTyping(boolean val) {
        currentlyTyping = val;
    }

    @Provides
    RoofToggleConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(RoofToggleConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        currentlyTyping = false;
        keyManager.registerKeyListener(inputListener);
    }

    @Override
    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(inputListener);
    }

    boolean chatboxFocused() {
        Widget chatboxParent = client.getWidget(WidgetInfo.CHATBOX_PARENT);

        // If not focused on the chatbox or the world map search, return true
        if (chatboxParent != null && chatboxParent.getOnKeyListener() != null) {
            Widget worldMapSearch = client.getWidget(WidgetInfo.WORLD_MAP_SEARCH);
            return worldMapSearch == null || client.getVar(VarClientInt.WORLD_MAP_SEARCH_FOCUSED) != 1;
        }

        return false;
    }
}
