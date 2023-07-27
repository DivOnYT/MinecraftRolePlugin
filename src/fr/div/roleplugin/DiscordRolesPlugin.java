package fr.div.roleplugin;
import java.awt.Color;
import java.util.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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
    JDA jda;
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
        jda = discordPlayerJoin.jda;
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

            TextChannel channel = jda.getTextChannelById("1100491285124632677");



            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("-- [Server] --")
                    .setDescription("-- " + "***" +  "Server Start Up!" + "***" + " --")
                    .setFooter("-- --------------------------------- --")
                    .setColor(Color.green)
                    .build();

            channel.sendMessageEmbeds(embed).queue();

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        jda = discordPlayerJoin.jda;




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
                        TextChannel channel = jda.getTextChannelById("1100491285124632677");


                        if(channel != null) {
                            getLogger().info("Sending embed.");

                            MessageEmbed embed = new EmbedBuilder()
                                    .setTitle("-- [Development Announcement] --")
                                    .setDescription("-- " + "**" +  text + "**" + " --")
                                    .setFooter("-- " + "Announced by: " + sender.getName() + " --")
                                    .setColor(Color.blue)
                                    .build();

                            if (channel.getLatestMessageIdLong() != 0 && channel.retrieveMessageById(channel.getLatestMessageIdLong()).complete().getEmbeds().contains(embed)) {
                                return true;
                            } else {
                                channel.sendMessageEmbeds(embed).queue();
                            }
                            getLogger().info("Embed sent.");

                        }else{

                            getLogger().info("This channel was not found.");

                        }






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
                        TextChannel channel = jda.getTextChannelById("1100491285124632677");


                        if(channel != null) {
                            getLogger().info("Sending embed.");




                            MessageEmbed embed = new EmbedBuilder()
                                    .setTitle("-- [Server Announcement] --")
                                    .setDescription("-- " + "**" +  text + "**" + " --")
                                    .setFooter("-- " + "Announced by: " + sender.getName() + " --")
                                    .setColor(Color.RED)
                                    .build();

                            if (channel.getLatestMessageIdLong() != 0 && channel.retrieveMessageById(channel.getLatestMessageIdLong()).complete().getEmbeds().contains(embed)) {
                                return true;
                            } else {
                                channel.sendMessageEmbeds(embed).queue();
                            }


                            getLogger().info("Embed sent.");

                        }else{

                            getLogger().info("This channel was not found.");

                        }






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
                    Guild guild = jda.getGuildById("886233018895843328");


                    guild.retrieveMemberById(args[0]).queue(member -> {
                        if (member != null) {
                            Random rand = new Random();
                            int num = rand.nextInt(900000) + 100000;

                            getLogger().info(member.toString());
                            User user = member.getUser();
                            player.sendMessage(user.getName());

                            awaitingVerification.add(player.getName() + ": " + num);

                            getLogger().info(awaitingVerification.toString());


                            user.openPrivateChannel().queue(channel -> {
                                channel.sendMessage("Hello, " + user.getName() + "! " + "Your verification code is: " + num + " , this code will expire in 5 minutes, if you did not request this then please just ignore this message. You can also report it to our server at https://discord.gg/nukamc-minecraft-server-886233018895843328 , verification started by: " + player.getName()).queue();
                                player.sendMessage(ChatColor.GREEN + "[Verification] Verification code sent to: " + user.getName());
                                getLogger().info("Starting verification for: " + player.getName() + "Discord account: (" + user.getName() + ": " + user.getId() + ")");
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if(awaitingVerification.contains(player.getName() + ": " + num)) {

                                            awaitingVerification.remove(player.getName() + ": " + num);

                                            getLogger().info("Players awaiting verification: " + awaitingVerification.toString());
                                            getLogger().info("Removed: " + player.getName() + ": " + num + " , from awaiting verification. Reason: Time expired.");

                                        }

                                    }
                                }, 5 * 60 * 1000);
                            });
                        }
                    });

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


                    Guild guild = jda.getGuildById("886233018895843328");


                    guild.retrieveMemberById(args[0]).queue(member -> {
                        if (member != null) {
                            Role highestRole = member.getRoles().stream().min((r1, r2) -> r2.getPosition() - r1.getPosition()).orElse(null);
                            player.sendMessage(ChatColor.RED + "[Verification] Refreshing roles...");
                            if(highestRole != null) {

                                if(highestRole.getName().equals("Developer")) {



                                    roleSystem.addRole(player.getName(), "Developer");

                                    player.setPlayerListName(ChatColor.BLUE + "[Developer] " + ChatColor.BLUE + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                } else if (highestRole.getName().equals("Moderator")) {

                                    roleSystem.addRole(player.getName(), "Moderator");

                                    player.setPlayerListName(ChatColor.YELLOW + "[Moderator] " + ChatColor.YELLOW + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Super Moderator")) {

                                    roleSystem.addRole(player.getName(), "Super-Moderator");

                                    player.setPlayerListName(ChatColor.GOLD + "[Super-Moderator] " + ChatColor.GOLD + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Owner")) {

                                    roleSystem.addRole(player.getName(), "Owner");

                                    player.setPlayerListName(ChatColor.BLUE + "[" + ChatColor.LIGHT_PURPLE + "O" + ChatColor.RED + "w" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "e" + ChatColor.BLUE + "r" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.AQUA + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Admin")) {

                                    roleSystem.addRole(player.getName(), "Admin");

                                    player.setPlayerListName(ChatColor.RED + "[Admin] " + ChatColor.RED +  player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Head Admin")) {

                                    roleSystem.addRole(player.getName(), "Head-Admin");

                                    player.setPlayerListName(ChatColor.DARK_RED + "[Head-Admin] " + ChatColor.DARK_RED + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Builder")) {

                                    roleSystem.addRole(player.getName(), "Builder");

                                    player.setPlayerListName(ChatColor.AQUA + "[Builder] " + ChatColor.AQUA + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if (highestRole.getName().equals("Server Booster")) {

                                    roleSystem.addRole(player.getName(), "Server Booster");

                                    player.setPlayerListName(ChatColor.LIGHT_PURPLE + "[Server Booster] " + ChatColor.LIGHT_PURPLE + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                } else if(highestRole.getName().equals("Faction Leader")) {

                                    roleSystem.addRole(player.getName(), "Faction Leader");

                                    player.setPlayerListName(ChatColor.DARK_PURPLE + "[Faction Leader] " + ChatColor.DARK_PURPLE + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else if(highestRole.getName().equals("Beta Tester")) {

                                    roleSystem.addRole(player.getName(), "Beta Tester");

                                    player.setPlayerListName(ChatColor.AQUA + "[Beta Tester] " + ChatColor.AQUA + player.getName());
                                    player.sendMessage(ChatColor.GREEN + "[Verification] Roles Refreshed!");

                                }else{
                                    player.setPlayerListName(ChatColor.DARK_GRAY + "[Wastelander] " + ChatColor.DARK_GRAY + player.getName());

                                    player.sendMessage(ChatColor.RED + "No role found other than the role: Wastelander . If you know you have a higher role please contact support at ");

                                }

                            }
                        }

                    });


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

            TextChannel channel = jda.getTextChannelById("1100491285124632677");



            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("-- [Server] --")
                    .setDescription("-- " + "***" +  "Server Shut Down!" + "***" + " --")
                    .setFooter("-- --------------------------------- --")
                    .setColor(Color.green)
                    .build();

            channel.sendMessageEmbeds(embed).queue();




        }

        // Shutdown JDA
        discordPlayerJoin.jda.shutdown();



    }





}
