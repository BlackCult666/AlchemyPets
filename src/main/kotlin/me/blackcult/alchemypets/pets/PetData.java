package me.blackcult.alchemypets.pets;

import me.blackcult.alchemypets.AlchemyPets;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PetData {
    private final AlchemyPets plugin;

    public PetData(AlchemyPets plugin) {
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

    public void removePlayerPet(Player player) {
        plugin.getPetsFile().set(player.getUniqueId().toString(), null);
        plugin.getPetsFile().saveConfig();
    }

    public void setPetName(Player owner, String name) {
        plugin.getPetsFile().set(owner.getUniqueId() + ".petName", name);
        plugin.getPetsFile().saveConfig();

        if(plugin.getPetManager().isPetNearby(owner)) {
            plugin.getPetManager().callPet(owner);
        }

    }

    public String getData(Entity entity) {
        PersistentDataContainer data = entity.getPersistentDataContainer();
        return data.get(new NamespacedKey(plugin, "owner"), PersistentDataType.STRING);
    }

    public String getPetType(Player player) {
        return plugin.getPetsFile().getString(player.getUniqueId() + ".petType");
    }

    public String getName(Player player) {
        String name = plugin.getConfig().getString("pet-settings.default-petname").replaceAll("%player%", player.getName());
        if (plugin.getPetsFile().getString(player.getUniqueId() + ".petName") == null) {
            return name;
        }
        return plugin.getPetsFile().getString(player.getUniqueId() + ".petName");
    }

    public double getMaxHealth(Player player) {
        return plugin.getPetsFile().getDouble(player.getUniqueId() + ".petMaxHealth");
    }

    public int getPetLevel(Player player) {
        return plugin.getPetsFile().getInt(player.getUniqueId() + ".petLevel");
    }

}
