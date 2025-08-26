package me.jordan.simpleplayertracker.Listeners;

import me.jordan.simpleplayertracker.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CraftTrackerEvent implements Listener {

    private final Main plugin;

    public CraftTrackerEvent(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack result = event.getInventory().getResult();
        if (result == null || result.getType() != org.bukkit.Material.COMPASS) return;
        if (!result.hasItemMeta()) return;

        ItemMeta meta = result.getItemMeta();
        if (meta.getDisplayName() == null) return;
        if (!meta.getDisplayName().contains("Tracking")) return;

        // Schedule a 1-tick delayed task to update each crafted item individually
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ItemStack[] contents = event.getView().getPlayer().getInventory().getContents();
            NamespacedKey key = new NamespacedKey(plugin, "creation_time");

            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item == null) continue;
                if (item.getType() != org.bukkit.Material.COMPASS) continue;
                if (!item.hasItemMeta()) continue;

                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.getDisplayName() != null && itemMeta.getDisplayName().contains("Tracking")) {
                    // Set a fresh timestamp for this individual item
                    itemMeta.getPersistentDataContainer().set(key, PersistentDataType.LONG, System.currentTimeMillis());
                    item.setItemMeta(itemMeta);
                }
            }
        }, 1L); // 1 tick later
    }
}
