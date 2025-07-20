package luoyu.legendarytalent.Talent;

import com.gmail.maccaronne.MaccaronneUtils;
import com.j256.ormlite.stmt.query.In;
import com.maccaronne.fuckvexview.variablehook.MagicSpellHook;

import luoyu.legendarytalent.LegendaryTalent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TalentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player){
            player = (Player) sender;
        }

        if (args.length == 0) {
            sender.sendMessage("§a可以点击下面的指令快速输入");
            MaccaronneUtils.sendPromptMessage("§a/lt update: §b更新玩家天赋效果", "/lt update ", "点击快速执行指令", player);
            if (player == null) {
                sender.sendMessage("§a/lt update: §b更新玩家天赋效果");
            }
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "update":
                if (player == null) {
                    System.out.println("这个命令必须由玩家执行");
                    break;
                }
                LegendaryTalent.getInstance().updateTalent(player);
                break;
            default:
                sender.sendMessage("未知子命令" + args[0]);
                sender.sendMessage("§a/lt update: §b更新玩家天赋效果");
                break;
        }
        return true;
    }
}
