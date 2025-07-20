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
            MaccaronneUtils.sendPromptMessage("§a/hb starttime: §b开始记录玩家时长", "/hb starttime ", "点击快速执行指令", player);
            MaccaronneUtils.sendPromptMessage("§a/hb endtime: §b结束记录玩家时长", "/hb endtime ", "点击快速执行指令", player);
            MaccaronneUtils.sendPromptMessage("§a/hb checklevel: §b检查玩家等级", "/hb checklevel ", "点击快速执行指令", player);
            if (player == null) {
                sender.sendMessage("§a/hb starttime: §b开始记录玩家时长");
                sender.sendMessage("§a/hb endtime: §b结束记录玩家时长");
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
                sender.sendMessage("§a/lt update: §b开始记录玩家时长");
                break;
        }
        return true;
    }
}
