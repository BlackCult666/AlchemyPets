package me.blackcult.alchemypets;

import co.aikar.commands.BukkitCommandManager;
import me.blackcult.alchemypets.commands.PetCommand;
import me.blackcult.alchemypets.inventories.InventoryManager;
import me.blackcult.alchemypets.inventories.impl.ShopInventory;
import me.blackcult.alchemypets.listeners.InventoryListener;
import me.blackcult.alchemypets.managers.FileManager;
import me.blackcult.alchemypets.pets.PetData;
import me.blackcult.alchemypets.pets.PetManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AlchemyPets extends JavaPlugin {
    private FileManager configFile;
    private FileManager petsFile;
    private FileManager inventoryFile;

    private InventoryManager inventoryManager;
    private ShopInventory shopInventory;

    private PetData petData;
    private PetManager petManager;

    @Override
    public void onEnable() {
        configFile = new FileManager(this, "config.yml");
        petsFile = new FileManager(this, "pets.yml");
        inventoryFile = new FileManager(this, "gui.yml");

        inventoryManager = new InventoryManager();
        shopInventory = new ShopInventory(this);

        petData = new PetData(this);
        petManager = new PetManager(this);

        getServer().getPluginManager().registerEvents(new InventoryListener(inventoryManager), this);
        setupCommands();
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            getPetManager().despawnPet(player);
        }
    }

    private void setupCommands() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);

        commandManager.registerCommand(new PetCommand());
        commandManager.enableUnstableAPI("help");
    }

    public PetData getPetData() {
        return petData;
    }

    public PetManager getPetManager() {
        return petManager;
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
