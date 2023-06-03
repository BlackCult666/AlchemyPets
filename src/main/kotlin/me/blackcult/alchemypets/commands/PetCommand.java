package me.blackcult.alchemypets.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.blackcult.alchemypets.AlchemyPets;
import me.blackcult.alchemypets.util.Utils;
import org.bukkit.entity.Player;

@CommandAlias("pet")
@CommandPermission("alchemypets.use")
public class PetCommand extends BaseCommand {

    @Dependency
    private AlchemyPets plugin;

    @Subcommand("here")
    @CommandPermission("alchemypets.pet.here")
    public void onPetCall(Player player) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        plugin.getPetManager().callPet(player);
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-here.pet-called")));
    }

    @Subcommand("away")
    @CommandPermission("alchemypets.pet.away")
    public void onPetAway(Player player) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        if(!plugin.getPetManager().isPetNearby(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-away.not-here")));
            return;
        }
        plugin.getPetManager().despawnPet(player);
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-away.sent-away")));
    }

    @Subcommand("name")
    @CommandPermission("alchemypets.pet.name")
    public void onPetName(Player player, String[] args) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        if(args.length != 1) {
            player.sendMessage(Utils.color(plugin.getConfig().getString("messages.pet-name.correct-usage")));
            return;
        }
        if(!checkNameConditions(player, args[0])) {
            return;
        }
        plugin.getPetData().setPetName(player, args[0]);
        player.sendMessage(Utils.color(plugin.getConfig().getString("messages.pet-name.changed").replaceAll("%name%", Utils.color(args[0]))));
    }

    @Subcommand("change")
    @CommandPermission("alchemypets.pet.change")
    public void onPetChange(Player player) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        plugin.getShopInventory().openInventory(player);
    }

    @Subcommand("shop")
    @CommandPermission("alchemypets.pet.shop")
    public void onPetShop(Player player) {
        plugin.getShopInventory().openInventory(player);
    }

    @Subcommand("upgrades")
    @CommandPermission("alchemypets.pet.upgrades")
    public void onPetUpgrades(Player player, String[] args) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-upgrades")));
    }

    @Subcommand("remove")
    @CommandPermission("alchemypets.pet.remove")
    public void onPetRemove(Player player) {
        if(!plugin.getPetManager().hasPet(player)) {
            player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.no-pet")));
            return;
        }
        plugin.getPetManager().despawnPet(player);
        plugin.getPetData().removePlayerPet(player);
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-remove.removed")));
    }

    @HelpCommand
    public void onHelp(Player player) {
        for(String line : plugin.getConfigFile().getStringList("messages.pet-help")) {
            player.sendMessage(Utils.color(line));
        }
    }

    // To change
    private boolean checkNameConditions(Player player, String name) {
        int maxCharacters = plugin.getConfig().getInt("pet-settings.max-name-length");
        if(name.length() > maxCharacters) {
            player.sendMessage(Utils.color(plugin.getConfig().getString("messages.pet-name.max-characters")).replaceAll("%characters%", String.valueOf(maxCharacters)));
            return false;
        }
        if(!name.matches("^[a-zA-Z0-9&#]*$")) {
            player.sendMessage(Utils.color(plugin.getConfig().getString("messages.pet-name.invalid-name")));
            return false;
        }
        return true;
    }
}
