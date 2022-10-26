package com.blebdapleb.bosses.effects;

import com.blebdapleb.bosses.BossesMain;
import com.blebdapleb.bosses.events.OnDrownedSummonEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class BossShieldParticles {

    public static int taskID;

    private Skeleton boss;
    public BossShieldParticles(Skeleton boss){

        this.boss = boss;

    }

    public void startParticles(){

        new BukkitRunnable(){

            int countDownSecs = 10;

            @Override
            public void run() {

                if (countDownSecs != 0){

                    taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BossesMain.getInstance(), new Runnable() {

                        double var = 0;
                        Location loc, first, second;

                        @Override
                        public void run() {

                            var += Math.PI / 16;

                            loc = boss.getLocation();
                            first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
                            second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));

                            boss.getWorld().spawnParticle(Particle.WATER_WAKE, first, 0);
                            boss.getWorld().spawnParticle(Particle.WATER_WAKE, second, 0);

                        }

                    }, 0, 1);

                    countDownSecs--;

                } else {

                    Bukkit.getScheduler().cancelTask(taskID);
                    cancel();
                    OnDrownedSummonEvent.bossHasShield = false;

                }

            }

        }.runTaskTimer(BossesMain.getInstance(), 0, 20);

    }

}
