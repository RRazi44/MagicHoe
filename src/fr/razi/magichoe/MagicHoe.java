package fr.razi.magichoe;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import fr.razi.magichoe.listeners.MagicHoeListener;

public class MagicHoe extends JavaPlugin {

	@Override
	public void onEnable() {
		
		getLogger().log(Level.INFO, "Plugin load.");
		getServer().getPluginManager().registerEvents(new MagicHoeListener(this), this);
		
	}
	
	@Override
	public void onLoad() {
		getLogger().log(Level.INFO, "Plugin loading...");
	}
	
	public String getHoeName() {
		return getConfig().getString("NomExpHoue");
	}
	
}

