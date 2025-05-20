package fr.razi.magichoe.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import fr.razi.magichoe.Hoe;
import fr.razi.magichoe.MagicHoe;

public class MagicHoeListener implements Listener {
	
	private MagicHoe instance;
	
	public MagicHoeListener(MagicHoe instance) {
		this.instance = instance;
	}

	@EventHandler
	public void playerHarvest(BlockBreakEvent event) {
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		ItemStack it = event.getPlayer().getItemInHand();
		
		if(Hoe.isMagicHoe(it, instance.getHoeName())) {
			Hoe hoe = new Hoe(it, instance);
			event.setCancelled(true);
			hoe.harve(player, block);
	
			System.out.println("NTM");
		}
			


	}

}
