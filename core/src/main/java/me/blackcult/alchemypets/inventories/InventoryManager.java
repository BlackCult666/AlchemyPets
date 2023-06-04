package me.blackcult.alchemypets.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private final Map<Inventory, InventoryHandler> activeInventories = new HashMap<>();

    public void registerInventory(Inventory inventory, InventoryHandler inventoryHandler) {
        this.activeInventories.put(inventory, inventoryHandler);
    }

    public void unregisterInventory(Inventory inventory) {
        this.activeInventories.remove(inventory);
    }

    public void handleClick(InventoryClickEvent e) {
        InventoryHandler inventoryHandler = this.activeInventories.get(e.getInventory());
        if(inventoryHandler != null) {
            inventoryHandler.onClick(e);
        }
    }

    public void handleClose(InventoryCloseEvent e) {
        InventoryHandler inventoryHandler = this.activeInventories.get(e.getInventory());
        if(inventoryHandler != null) {
            unregisterInventory(e.getInventory());
        }
    }
}
