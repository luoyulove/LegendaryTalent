package luoyu.legendarytalent.Talent;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class TalentConfig {
    private File configFile;
    private FileConfiguration config;

    public TalentConfig(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "天赋配置.yml");
        if (!configFile.exists()) {
            plugin.saveResource("天赋配置.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadTalentConfig() {
        ConfigurationSection talentSection = config.getConfigurationSection("天赋");
    }
}
