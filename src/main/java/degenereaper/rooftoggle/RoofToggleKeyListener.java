package degenereaper.rooftoggle;

import java.awt.event.KeyEvent;
import javax.inject.Inject;

import com.google.common.base.Strings;
import net.runelite.api.Client;
import net.runelite.api.VarClientStr;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.KeyListener;

class RoofToggleKeyListener implements KeyListener
{
    private static final int SET_REMOVE_ROOFS = 4577;

    @Inject
    private RoofTogglePlugin plugin;

    @Inject
    private RoofToggleConfig config;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If focused on chatbox, don't execute
        if (!plugin.chatboxFocused()) {
            return;
        }

        // If not currently typing, toggle roof
        if (!plugin.isTyping()) {
            if (config.toggle().matches(e)) {
                clientThread.invoke(() -> client.runScript(SET_REMOVE_ROOFS));
            }

            // If chatbox is opened from key press, set typing to true
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SLASH:
                case KeyEvent.VK_COLON:
                    plugin.setTyping(true);
                    break;
            }
        } else {
            // If typing, set typing to false when using any of the following keys
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_ESCAPE:
                    e.consume();
                    plugin.setTyping(false);
                    break;
                case KeyEvent.VK_ENTER:
                    plugin.setTyping(false);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    // Only lock chat on backspace when the typed text is now empty
                    if (Strings.isNullOrEmpty(client.getVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT)))
                    {
                        plugin.setTyping(false);
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
