/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.example.powerups;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLinePickupEvent;
import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerUps extends JavaPlugin implements Listener {

    private HolographicDisplaysAPI holographicDisplaysAPI;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        holographicDisplaysAPI = HolographicDisplaysAPI.get(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            // Remove normal drops and exp
            event.getDrops().clear();
            event.setDroppedExp(0);

            // Spawn the floating item with a label
            Hologram hologram = holographicDisplaysAPI.createHologram(event.getEntity().getLocation().add(0.0, 0.9, 0.0));
            hologram.getLines().appendText(ChatColor.AQUA + "" + ChatColor.BOLD + "Speed PowerUp", false);
            ItemHologramLine itemLine = hologram.getLines().appendItem(new ItemStack(Material.SUGAR));

            itemLine.setPickupListener((HologramLinePickupEvent pickupEvent) -> {
                // Play an effect
                pickupEvent.getPlayer().playEffect(hologram.getPosition().toLocation(), Effect.MOBSPAWNER_FLAMES, null);

                // 30 seconds of speed II
                pickupEvent.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 1), true);

                // Delete the hologram
                hologram.delete();
            });
        }
    }

}
