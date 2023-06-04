package me.blackcult.alchemypets.inventories.impl;

import me.blackcult.alchemypets.AlchemyPets;
import me.blackcult.alchemypets.inventories.InventoryHandler;
import me.blackcult.alchemypets.managers.ItemManager;
import me.blackcult.alchemypets.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ShopInventory extends InventoryHandler {

    private final AlchemyPets plugin;
    private Inventory inv;

    public ShopInventory(AlchemyPets plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTitle() {
        return plugin.getInventoryFile().getString("pet-shop.title");
    }

    @Override
    public int getSlots() {
        return plugin.getInventoryFile().getInt("pet-shop.rows") * 9;
    }

    @Override
    public void addItems() {
        for(String key : plugin.getInventoryFile().getConfigurationSection("pet-shop.pets").getKeys(false)) {
            ItemManager itemManager = new ItemManager(plugin.getInventoryFile(), "pet-shop.pets." + key);
            itemManager.setItem(inv, itemManager.getItemStack());
        }
    }

    @Override
    public void openInventory(Player player) {
        inv = Bukkit.createInventory(null, getSlots(), Utils.color(getTitle()));
        addItems();

        plugin.getInventoryManager().registerInventory(inv, this);
        player.openInventory(inv);
    }

    // This code should be improved.
    @Override
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        for(String key : plugin.getInventoryFile().getConfigurationSection("pet-shop.pets").getKeys(false)) {
            if(!plugin.getInventoryFile().getString("pet-shop.pets." + key + ".slot").equals(String.valueOf(e.getSlot() + 1))) {
                continue;
            }
            String entityType = plugin.getInventoryFile().getString("pet-shop.pets." + key + ".type");
            if(entityType != null) {
                plugin.getPetData().addPlayerPet(p, entityType);
                plugin.getPetManager().spawnPet(p);
                p.closeInventory();
                break;
            }
            if(plugin.getPetManager().hasPet(p)) {
                p.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.has-pet")));
                break;
            }
        }
        e.setCancelled(true);
    }
}
