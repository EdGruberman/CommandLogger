package edgruberman.bukkit.commandlogger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onLoad() {
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
        Logger logger = this.getServer().getLogger();

        String message = "%1$s tried command: %2$s";
        if (event.getPlayer().isOp()) message = "%1$s issued server command: %2$s";
        message = String.format(message, event.getPlayer().getName(), event.getMessage().substring(1));

        logger.log(Level.INFO, message);
    }

}