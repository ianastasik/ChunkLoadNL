package org.northlights.chunkloadnl.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.northlights.chunkloadnl.ChunkLoad;

import static org.northlights.chunkloadnl.utils.Painter.paint;

import java.util.Calendar;
import java.util.List;

public class LoadChunk implements CommandExecutor {

	private ChunkLoad plugin;

	public LoadChunk(ChunkLoad plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("chunkloadnl.use")) {
			sender.sendMessage(paint(plugin.getConfig().getString("no-permission")));
			return true;
		}
		if (args.length != 1) {
			sender.sendMessage(paint(plugin.getConfig().getString("args")));
			return true;
		}
		Player p = Bukkit.getPlayerExact(args[0]);
		if (p == null) {
			sender.sendMessage(paint(plugin.getConfig().getString("player-offline")));
			return true;
		}
		p.getLocation().getChunk().setForceLoaded(true);
		sender.sendMessage(paint(plugin.getConfig().getString("chunk-force-loaded")));
		p.sendMessage(paint(plugin.getConfig().getString("admin-loaded-your-chunk")));
		var loc = p.getLocation();
		Calendar c = Calendar.getInstance();
		int year = c.get(1);
		int month = c.get(2);
		int day = c.get(5);
		int hour = c.get(11);
		int minute = c.get(12);
		int second = c.get(13);
		var builder = new StringBuilder();
		builder.append(p.getName()).append(";").append(loc.getWorld().getName()).append(";").append(loc.getBlockX()).append(";").append(loc.getBlockY()).append(";")
				.append(loc.getBlockZ()).append(";").append(day).append(".").append(month).append(".").append(year)
				.append(";").append(hour).append(":").append(minute).append(":").append(second);
		if (plugin.getConfig().contains("chunks")) {
			var newList = plugin.getConfig().getStringList("chunks");
			newList.add(builder.toString());
			plugin.getConfig().set("chunks", newList);
		} else {
			plugin.getConfig().set("chunks", List.of(builder.toString()));
		}
		plugin.saveConfig();
		return true;
	}

}