

package fr.div.roleplugin;

import fr.div.roleplugin.Badge;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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
    JDA jda;

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
        jda = discordRolesPlugin.jda;
        Player player = event.getPlayer();
        String role = getRole(player.getName());
        String message = event.getMessage();

        TextChannel channel = jda.getTextChannelById("1100491285124632677");
        
        playerBadge = (String) badges.getBadge(role); // getting the actual player badge






        // Define chat format based on the player's role
        if (role.equals("Developer")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.blue)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.BLUE + "[Developer] " + ChatColor.BOLD + ChatColor.BLUE + "%s: %s");

        } else if (role.equals("Moderator")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.yellow)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.YELLOW + "[Moderator] " + ChatColor.BOLD + ChatColor.YELLOW +"%s: %s");

        } else if (role.equals("Super-Moderator")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("-- [Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.ORANGE)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.GOLD + "[Super-Moderator] " + ChatColor.BOLD + ChatColor.GOLD +"%s: %s");

        }else if (role.equals("Head-Admin")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.RED)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.DARK_RED + "[Head-Admin] " + ChatColor.BOLD + ChatColor.DARK_RED +"%s: %s");

        } else if (role.equals("Owner")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("-- " + "[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.magenta)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "O" + ChatColor.RED + "w" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "e" + ChatColor.BLUE + "r" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.BOLD + ChatColor.AQUA +"%s: %s");

        } else if (role.equals("Builder")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.cyan)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.AQUA + "[Builder] " + ChatColor.BOLD + ChatColor.AQUA +"%s: %s");

        } else if (role.equals("Admin")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("-- " + "[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.RED)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.RED + "[Admin] " + ChatColor.BOLD + ChatColor.RED +"%s: %s");

        } else if (role.equals("Wastelander")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.GRAY)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.DARK_GRAY + "[Wastelander] " + "%s: %s");

        } else if (role.equals("Server Booster")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.PINK)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.LIGHT_PURPLE + "[Server Booster] " + "%s: %s");

        } else if(role.equals("Faction Leader")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.pink)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.DARK_PURPLE + "[Faction Leader] " + "%s: %s");

        }else if(role.equals("Beta Tester")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.cyan)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.AQUA + "[Beta Tester] " + "%s: %s");

        }else if(role.equals("In-Test")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("[Chat]")
                    .setDescription(message)
                    .setFooter("[Sent by]: " + player.getName() + " -- [Highest role]: " + getRole(player.getName()))
                    .setColor(Color.cyan)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

            event.setFormat(playerBadge + ChatColor.AQUA + "[In-Test (Staff, Dev, Builder)] " + "%s: %s");

        }
    }



}

