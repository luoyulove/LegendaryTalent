package luoyu.legendarytalent;

import com.gmail.maccaronne.legendarypowers.LegendaryPowers;
import com.gmail.maccaronne.legendarypowers.effects.PStat;
import luoyu.legendarytalent.Talent.Talent;
import luoyu.legendarytalent.Talent.TalentConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import static com.gmail.maccaronne.craftlearner.magicspell.Hooker.getVariable;

public final class LegendaryTalent extends JavaPlugin implements Listener {

    private TalentConfig talentConfig;
    private static LegendaryTalent instance;

    @Override
    public void onEnable() {
        System.out.println("【LegendaryTalent】启动");
        instance = this;
        talentConfig = new TalentConfig(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println("【LegendaryTalent】关闭");
    }

    public static LegendaryTalent getInstance() {
        if (instance == null) {
            instance = JavaPlugin.getPlugin(LegendaryTalent.class);
        }
        return instance;
    }

    public TalentConfig getTalentConfig(){
        return talentConfig;
    }

    public void updateTalentStat(Player player, String talentKey, PStat stat){
        Talent talent = talentConfig.getTalent(talentKey);
        Double talentLevel = getVariable("天赋_"+talentKey, player);

        if (talent != null && talentLevel > 0){
            Map<String, Double> effects = talent.getEffects();
            for (Map.Entry<String, Double> entry : effects.entrySet()) {
                String stats = entry.getKey();
                Double value = entry.getValue();
                Double[] values = {value * talentLevel};
                stat.addValue("talent", stats, values);
                System.out.println("为玩家 "+ player.getName() +" 增加属性，原因: talent / " + "增加属性: " + stats + " 数值: " + values[0]);
            }
        }
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            stat.updateTrigger(player);
        }, 20);
    }
    @EventHandler(priority = EventPriority.NORMAL)
        public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            PStat stat = LegendaryPowers.getPStat(player);
            Map<String, Talent> allTalents = talentConfig.getAllTalents();
            for (String talentKey : allTalents.keySet()){
                updateTalentStat(player, talentKey, stat);
            }
        }, 60);
    }
}