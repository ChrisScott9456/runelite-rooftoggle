package degenereaper.rooftoggle;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;
import java.awt.event.KeyEvent;

@ConfigGroup("rooftoggle")
public interface RoofToggleConfig extends Config {
    @ConfigItem(
            position = 0,
            keyName = "toggle",
            name = "Roof Toggle Keybind",
            description = "The keybind used to toggle roofs."
    )

    default ModifierlessKeybind toggle()
    {
        return new ModifierlessKeybind(KeyEvent.VK_R, 0);
    }
}
