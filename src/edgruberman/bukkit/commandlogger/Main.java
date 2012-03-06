package edgruberman.bukkit.commandlogger;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        this.getLogger().setLevel(Level.FINE);
    }

    @Override
    public void onEnable() {
        if (!new File(this.getDataFolder(), "config.yml").isFile()) this.saveDefaultConfig();
        this.reloadConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        String message = (event.getPlayer().isOp() ? this.getConfig().getString("operator") : this.getConfig().getString("player"));
        if (message == null) return;

        message = String.format(message, event.getPlayer().getName(), event.getMessage().substring(1));
        this.getLogger().log(Level.INFO, message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerCommand(final ServerCommandEvent event) {
        String message = this.getConfig().getString("console");
        if (message == null) return;

        message = String.format(message, event.getSender().getName(), event.getCommand());
        this.getLogger().log(Level.FINE, message);
    }

}
