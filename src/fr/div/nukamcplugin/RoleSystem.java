

package fr.div.nukamcplugin;

import fr.div.nukamcplugin.Badge;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.awt.*;

import java.util.HashMap;



public class RoleSystem implements Listener {



    private final HashMap<String, String> roles; // Map to store roles
    
    private Badge badges;
    private String playerBadge;
    
    DiscordRolesPlugin discordRolesPlugin;

    public RoleSystem(DiscordRolesPlugin discordRolesPlugin) {
        roles = new HashMap<>(); // Initialize the roles map
        badges = new Badge();
        this.discordRolesPlugin = discordRolesPlugin;

    }

    public void addRole(String playerName, String role) {
        roles.put(playerName, role); // Add or update the role for the player
    }

    public String getRole(String playerName) {
        return roles.getOrDefault(playerName, "default"); // Return the player's role, or "default" if not found

    } 
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String role = getRole(player.getName());
        String message = event.getMessage();
        
        playerBadge = (String) badges.getBadge(role); // getting the actual player badge




        // Define chat format based on the player's role
        if (role.equals("Developer")) {

            event.setFormat(playerBadge + ChatColor.BLUE + "[Developer] " + ChatColor.BOLD + ChatColor.BLUE + "%s: %s");

        } else if (role.equals("Moderator")) {

            event.setFormat(playerBadge + ChatColor.YELLOW + "[Moderator] " + ChatColor.BOLD + ChatColor.YELLOW +"%s: %s");

        } else if (role.equals("Super-Moderator")) {

            event.setFormat(playerBadge + ChatColor.GOLD + "[Super-Moderator] " + ChatColor.BOLD + ChatColor.GOLD +"%s: %s");

        }else if (role.equals("Head-Admin")) {

            event.setFormat(playerBadge + ChatColor.DARK_RED + "[Head-Admin] " + ChatColor.BOLD + ChatColor.DARK_RED +"%s: %s");

        } else if (role.equals("Owner")) {

            event.setFormat(playerBadge + ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "O" + ChatColor.RED + "w" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "e" + ChatColor.BLUE + "r" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.BOLD + ChatColor.AQUA +"%s: %s");

        } else if (role.equals("Builder")) {

            event.setFormat(playerBadge + ChatColor.AQUA + "[Builder] " + ChatColor.BOLD + ChatColor.AQUA +"%s: %s");

        } else if (role.equals("Admin")) {

            event.setFormat(playerBadge + ChatColor.RED + "[Admin] " + ChatColor.BOLD + ChatColor.RED +"%s: %s");

        } else if (role.equals("Wastelander")) {

            event.setFormat(playerBadge + ChatColor.DARK_GRAY + "[Wastelander] " + "%s: %s");

        } else if (role.equals("Server Booster")) {

            event.setFormat(playerBadge + ChatColor.LIGHT_PURPLE + "[Server Booster] " + "%s: %s");

        } else if(role.equals("Faction Leader")) {

            event.setFormat(playerBadge + ChatColor.DARK_PURPLE + "[Faction Leader] " + "%s: %s");

        }else if(role.equals("Beta Tester")) {

            event.setFormat(playerBadge + ChatColor.AQUA + "[Beta Tester] " + "%s: %s");

        }else if(role.equals("In-Test")) {

            event.setFormat(playerBadge + ChatColor.AQUA + "[In-Test (Staff, Dev, Builder)] " + "%s: %s");

        }
    }



}

