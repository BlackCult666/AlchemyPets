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
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-here.pet-called")));
    }

    @Subcommand("away")
    @CommandPermission("alchemypets.pet.away")
    public void onPetAway(Player player) {
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-away.sent-away")));
    }

    @Subcommand("name")
    @CommandPermission("alchemypets.pet.name")
    public void onPetName(Player player, String[] args) {}

    @Subcommand("change")
    @CommandPermission("alchemypets.pet.change")
    public void onPetChange(Player player) {
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
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-upgrades")));
    }

    @Subcommand("remove")
    @CommandPermission("alchemypets.pet.remove")
    public void onPetRemove(Player player) {
        player.sendMessage(Utils.color(plugin.getConfigFile().getString("messages.pet-remove.removed")));
    }

    @HelpCommand
    public void onHelp(Player player) {
        for(String line : plugin.getConfigFile().getStringList("messages.pet-help")) {
            player.sendMessage(Utils.color(line));
        }
    }
}
