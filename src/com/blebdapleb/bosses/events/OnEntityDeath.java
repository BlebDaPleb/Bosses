package com.blebdapleb.bosses.events;

import com.blebdapleb.bosses.BossesMain;
import com.blebdapleb.bosses.effects.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){

        if (e.getEntity() instanceof Skeleton){

            if (e.getEntity().hasMetadata("DrownedBoss")){

                OnDrownedSummonEvent.drownedBossAlive = false;
                OnDrownedSummonEvent.bossHasShield = false;
                Bukkit.broadcastMessage(BossesMain.getInstance().getConfig().getString("BossDiedMsg"));
                BossBar.bossBar.removeAll();

            }

        }

    }

}
