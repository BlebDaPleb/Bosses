package com.blebdapleb.bosses.effects;

import com.blebdapleb.bosses.BossesMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBar {

    private static int bossBarID;

    public static org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Poseidon", BarColor.BLUE, BarStyle.SOLID);

    private Skeleton boss;
    public BossBar(Skeleton boss){

        this.boss = boss;

    }

    public void showBossBar(){

        double maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        bossBarID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BossesMain.getInstance(), new Runnable() {

            @Override
            public void run() {

                double currentHealth = boss.getHealth();
                bossBar.setProgress(currentHealth / maxHealth);

            }


        }, 0, 1);

        for (Entity e : boss.getNearbyEntities(50, 0, 50)){

            if (e instanceof Player player){

                bossBar.addPlayer(player);

            }

        }

    }

}
