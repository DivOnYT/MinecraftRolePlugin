package fr.div.nukamcplugin;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public final class DiscordRolesPlugin extends JavaPlugin {
    public RoleSystem roleSystem;
    DiscordPlayerJoin discordPlayerJoin;

    OnPlayerInteract onPlayerInteract;
    public Inventory premiumMenu;
    List<String> awaitingVerification;

    List<String> verifiedPlayers;
    String fileName = "verified_players.txt";
    @Override
    public void onEnable() {


        awaitingVerification = new ArrayList<>();
        verifiedPlayers = new ArrayList<>();
        // Plugin startup logic
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                verifiedPlayers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Inventory Menu
        premiumMenu = Bukkit.createInventory(null, 9, "Premium Menu");


        ItemStack fly = new ItemStack(Material.ELYTRA);
        ItemMeta flymeta = fly.getItemMeta();
        flymeta.setDisplayName("Fly");
        fly.setItemMeta(flymeta);
        premiumMenu.setItem(1, fly);

        roleSystem = new RoleSystem(this);
        discordPlayerJoin = new DiscordPlayerJoin(roleSystem, this);
        onPlayerInteract = new OnPlayerInteract();
        // Register listener

        getServer().getPluginManager().registerEvents(discordPlayerJoin, this);
        getServer().getPluginManager().registerEvents(roleSystem, this);
        getServer().getPluginManager().registerEvents(onPlayerInteract, this);
        getLogger().info(ChatColor.GREEN + "-- --------------------------------- --");
        getLogger().info(ChatColor.BLUE + "-- DiscordRolesPlugin is enabled --");
        getLogger().info(ChatColor.BLUE + "-- Author: HuggeTheDev --");
        getLogger().info(ChatColor.GREEN + "-- --------------------------------- --");


        synchronized (this){

            try {
                this.wait(4000, 0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {




        if (cmd.getName().equalsIgnoreCase("hello")) {
            sender.sendMessage(ChatColor.GREEN + "Hello, " + sender.getName() + "!");
            return true;
        }else if (cmd.getName().equalsIgnoreCase("devannounce")) {

            if(args.length > 0) {


                if(roleSystem.getRole(sender.getName()).equals("Developer")) {

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        String text = String.join(" ", args);
                        player.sendMessage(ChatColor.BLUE + "-- ----------------------------- --");
                        player.sendMessage(ChatColor.AQUA + "-- [Development Announcement] --");
                        player.sendMessage(ChatColor.AQUA + "-- " + text + " --");
                        player.sendMessage(ChatColor.AQUA + "-- " + "Announced by: " + sender.getName() + " --");
                        player.sendMessage(ChatColor.BLUE + "-- ----------------------------- --");

                        getLogger().info("A development announcement has been triggered by: " + sender.getName() + ": "+ text);


                    }




                }else {
                    getLogger().warning(sender.getName() + " Is trying to call a command that he/she does not have permission to call, get ready to potentially get pinged about it.");
                    sender.sendMessage(ChatColor.RED + "[Server] " + "You do not have authority to call this command. Please speak with the server staff if you think you should have access to this command.");

                }



            }else {
                sender.sendMessage(ChatColor.RED + "[Server] " + "Usage: /devannounce <What To Say as the developer>");

            }
            return true;


        }else if (cmd.getName().equalsIgnoreCase("serverannounce")) {

            if(args.length > 0) {


                if(roleSystem.getRole(sender.getName()).equals("Developer") || roleSystem.getRole(sender.getName()).equals("Owner") || roleSystem.getRole(sender.getName()).equals("Moderator") || roleSystem.getRole(sender.getName()).equals("Super-Moderator") || roleSystem.getRole(sender.getName()).equals("Admin") || roleSystem.getRole(sender.getName()).equals("Head-Admin")) {

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        String text = String.join(" ", args);
                        player.sendMessage(ChatColor.DARK_RED + "-- ----------------------------- --");
                        player.sendMessage(ChatColor.RED + "-- [Server Announcement] --");
                        player.sendMessage(ChatColor.RED + "-- " + text + " --");
                        player.sendMessage(ChatColor.RED + "-- " + "Announced by: " + sender.getName() + " --");
                        player.sendMessage(ChatColor.DARK_RED + "-- ----------------------------- --");

                        getLogger().info("A server announcement has been triggered by: " + sender.getName() + ": "+ text);



                    }




                }else {
                    getLogger().warning(sender.getName() + " Is trying to call a command that he/she does not have permission to call, get ready to potentially get pinged about it.");
                    sender.sendMessage(ChatColor.RED + "[Server] " + "You do not have authority to call this command. Please speak with the server staff if you think you should have access to this command.");

                }



            }else {
                sender.sendMessage(ChatColor.RED + "[Server] " + "Usage: /serverannounce <What To Say as the server>");

            }
            return true;


        }else if(cmd.getName().equalsIgnoreCase("requestAdmin")) {



            if(args.length > 0) {


                for (Player player : Bukkit.getOnlinePlayers()) {

                    if(roleSystem.getRole(player.getName()).equals("Admin") || roleSystem.getRole(player.getName()).equals("Moderator")) {

                        if(sender instanceof Player) {
                            sender.sendMessage(ChatColor.YELLOW + "[Admin Support] " + sender.getName() + ", Help will be with you shortly.");
                            Player requester = (Player) sender;
                            String text = String.join(" ", args);
                            double playerX = requester.getLocation().getX();
                            playerX = Math.round(playerX);

                            double playerY = requester.getLocation().getY();
                            playerY = Math.round(playerY);

                            double playerZ = requester.getLocation().getZ();
                            playerZ = Math.round(playerZ);

                            player.sendMessage(ChatColor.GOLD + sender.getName() + " is requesting help at: X: " + playerX + ", Y: " + playerY + ", Z: " + playerZ + ". Reason for request: " + text);

                        }else {
                            String text = String.join(" ", args);
                            sender.sendMessage(ChatColor.YELLOW + "[Admin Support] " + "Server" + ", Help will be with you shortly.");
                            player.sendMessage(ChatColor.GOLD + "Server" + " is requesting help at: X: " + "Nowhere" + ", Y: " + "Nowhere" + ", Z: " + "Nowhere" + ". Reason for request: " + text);

                        }

                    }

                }

            }

        }else if(cmd.getName().equalsIgnoreCase("premiummenu")) {
            if (sender instanceof Player) {
                // Open the menu for the player
                Player player = (Player) sender;
                player.openInventory(premiumMenu);
                return true;
            }
        }else if(cmd.getName().equalsIgnoreCase("startverification")) {

            Player player = (Player) sender;



            if(args.length > 0) {

                if (!verifiedPlayers.contains(player.getName() + ": " + args[0] + " :")) {

                    getLogger().info(args[0]);

                }else{

                    player.sendMessage(ChatColor.RED + "[Verification] You are already verified. Do /refreshroles <Your discord id> to refresh your in-game roles.");

                }
            }else{

                player.sendMessage("Command Usage is: /startverification <discord id>");

            }

        }else if(cmd.getName().equalsIgnoreCase("verify")) {
            Player player = (Player) sender;

            if (args.length > 0) {

                if (awaitingVerification.contains(player.getName() + ": " + args[0])) {

                    verifiedPlayers.add(player.getName() + ": " + args[1] + " :");
                    awaitingVerification.remove(player.getName() + ": " + args[0]);


                }else{

                    player.sendMessage(ChatColor.RED + "[Verification] Verification failed: You have either not started the verification or the verification code is wrong, please try again later.");

                }

            }else{

                player.sendMessage("Usage: /verify <code> <discord id>");

            }
        } else if (cmd.getName().equalsIgnoreCase("refreshRoles")) {

            Player player = Bukkit.getPlayer(sender.getName());
            if(args.length > 0) {

                if(verifiedPlayers.contains(player.getName() + ": " + args[0] + " :")) {


                }else {

                    player.sendMessage(ChatColor.RED + "[Verification] You are not verified yet or the discord id provided is wrong, please try again later.");

                }

            }



        }else if(cmd.getName().equalsIgnoreCase("manualverify")) {

            Player player = (Player) sender;

            if(roleSystem.getRole(player.getName()).equals("Developer")){

                if(args.length > 0) {

                    verifiedPlayers.add(args[0] + ": " + args[1] + " :");

                }else {

                    player.sendMessage(ChatColor.RED + "[Verification] Usage: /manualverify <playername> <discordid>");

                }



            }else{

                player.sendMessage(ChatColor.RED + "[Server] You do not have permission to use this command.");

            }


        }else if(cmd.getName().equalsIgnoreCase("resetverifiedplayers")){

            Player player = (Player) sender;

            if(roleSystem.getRole(player.getName()).equals("Developer")){

                if(args.length > 0) {

                    verifiedPlayers.remove(args[0] + ": " + args[1] + " :");

                }else {

                    player.sendMessage(ChatColor.RED + "[Verification] Usage: /resetverifiedplayers <playername> <discordid>");

                }



            }else{

                player.sendMessage(ChatColor.RED + "[Server] You do not have permission to use this command.");

            }


        }else if(cmd.getName().equalsIgnoreCase("refreshallroles")){

            Player player = (Player) sender;

            if(roleSystem.getRole(player.getName()).equals("Developer")) {

                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                players.sort(Comparator.comparing(player1 -> {
                    String playerName = player1.getName();

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

            }else {

                getLogger().warning(sender.getName() + " Is trying to call a command that he/she does not have permission to call, get ready to potentially get pinged about it.");
                sender.sendMessage(ChatColor.RED + "[Server] " + "You do not have authority to call this command. Please speak with the server staff if you think you should have access to this command.");

            }



        }else{

            Player player = (Player) sender;

            player.sendMessage(ChatColor.RED + "[Server] Command is invalid. (No command with that name exists)");

        }


        return false;

    }

    @Override
    public void onDisable() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String player : verifiedPlayers) {
                writer.write(player);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized (this){

            try {
                this.wait(4000, 0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }




        }

        // Shutdown JDA



    }





}
