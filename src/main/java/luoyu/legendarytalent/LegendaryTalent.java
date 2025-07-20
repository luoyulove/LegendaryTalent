package luoyu.legendarytalent;

import com.gmail.maccaronne.legendarypowers.LegendaryPowers;
import com.gmail.maccaronne.legendarypowers.effects.PStat;
import luoyu.legendarytalent.Talent.Talent;
import luoyu.legendarytalent.Talent.TalentCommand;
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

        TalentCommand command = new TalentCommand();
        this.getCommand("lt").setExecutor(new TalentCommand());
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

    public void updateTalentStat(Player player, String talentKey, PStat stat, Boolean setMaxHealth){
        Talent talent = talentConfig.getTalent(talentKey);
        Double talentLevel = getVariable("天赋_"+talentKey, player);

        stat.resetMap("talent");

        if (talent != null && talentLevel > 0){
            Map<String, Double> effects = talent.getEffects();
            for (Map.Entry<String, Double> entry : effects.entrySet()) {
                String stats = entry.getKey();
                Double value = entry.getValue();
                Double[] values = {value * talentLevel};
                stat.addValue("talent", stats, values);
            }
        }
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            stat.updateTrigger(player);
            if (setMaxHealth) player.setHealth(player.getMaxHealth());
        }, 20);
    }
    @EventHandler(priority = EventPriority.NORMAL)
        public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            PStat stat = LegendaryPowers.getPStat(player);
            Map<String, Talent> allTalents = talentConfig.getAllTalents();
            for (String talentKey : allTalents.keySet()){
                updateTalentStat(player, talentKey, stat, true);
            }
        }, 60);
    }

    public void updateTalent(Player player){
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            PStat stat = LegendaryPowers.getPStat(player);
            Map<String, Talent> allTalents = talentConfig.getAllTalents();
            for (String talentKey : allTalents.keySet()){
                updateTalentStat(player, talentKey, stat, false);
            }
        }, 60);
    }
}