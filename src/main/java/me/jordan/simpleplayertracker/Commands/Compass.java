package me.jordan.simpleplayertracker.Commands;

import me.jordan.simpleplayertracker.Main;
import me.jordan.simpleplayertracker.Util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.jordan.simpleplayertracker.Util.Utils.enableAdminDebug;

public class Compass implements CommandExecutor{

    private Main plugin;

    public Compass(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("ptcompass").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("YOU'RE NOT A PLAYER CANT EXECUTE.");
            return true;
        }

        Player p = (Player) sender;
        enableAdminDebug(p,true);
        if (!p.isOp()) return false; // if we arent op this command is unusable!

        ItemStack trackerCompass = new ItemStack(Material.COMPASS);
        ItemMeta meta = trackerCompass.getItemMeta();

        // Set display name and lore
        meta.setDisplayName(Utils.color("&ePermanent Tracking Compass"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7Track players in the same world"));
        lore.add(Utils.color("&bSHIFT-Right-click to select target"));
        meta.setLore(lore);
        trackerCompass.setItemMeta(meta);

        // Give the compass
        p.getInventory().addItem(trackerCompass);
        p.sendMessage(ChatColor.BLUE + "You have been given the Player Tracker Compass!");
        return true;
    }

}
