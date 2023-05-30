package org.northlights.chunkloadnl.utils;

import net.md_5.bungee.api.ChatColor;

public class Painter {
	
	public static String paint(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

}
