package edgruberman.bukkit.commandlogger;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if (!new File(this.getDataFolder(), "config.yml").isFile()) this.saveDefaultConfig();
        this.reloadConfig();
        this.setLoggingLevel();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private void setLoggingLevel() {
        this.reloadConfig();
        final String name = this.getConfig().getString("logLevel", "INFO");
        Level level;
        try { level = Level.parse(name); } catch (final Exception e) {
            level = Level.INFO;
            this.getLogger().warning("Unrecognized java.util.logging.Level in \"" + this.getDataFolder().getPath() + "\\config.yml\"; logLevel: " + name);
        }

        // Only set the parent handler lower if necessary, otherwise leave it alone for other configurations that have set it.
        for (final Handler h : this.getLogger().getParent().getHandlers())
            if (h.getLevel().intValue() > level.intValue()) h.setLevel(level);

        this.getLogger().setLevel(level);
        this.getLogger().log(Level.CONFIG, "Logging level set to: " + this.getLogger().getLevel());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        String message = (event.getPlayer().isOp() ? this.getConfig().getString("operator") : this.getConfig().getString("player"));
        if (message == null || message.length() == 0) return;

        message = String.format(message, event.getPlayer().getName(), event.getMessage().substring(1));
        this.getLogger().log(Level.INFO, message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerCommand(final ServerCommandEvent event) {
        String message = this.getConfig().getString("console");
        if (message == null || message.length() == 0) return;

        message = String.format(message, event.getSender().getName(), event.getCommand());
        this.getLogger().log(Level.FINE, message);
    }

}
