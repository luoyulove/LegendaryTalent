package luoyu.legendarytalent.Talent;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TalentConfig {
    private File configFile;
    private FileConfiguration config;
    private Map<String, Talent> talents = new HashMap<>();

    public TalentConfig(Plugin plugin) {
        // 初始化
        configFile = new File(plugin.getDataFolder(), "天赋.yml");
        if (!configFile.exists()) {
            plugin.saveResource("天赋.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // 加载
        loadTalentConfig();
    }

    private void loadTalentConfig() {
        ConfigurationSection talentSection = config.getConfigurationSection("天赋配置");
        if (talentSection != null) {
            for (String talentKey : talentSection.getKeys(false)) {
                ConfigurationSection talentConfig = talentSection.getConfigurationSection(talentKey);
                if (talentConfig != null) {
                    String name = talentConfig.getString("天赋");
                    String description = talentConfig.getString("描述");
                    ConfigurationSection effectsConfig = talentConfig.getConfigurationSection("属性");
                    Map<String, Double> effects = new HashMap<>();
                    if (effectsConfig != null) {
                        for (String effectKey : effectsConfig.getKeys(false)) {
                            double value = effectsConfig.getDouble(effectKey);
                            effects.put(effectKey, value);
                            System.out.println("解析了天赋配置");
                            System.out.println("解析: " + effectKey + "/" + value);
                        }
                    }
                    talents.put(talentKey, new Talent(name, description, effects));
                }
            }
        }
    }

    public Talent getTalent(String key) {
        return talents.get(key);
    }

    public Map<String, Talent> getAllTalents() {
        return talents;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



