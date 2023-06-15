package fr.div.roleplugin;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DiscordPlayerJoin implements Listener {



    final RoleSystem roleSystem;













    String token = "Your BOT Token HERE";


    public JDA jda = JDABuilder.createLight(token).build();

    DiscordRolesPlugin discordRolesPlugin;
    private int animationIndex = 0;
    private int animationIndex1;

    private String[] animationFrames1 = new String[] {ChatColor.GOLD + "W" + ChatColor.WHITE + "ebsite: nukamc.com","W" + ChatColor.GOLD + "e" + ChatColor.WHITE+ "bsite: nukamc.com", "We" + ChatColor.GOLD + "b" + ChatColor.WHITE + "site: nukamc.com", "Web" + ChatColor.GOLD + "s" + ChatColor.WHITE + "ite: nukamc.com", "Webs" + ChatColor.GOLD + "i" + ChatColor.WHITE+ "te: nukamc.com", "Websi" + ChatColor.GOLD + "t" + ChatColor.WHITE + "e: nukamc.com", "Websit" + ChatColor.GOLD + "e" + ChatColor.WHITE + ": nukamc.com"};

    private String[] animationFrames = new String[] {ChatColor.DARK_RED + "NukaMC" + "\n" + ChatColor.RED + "Welcome",ChatColor.DARK_PURPLE + "NukaMC" + "\n" + ChatColor.BLUE + "Welcome",ChatColor.LIGHT_PURPLE + "NukaMC" + "\n" + ChatColor.LIGHT_PURPLE + "Welcome",ChatColor.DARK_AQUA + "NukaMC" + "\n" + ChatColor.DARK_PURPLE +"Welcome",ChatColor.BLUE + "NukaMC" + "\n" + ChatColor.DARK_RED + "Welcome",ChatColor.DARK_PURPLE + "NukaMC" + "\n" + ChatColor.AQUA + "Welcome",ChatColor.LIGHT_PURPLE + "NukaMC" + "\n" + ChatColor.RED + "Welcome",ChatColor.DARK_AQUA + "NukaMC" + "\n" + ChatColor.RED + "Welcom",ChatColor.BLUE + "NukaMC" + "\n" + ChatColor.RED + "Welco",ChatColor.GOLD + "NukaMC" + "\n" + ChatColor.RED + "Welc",ChatColor.DARK_RED + "NukaMC" + "\n" + ChatColor.RED + "Wel",ChatColor.DARK_PURPLE + "NukaMC" + "\n" + ChatColor.RED + "We",ChatColor.LIGHT_PURPLE + "NukaMC" + "\n" + ChatColor.RED + "W",ChatColor.DARK_AQUA + "NukaMC" + "\n" + ChatColor.RED + "We",ChatColor.LIGHT_PURPLE + "NukaMC" + "\n" + ChatColor.RED + "Wel",ChatColor.BLUE + "NukaMC" + "\n" + ChatColor.RED + "Welc",ChatColor.GOLD + "NukaMC" + "\n" + ChatColor.RED + "Welco",ChatColor.DARK_RED + "NukaMC" + "\n" + ChatColor.RED + "Welcom", ChatColor.GOLD + "NukaMC" + "\n" + ChatColor.RED + "Welcome"};



    public DiscordPlayerJoin(RoleSystem roleSystem, DiscordRolesPlugin discordRolesPlugin) {

        this.roleSystem = roleSystem;

        this.discordRolesPlugin = discordRolesPlugin;




    }

    public void sendTitle(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subtitle) {
        player.sendTitle(title, subtitle, fadeInTime, showTime, fadeOutTime);
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {




        event.getPlayer().sendMessage(ChatColor.GREEN + "Welcome to NukaMC, " + event.getPlayer().getName() + "!");

        Player player = event.getPlayer();

        sendTitle(player, 20, 100, 20, ChatColor.RED + "Hello " + player.getName(), ChatColor.GOLD + "Welcome to the server!");

        String playerName = player.getName();
        
        Badge badge = new Badge();
        String playerBadge;
        
        if (roleSystem.getRole(playerName) == "default") {
        	playerBadge = badge.getBadge("Wastelander");
        } else {
        	playerBadge = badge.getBadge(roleSystem.getRole(playerName));
        }



        if (discordRolesPlugin.verifiedPlayers.contains(playerName)) {

            player.sendMessage(ChatColor.GREEN + "[Verification] You have a discord account connected, do /refreshroles to enable them.");

        }
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());



        if(roleSystem.getRole(playerName).equals("Developer")) {

            player.setPlayerListName(playerBadge + ChatColor.BLUE + "[Developer] " + ChatColor.BLUE + player.getName());
            roleSystem.addRole(playerName, "Developer");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Moderator")) {

            player.setPlayerListName(playerBadge + ChatColor.YELLOW + "[Moderator] " + ChatColor.YELLOW + playerName);
            roleSystem.addRole(playerName, "Moderator");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Super-Moderator")) {

            player.setPlayerListName(playerBadge + ChatColor.GOLD + "[Super-Moderator] " + ChatColor.GOLD + playerName);
            roleSystem.addRole(playerName, "Super-Moderator");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        }else if (roleSystem.getRole(playerName).equals("Head-Admin")) {

            player.setPlayerListName(playerBadge + ChatColor.DARK_RED + "[Head-Admin] " + ChatColor.DARK_RED + playerName);
            roleSystem.addRole(playerName, "Head-Admin");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Owner")) {

            player.setPlayerListName(playerBadge + ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "O" + ChatColor.RED + "w" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "e" + ChatColor.BLUE + "r" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.AQUA + playerName);
            roleSystem.addRole(playerName, "Owner");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Builder")) {

            player.setPlayerListName(playerBadge + ChatColor.AQUA + "[Builder] " + ChatColor.AQUA + playerName);
            roleSystem.addRole(playerName, "Builder");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Admin")) {

            player.setPlayerListName(playerBadge + ChatColor.RED + "[Admin] " + ChatColor.RED +  playerName);
            roleSystem.addRole(playerName, "Admin");
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if (roleSystem.getRole(playerName).equals("Server Booster")) {

            roleSystem.addRole(player.getName(), "Server Booster");

            player.setPlayerListName(playerBadge + ChatColor.LIGHT_PURPLE + "[Server Booster] " + ChatColor.LIGHT_PURPLE + player.getName());
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        } else if(roleSystem.getRole(playerName).equals("Faction Leader")) {

            roleSystem.addRole(player.getName(), "Faction Leader");

            player.setPlayerListName(playerBadge + ChatColor.DARK_PURPLE + "[Faction Leader] " + ChatColor.DARK_PURPLE + player.getName());
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        }else if(roleSystem.getRole(playerName).equals("Beta Tester")) {

            roleSystem.addRole(player.getName(), "Beta Tester");

            player.setPlayerListName(playerBadge + ChatColor.AQUA + "[Beta Tester] " + ChatColor.AQUA + player.getName());
            player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

        }else {

            player.setPlayerListName(playerBadge  + ChatColor.DARK_GRAY + "[Wastelander] " + playerName);
            roleSystem.addRole(playerName, "Wastelander");

        }



        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setPlayerListHeader(ChatColor.DARK_PURPLE + "————————————————————" + "\n" + animationFrames[animationIndex] + " " + player.getName() + "\n" + "\n" + ChatColor.GOLD + "Ping: " + ChatColor.GREEN + player.getPing() + ChatColor.GRAY + "ms" + "\n" + ChatColor.RESET);
                    player.setPlayerListFooter("\n" + ChatColor.YELLOW + "Players: " + Bukkit.getOnlinePlayers().size() + " / 20" + "\n" + "\n" + ChatColor.WHITE + animationFrames1[animationIndex1] + ChatColor.DARK_PURPLE + "\n" + "\n" + "————————————————————");

                }
                players.sort(Comparator.comparing(player1 -> {
                    if(roleSystem.getRole(playerName).equals("Developer")) {


                        return 5;

                    } else if (roleSystem.getRole(playerName).equals("Moderator")) {


                        return 4;

                    } else if (roleSystem.getRole(playerName).equals("Super-Moderator")) {


                        return 3;

                    }else if (roleSystem.getRole(playerName).equals("Head-Admin")) {


                        return 1;

                    } else if (roleSystem.getRole(playerName).equals("Owner")) {

                        return 0;

                    } else if (roleSystem.getRole(playerName).equals("Builder")) {


                        return 6;

                    } else if (roleSystem.getRole(playerName).equals("Admin")) {

                        return 2;

                    } else if (roleSystem.getRole(playerName).equals("Server Booster")) {

                        return 7;

                    } else if(roleSystem.getRole(playerName).equals("Faction Leader")) {

                        return 8;

                    }else if(roleSystem.getRole(playerName).equals("Beta Tester")) {

                        return 9;

                    }else {

                        return 10;
                    }
                }));
                animationIndex = (animationIndex + 1) % animationFrames.length;
                animationIndex1 = (animationIndex1 + 1) % animationFrames1.length;

            }
        }.runTaskTimer(discordRolesPlugin, 0L, 6L);


    }



}
