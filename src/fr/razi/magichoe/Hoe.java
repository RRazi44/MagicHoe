package fr.razi.magichoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.material.NetherWarts;

public class Hoe {
	
	private List<String> lore;
	private final ItemStack item;
	@SuppressWarnings("unused")
	private final String name;
	@SuppressWarnings("unused")
	private int experienceRequired;
	private int experience;
	@SuppressWarnings("unused")
	private MagicHoe instance;
	private int radiusReach = 0;
	private int level;
	
	public Hoe(ItemStack item, MagicHoe instance) {
		this.item = item;
		this.instance = instance;
		this.lore = item.getItemMeta().getLore();
		this.experience = getExperienceFromLore();
		this.name = instance.getHoeName();
	}
	
	public Hoe toMagicHoe(ItemStack baseHoe, MagicHoe instance) {
		if(isMagicHoe(baseHoe, instance.getHoeName())) {
			return new Hoe(baseHoe, instance);
		}
		return null;
	}
	
	public static boolean isMagicHoe(ItemStack it, String recolteuseEXPName) {
		
    	if(it == null || !it.hasItemMeta()) return false;
    	if(!it.getItemMeta().hasDisplayName()) return false;
    	return it.getItemMeta().getDisplayName().equalsIgnoreCase(recolteuseEXPName);
    	
    }
	
	public ItemStack createMagicHoe(Material mat, String nomItem, ArrayList<String> lore) {

		ItemStack houe = new ItemStack(mat);
		ItemMeta houeM = houe.getItemMeta();
		
		houeM.setDisplayName(nomItem);
		houeM.setLore(lore);
		houe.setItemMeta(houeM);
		
		return houe;
	}
	
	@SuppressWarnings("deprecation")
	public void harve(Player player, Block harvestBlock) {
		
		int xpAcquired = 0;
		ArrayList<Block> BlocksInRange = getBlockRadius(harvestBlock);
		
		for(Block block : BlocksInRange) {
			
	    	BlockState state = block.getState();
		    MaterialData data = state.getData();
		    Material type = block.getType();
		    if (type == Material.NETHER_WARTS && data instanceof NetherWarts) {
		        if (((NetherWarts) data).getState() == NetherWartsState.RIPE) {
		            block.setType(Material.NETHER_WARTS);
		            player.getInventory().addItem(new ItemStack(Material.NETHER_STALK, 1));
		        }
		    } else if (data instanceof Crops) {
		        if (((Crops) data).getState() == CropState.RIPE) {
		            block.setType(Material.CROPS);
		            player.getInventory().addItem(new ItemStack(Material.WHEAT));
		        }
		    } else if ((type == Material.CARROT || type == Material.POTATO) && block.getData() == 7) {
		        block.setType(type);
		        Material drop = (type == Material.CARROT) ? Material.CARROT_ITEM : Material.POTATO_ITEM;
		        player.getInventory().addItem(new ItemStack(drop, 1));
		    }  
		}
		
		incExperience(xpAcquired);
		
	}
	
	private ArrayList<Block> getBlockRadius(Block center) {
	    ArrayList<Block> blocksInRadius = new ArrayList<>();
	    int radius = this.getRadiusReach();

	    for (int dx = -radius; dx <= radius; dx++) {
	        for (int dz = -radius; dz <= radius; dz++) {
	            Block b = center.getRelative(dx, 0, dz);
	            blocksInRadius.add(b);
	        }
	    }

	    return blocksInRadius;
	}

	public void incExperience(int amount) {
		if(isLevelMax()) return;
		
	    List<String> lore = getLore();
	    if (lore.size() < 2) return;

	    Optional<XpData> optXp = XpData.from(lore.get(1));
	    if (!optXp.isPresent()) return;

	    XpData xp = optXp.get();
	    xp.add(amount);

	    if (xp.isLevelUp() && !isLevelMax()) {
	        levelUp();
	        xp.reset();
	        xp.setRequired(getRequiredXpForNextLevel());
	    }
	    
	    lore.set(1, xp.toLoreLine());
	    updateLore(lore);
	}
	
	//TODO
	private int getRequiredXpForNextLevel() {
		return 0;
	}
	
	//TODO
	public int getExperienceFromLore() {return 0;}
	
	//TODO
	public void levelUp() {}
	
	//TODO
	public boolean isLevelMax() {return false;}

	public void updateLore(List<String> newlore) {
		this.lore = newlore;
		this.item.getItemMeta().setLore(newlore);
	}
	
	public int getExperience () {
		return this.experience;
	}

	public List<String> getLore() {
		return this.lore;
	}
	
	public int getRadiusReach(){
		return this.radiusReach;
	}
	
	public int getHoeLevel() {
		return this.level;
	}
}