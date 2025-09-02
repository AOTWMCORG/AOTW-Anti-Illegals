package com.example.illegalitems;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IllegalItemsPlugin extends JavaPlugin implements Listener {

    private final Set<Material> bannedItems = new HashSet<>();
    private final Set<String> trustedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        // Save default config if not exists
        saveDefaultConfig();

        // Load config
        loadConfig();

        // Register events
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("IllegalItemsPlugin enabled!");
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();

        // Load banned items
        List<String> bannedList = config.getStringList("banned-items");
        bannedItems.clear();
        for (String itemName : bannedList) {
            try {
                bannedItems.add(Material.valueOf(itemName.toUpperCase()));
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid material in config.yml: " + itemName);
            }
        }

        // Load trusted players
        List<String> trustedList = config.getStringList("trusted-players");
        trustedPlayers.clear();
        trustedPlayers.addAll(trustedList);
    }

    private boolean isIllegal(ItemStack item) {
        return item != null && bannedItems.contains(item.getType());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (isIllegal(item)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cThat item is not allowed!");
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (isIllegal(item)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cThat item is not allowed!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Remove illegal items from inventory
        event.getPlayer().getInventory().forEach(item -> {
            if (isIllegal(item)) {
                event.getPlayer().getInventory().remove(item);
            }
        });

        // Give perks to trusted players
        if (trustedPlayers.contains(event.getPlayer().getName())) {
            event.getPlayer().sendMessage("§aWelcome back, trusted player!");
            event.getPlayer().setAllowFlight(true); // Example perk
        }
    }
}
