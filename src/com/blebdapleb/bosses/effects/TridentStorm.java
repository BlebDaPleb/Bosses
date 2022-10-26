package com.blebdapleb.bosses.effects;

import com.blebdapleb.bosses.BossesMain;
import com.blebdapleb.bosses.events.OnDrownedSummonEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TridentStorm {

    private BossesMain bossesMain;
    public TridentStorm(){

        this.bossesMain = BossesMain.getInstance();

    }

    public static int tridentID;

    public void tridentStorm(){

        Skeleton boss = OnDrownedSummonEvent.boss;

        new BukkitRunnable(){

            int countDownSecs = 10;

            @Override
            public void run() {

                if (countDownSecs != 0){

                    tridentID = Bukkit.getScheduler().scheduleSyncRepeatingTask(bossesMain, new Runnable() {
                        @Override
                        public void run() {

                            int randomAmount = ThreadLocalRandom.current().nextInt(10, 15);

                            for (int i = 0; i < randomAmount; i++){

                                Location bossLoc = boss.getLocation();
                                Location aboveBossLoc = bossLoc.clone().add(0, 10, 0);
                                int randomX = ThreadLocalRandom.current().nextInt(-50, 50);
                                int randomZ = ThreadLocalRandom.current().nextInt(-50, 50);
                                Location resultLoc = aboveBossLoc.clone().add(randomX, 0, randomZ);
                                boss.getWorld().spawnEntity(resultLoc, EntityType.TRIDENT);

                            }

                        }
                    }, 0, 15);

                    countDownSecs--;

                } else {

                    Bukkit.getScheduler().cancelTask(tridentID);
                    cancel();
                    OnDrownedSummonEvent.bossHasShield = false;
                }

            }
        }.runTaskTimer(bossesMain, 0, 20);

    }

}
