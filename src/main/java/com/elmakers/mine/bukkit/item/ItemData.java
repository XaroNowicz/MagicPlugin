package com.elmakers.mine.bukkit.item;

import com.elmakers.mine.bukkit.block.MaterialAndData;
import com.elmakers.mine.bukkit.utility.InventoryUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData implements com.elmakers.mine.bukkit.api.item.ItemData {
    private String key;
    private ItemStack item;
    private double worth;
    private String creatorId;
    private String creator;
    
    public ItemData(String key, ConfigurationSection configuration) throws Exception {
        if (configuration.isItemStack("item")) {
            item = configuration.getItemStack("item");
        } else {
            String materialKey = configuration.getString("item", key);
            MaterialAndData material = new MaterialAndData(materialKey);
            if (material.isValid()) {
                item = material.getItemStack(1);
            }
            if (item == null) {
                throw new Exception("Invalid item key: " + materialKey);
            }
        }
        if (item == null) {
            throw new Exception("Invalid item configuration: " + key);
        }
        worth = configuration.getDouble("worth", 0);
        creator = configuration.getString("creator");
        creatorId = configuration.getString("creator_id");
        
        // Convenience method for renaming items
        String customName = configuration.getString("name");
        if (customName != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(customName);
            item.setItemMeta(meta);
        }
    }
    
    public ItemData(String key, ItemStack item, double worth) throws Exception {
        if (item == null) {
            throw new Exception("Invalid item");
        }
        this.key = key;
        this.item = item;
        this.worth = worth;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public double getWorth() {
        return worth;
    }
    
    @Override
    public ItemStack getItemStack(int amount) {
        ItemStack newItem = InventoryUtils.getCopy(item);
        if (newItem == null) {
            return null;
        }
        newItem.setAmount(amount);
        return newItem;
    }
    
    @Override
    public String getCreator() {
        return creator;
    }
    
    @Override
    public String getCreatorId() {
        return creatorId;
    }
}
