package luoyu.legendarytalent;

import org.bukkit.plugin.java.JavaPlugin;

public final class LegendaryTalent extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("【LegendaryTalent】启动");

    }

    @Override
    public void onDisable() {
        System.out.println("【LegendaryTalent】关闭");
    }
}
