package edgruberman.bukkit.wrappercommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
    
    private Logger logger;
    
    public PlayerListener(Plugin plugin) {
        this.logger = plugin.getServer().getLogger();
        
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this, Event.Priority.Monitor, plugin);
    }
    
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().isOp()) {
            this.logger.log(Level.INFO, event.getPlayer().getName() + " issued server command: " + event.getMessage().substring(1));
            return;
        }
        
        this.logger.log(Level.INFO, event.getPlayer().getName() + " tried command: " + event.getMessage().substring(1));
    }
}