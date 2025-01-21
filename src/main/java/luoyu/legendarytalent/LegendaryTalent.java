package luoyu.legendarytalent;

import com.gmail.maccaronne.legendarypowers.LegendaryPowers;
import com.gmail.maccaronne.legendarypowers.effects.PStat;
import luoyu.legendarytalent.Talent.Talent;
import luoyu.legendarytalent.Talent.TalentConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class LegendaryTalent extends JavaPlugin {

    private TalentConfig talentConfig;
    private static LegendaryPowers instance;

    @Override
    public void onEnable() {
        System.out.println("【LegendaryTalent】启动");
        talentConfig = new TalentConfig(this);
    }

    @Override
    public void onDisable() {
        System.out.println("【LegendaryTalent】关闭");
    }

    public static LegendaryPowers getInstance() {
        if (instance == null) {
            instance = JavaPlugin.getPlugin(LegendaryPowers.class);
        }
        return instance;
    }

    public TalentConfig getTalentConfig(){
        return talentConfig;
    }

    public void updateTalentStat(Player player, String talentKey, int talentLevel){
        PStat stat = LegendaryPowers.getPStat(player);

        Talent talent = talentConfig.getTalent(talentKey);
        if (talent != null && talentLevel > 0){
            Map<String, Double> effects = talent.getEffects();
            for (Map.Entry<String, Double> entry : effects.entrySet()) {
                String stats = entry.getKey();
                Double value = entry.getValue();
                Double[] values = {value * talentLevel};
                stat.addValue("talent", stats, values);
            }
        }
        Bukkit.getScheduler().runTask(this, () -> stat.updateTrigger(player));
    }
}