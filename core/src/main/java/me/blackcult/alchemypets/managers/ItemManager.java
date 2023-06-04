package me.blackcult.alchemypets.managers;

import me.blackcult.alchemypets.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemManager {
    private final Material material;
    private final String name;
    private final String slots;
    private final List<String> lore;
    private final int price;

    public ItemManager(FileManager fileManager, String path) {
        this.material = Material.valueOf(fileManager.getString(path + ".material"));
        this.name = fileManager.getString(path + ".name");
        this.slots = fileManager.getString(path + ".slot");
        this.lore = fileManager.getStringList(path + ".lore");
        this.price = fileManager.getInt(path + ".price");
    }

    public void setItem(Inventory inventory, ItemStack item) {
        int slotsLength = slots.split("-").length;
        if(slotsLength == 1) {
            inventory.setItem(Integer.parseInt(slots) - 1, item);
            return;
        }
        int fromSlot = Integer.parseInt(slots.split("-")[0]);
        int toSlot = Integer.parseInt(slots.split("-")[1]);

        for(int i = fromSlot; i <= toSlot; i++) {
            inventory.setItem(i - 1, item);
        }
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(Utils.color(name).replaceAll("%price%", String.valueOf(price)));
        meta.setLore(Utils.color(lore).stream().map(s -> s.replaceAll("%price%", String.valueOf(price))).collect(Collectors.toList()));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
