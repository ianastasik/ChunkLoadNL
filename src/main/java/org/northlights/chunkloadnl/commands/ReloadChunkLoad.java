package org.northlights.chunkloadnl.commands;

import static org.northlights.chunkloadnl.utils.Painter.paint;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.northlights.chunkloadnl.ChunkLoad;

public class ReloadChunkLoad implements CommandExecutor {

	private ChunkLoad plugin;

	public ReloadChunkLoad(ChunkLoad plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("chunkloadnl.reload")) {
			sender.sendMessage(paint(plugin.getConfig().getString("no-permission")));
			return true;
		}
		if (args.length > 0) {
			sender.sendMessage(paint(plugin.getConfig().getString("args")));
			return true;
		}
		if (!plugin.getConfig().contains("chunks")) {
			plugin.reloadConfig();
			sender.sendMessage(paint(plugin.getConfig().getString("reloaded")));
			return true;
		}
		final List<String> oldChunks = plugin.getConfig().getStringList("chunks");
		plugin.reloadConfig();
		List<String> newChunks = plugin.getConfig().getStringList("chunks");
		if (newChunks == null) {
			for (String s : oldChunks) {
				String world = s.split(";")[1];
				Integer x = Integer.valueOf(s.split(";")[2]);
				Integer y = Integer.valueOf(s.split(";")[3]);
				Integer z = Integer.valueOf(s.split(";")[4]);
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				l.getChunk().setForceLoaded(false);
			}
			sender.sendMessage(paint(plugin.getConfig().getString("reloaded")));
			return true;
		}
		for (String s : oldChunks) {
			if (!newChunks.contains(s)) {
				String world = s.split(";")[1];
				Integer x = Integer.valueOf(s.split(";")[2]);
				Integer y = Integer.valueOf(s.split(";")[3]);
				Integer z = Integer.valueOf(s.split(";")[4]);
				Location l = new Location(Bukkit.getWorld(world), x, y, z);
				l.getChunk().setForceLoaded(false);
			}
		}
		sender.sendMessage(paint(plugin.getConfig().getString("reloaded")));
		return true;
	}

}
