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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If focused on chatbox, don't execute
        if (!plugin.chatboxFocused()) {
            return;
        }

        // If not currently typing, toggle roof
        if (!plugin.getCurrentlyTyping()) {
            if (config.toggle().matches(e)) {
                clientThread.invoke(() -> client.runScript(SET_REMOVE_ROOFS));
            }

            // If chatbox is opened from key press, set typing to true
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SLASH:
                case KeyEvent.VK_COLON:
                    plugin.setCurrentlyTyping(true);
                    break;
            }

        } else {
            // If currently typing, set currentlyTyping to false in the following cases
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                plugin.setCurrentlyTyping(false);

            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (Strings.isNullOrEmpty(client.getVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT))) {
                    plugin.setCurrentlyTyping(false);
                }

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                e.consume();
                plugin.setCurrentlyTyping(false);
                clientThread.invoke(() -> client.setVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT, ""));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
