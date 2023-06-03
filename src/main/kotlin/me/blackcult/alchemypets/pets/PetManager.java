package me.blackcult.alchemypets.pets;

import me.blackcult.alchemypets.AlchemyPets;
import me.blackcult.alchemypets.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

public class PetManager {
    private AlchemyPets plugin;

    public PetManager(AlchemyPets plugin) {
        this.plugin = plugin;
    }

    public void spawnPet(Player owner) {
        LivingEntity entity = (LivingEntity) owner.getWorld().spawnEntity(owner.getLocation(), EntityType.valueOf(plugin.getPetData().getPetType(owner)));
        setupPet(entity, owner);
    }

    public void despawnPet(Player owner) {
        for(World world : Bukkit.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(belongToPlayer(owner, entity)) {
                    entity.remove();
                }
            }
        }
    }

    public void callPet(Player owner) {
        despawnPet(owner);
        spawnPet(owner);
    }

    public boolean hasPet(Player owner) {
        return plugin.getPetData().getPetType(owner) != null;
    }

    public boolean isPet(Entity entity) {
        return plugin.getPetData().getData(entity) != null;
    }

    public boolean belongToPlayer(Player owner, Entity entity) {
        return isPet(entity) && plugin.getPetData().getData(entity).contains(owner.getUniqueId().toString());
    }

    public boolean isPetNearby(Player owner) {
        for(Entity entity : owner.getWorld().getEntities()) {
            if(belongToPlayer(owner, entity)) {
                return true;
            }
        }
        return false;
    }

    // To improve
    private void setupPet(LivingEntity entity, Player owner) {
        String customName = plugin.getPetData().getName(owner) + " " + plugin.getConfig().getString("pet-settings.level-format").replace("%level%", String.valueOf(plugin.getPetData().getPetLevel(owner)));
        boolean babyEnabled = plugin.getConfig().getBoolean("pet-settings.baby");
        double health = plugin.getPetData().getMaxHealth(owner);

        plugin.getPetData().addData(owner, entity);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        entity.setHealth(health);
        entity.setCustomNameVisible(true);
        entity.setCustomName(Utils.color(customName));

        if(entity.getType() == EntityType.SHEEP) ((Sheep) entity).setColor(DyeColor.WHITE);
        if(entity instanceof Ageable ageableEntity && babyEnabled) {
            ageableEntity.setBaby();
            ageableEntity.setAgeLock(true);
        }
    }
}
