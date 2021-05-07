package degenereaper.rooftoggle;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.rooftoggle.RoofTogglePlugin;

public class RoofTogglePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RoofTogglePlugin.class);
		RuneLite.main(args);
	}
}