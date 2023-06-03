package me.blackcult.alchemypets.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
public abstract class InventoryHandler {
    public abstract String getTitle();

    public abstract int getSlots();

    public abstract void addItems();

    public abstract void openInventory(Player player);

    public abstract void onClick(InventoryClickEvent e);
}
