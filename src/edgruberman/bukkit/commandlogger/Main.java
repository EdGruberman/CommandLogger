package edgruberman.bukkit.commandlogger;

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
        this.saveDefaultConfig();
        this.reloadConfig();
        this.setLogLevel(this.getConfig().getString("logLevel"));
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        String message = this.getConfig().getString((event.getPlayer().isOp() ? "operator" : "player"));
        if (message == null || message.length() == 0) return;

        message = String.format(message, event.getPlayer().getName(), event.getMessage().substring(1));
        this.getLogger().info(message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerCommand(final ServerCommandEvent event) {
        String message = this.getConfig().getString("console");
        if (message == null || message.length() == 0) return;

        message = String.format(message, event.getSender().getName(), event.getCommand());
        this.getLogger().fine(message);
    }

    private void setLogLevel(final String name) {
        Level level;
        try { level = Level.parse(name); } catch (final Exception e) {
            level = Level.INFO;
            this.getLogger().warning("Log level defaulted to " + level.getName() + "; Unrecognized java.util.logging.Level: " + name);
        }

        // Only set the parent handler lower if necessary, otherwise leave it alone for other configurations that have set it
        for (final Handler h : this.getLogger().getParent().getHandlers())
            if (h.getLevel().intValue() > level.intValue()) h.setLevel(level);

        this.getLogger().setLevel(level);
        this.getLogger().config("Log level set to: " + this.getLogger().getLevel());
    }

}
