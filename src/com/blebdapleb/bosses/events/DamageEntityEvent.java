package com.blebdapleb.bosses.events;

import com.blebdapleb.bosses.BossesMain;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class DamageEntityEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Skeleton || e.getDamager() instanceof Skeleton){

            if (e.getEntity().hasMetadata("DrownedBoss") || e.getDamager().hasMetadata("DrownedBoss")){

                CombatMode combatMode = new CombatMode();
                CombatMode.bossCombat = true;
                combatMode.setCombatSecs(15);
                int[] combatSecs = {combatMode.getCombatSecs()};

                new BukkitRunnable(){

                    @Override
                    public void run() {

                        if (combatSecs[0] != 0){

                            combatSecs[0]--;

                        } else {

                            CombatMode.bossCombat = false;

                        }

                    }

                }.runTaskTimer(BossesMain.getInstance(), 0, 20);

            }

        }

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Skeleton) {

            if (e.getDamager().hasMetadata("DrownedBoss")) {

                int random = ThreadLocalRandom.current().nextInt(10);
                if (random < 2){

                    e.setCancelled(true);
                    Player player = (Player) e.getEntity();
                    player.setVelocity(new Vector(0, 2, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, false, false));

                }

            }

        }
        if (e.getEntity() instanceof Skeleton && e.getDamager() instanceof Player){

            if (e.getEntity().hasMetadata("DrownedBoss")){

                if (OnDrownedSummonEvent.bossHasShield){

                    e.setCancelled(true);
                    Player player = (Player) e.getDamager();
                    player.sendMessage(BossesMain.getInstance().getConfig().getString("BossHasShieldMsg"));

                }

                int random = ThreadLocalRandom.current().nextInt(10);
                if (random < 2){


                    e.setCancelled(true);
                    Player player = (Player) e.getDamager();
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Your attack was blocked!");
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 3, 1);

                }

            }

        }

    }

}
