package me.blackcult.alchemypets.managers;

import me.blackcult.alchemypets.AlchemyPets;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class FileManager {
    private AlchemyPets plugin;

    private File file;
    private FileConfiguration fileConfig;

    public FileManager(AlchemyPets plugin, String fileName) {
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            fileConfig.save(file);
        } catch (Exception e) {
            plugin.getLogger().warning("Error while saving pet file..");
        }
    }

    public void reloadConfig() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String path, Object value) {
        fileConfig.set(path, value);
    }

    public String getString(String path) {
        return fileConfig.getString(path);
    }

    public List<String> getStringList(String path) {
        return fileConfig.getStringList(path);
    }

    public Integer getInt(String path) {
        return fileConfig.getInt(path);
    }

    public Double getDouble(String path) {
        return fileConfig.getDouble(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return fileConfig.getConfigurationSection(path);
    }

}