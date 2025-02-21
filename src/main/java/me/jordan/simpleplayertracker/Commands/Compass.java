package me.jordan.simpleplayertracker.Commands;

import me.jordan.simpleplayertracker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        p.getInventory().addItem(new ItemStack(Material.COMPASS));
        p.sendMessage(ChatColor.BLUE+"You have been given the tracker!");
        return true;
    }

}
