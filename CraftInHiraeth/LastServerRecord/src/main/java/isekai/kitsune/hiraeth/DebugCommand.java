package isekai.kitsune.hiraeth;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DebugCommand extends Command {
    private HiraethLastServerRecord plugin;

    public DebugCommand(String name, HiraethLastServerRecord plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            player.connect(this.plugin.getProxy().getServerInfo(HiraethLastServerRecord.playerData.get(player)));
        }
    }
}
