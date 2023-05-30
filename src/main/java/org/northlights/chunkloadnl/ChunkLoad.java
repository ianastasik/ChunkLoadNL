package org.northlights.chunkloadnl;

import org.bukkit.plugin.java.JavaPlugin;
import org.northlights.chunkloadnl.commands.LoadChunk;
import org.northlights.chunkloadnl.commands.ReloadChunkLoad;

public class ChunkLoad extends JavaPlugin {
	
	public void onEnable() {
		saveDefaultConfig();
		getCommand("loadchunk").setExecutor(new LoadChunk(this));
		getCommand("reloadchunkload").setExecutor(new ReloadChunkLoad(this));
	}

}
