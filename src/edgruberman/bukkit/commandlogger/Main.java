package edgruberman.bukkit.commandlogger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    private String logAdmin = "%1$s issued server command: %2$s";
    private String logConsole = "%1$s issued server command: %2$s";
    private String logPlayer = "%1$s tried command: %2$s";

    private Logger logger;

    public void onLoad() {
        this.logger = this.getLogger();
        this.logger.setLevel(Level.FINE);
        this.getLogger().log(Level.INFO, "Version " + this.getDescription().getVersion());
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().log(Level.INFO, "Plugin Enabled");
    }

    public void onDisable() {
        this.getLogger().log(Level.INFO, "Plugin Disabled");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        String message = (event.getPlayer().isOp() ? this.logAdmin : this.logPlayer);
        message = String.format(message, event.getPlayer().getName(), event.getMessage().substring(1));
        this.logger.log(Level.INFO, message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerCommand(final ServerCommandEvent event) {
        String message = String.format(this.logConsole, event.getSender().getName(), event.getCommand());
        this.logger.log(Level.FINE, message);
    }

}