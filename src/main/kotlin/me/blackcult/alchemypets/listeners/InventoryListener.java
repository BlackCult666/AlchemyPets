package me.blackcult.alchemypets.listeners;

import me.blackcult.alchemypets.inventories.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {
    private final InventoryManager inventoryManager;

    public InventoryListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        this.inventoryManager.handleClick(e);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        this.inventoryManager.handleClose(e);
    }
}
