package me.blackcult.alchemypets.managers;

import me.blackcult.alchemypets.AlchemyPets;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DataManager {
    private AlchemyPets plugin;

    public DataManager(AlchemyPets plugin) {
        this.plugin = plugin;
    }

    public void addData(Player player, Entity entity) {
        PersistentDataContainer data = entity.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "owner"), PersistentDataType.STRING, player.getUniqueId().toString());
    }

    public void addPlayerPet(Player player, String entityType) {
        plugin.getPetsFile().set(player.getUniqueId() + ".petType", entityType);
        plugin.getPetsFile().set(player.getUniqueId() + ".petName",  plugin.getConfig().getString("pet-settings.default-petname").replaceAll("%player%", player.getName()));
        plugin.getPetsFile().set(player.getUniqueId() + ".petMaxHealth", plugin.getConfig().getDouble("pet-settings.attributes.base-health"));
        plugin.getPetsFile().set(player.getUniqueId() + ".petLevel", 0);
        plugin.getPetsFile().set(player.getUniqueId() + ".petEffects", null);

        plugin.getPetsFile().saveConfig();

    }
}
