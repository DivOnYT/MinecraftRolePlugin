package fr.div.roleplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class OnPlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.JUNGLE_SIGN) {
                Sign sign = (Sign) block.getState();

                Player player = event.getPlayer();
                Location location = new Location(player.getWorld(), 444.08, 57.00, 439.50 , 810, 3);
                player.sendMessage(ChatColor.YELLOW + "[Client] " + "Starting warp");
                player.teleport(location);
            }
        }
    }
}
