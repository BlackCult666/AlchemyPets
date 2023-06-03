package me.blackcult.alchemypets;

import co.aikar.commands.BukkitCommandManager;
import me.blackcult.alchemypets.commands.PetCommand;
import me.blackcult.alchemypets.inventories.InventoryManager;
import me.blackcult.alchemypets.inventories.impl.ShopInventory;
import me.blackcult.alchemypets.listeners.InventoryListener;
import me.blackcult.alchemypets.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AlchemyPets extends JavaPlugin {
    private FileManager configFile;
    private FileManager petsFile;
    private FileManager inventoryFile;

    private InventoryManager inventoryManager;
    private ShopInventory shopInventory;

    @Override
    public void onEnable() {
        configFile = new FileManager(this, "config.yml");
        petsFile = new FileManager(this, "pets.yml");
        inventoryFile = new FileManager(this, "gui.yml");

        inventoryManager = new InventoryManager();
        shopInventory = new ShopInventory(this);

        getServer().getPluginManager().registerEvents(new InventoryListener(inventoryManager), this);

        setupCommands();
    }

    private void setupCommands() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);

        commandManager.registerCommand(new PetCommand());
        commandManager.enableUnstableAPI("help");
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ShopInventory getShopInventory() {
        return shopInventory;
    }

    public FileManager getConfigFile() {
        return configFile;
    }

    public FileManager getInventoryFile() {
        return inventoryFile;
    }

    public FileManager getPetsFile() {
        return petsFile;
    }
}
