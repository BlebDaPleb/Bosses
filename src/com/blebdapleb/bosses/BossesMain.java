package com.blebdapleb.bosses;

import com.blebdapleb.bosses.commands.SummonDrownedBoss;
import com.blebdapleb.bosses.effects.BossShieldParticles;
import com.blebdapleb.bosses.events.DamageEntityEvent;
import com.blebdapleb.bosses.events.OnDrownedSummonEvent;
import com.blebdapleb.bosses.events.OnEntityDeath;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BossesMain extends JavaPlugin {

    private static BossesMain instance;
    FileConfiguration config = getConfig();

    public static BossesMain getInstance(){

        return instance;

    }

    @Override
    public void onEnable() {

        // Config
        instance = this;
        config.options().copyDefaults(true);
        saveConfig();

        // Commands
        OnDrownedSummonEvent onDrownedSummonEvent = new OnDrownedSummonEvent(this);
        getCommand("summondrownedboss").setExecutor(new SummonDrownedBoss(onDrownedSummonEvent));

        // Events
        Bukkit.getPluginManager().registerEvents(new DamageEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityDeath(), this);

        System.out.println("Bosses plugin has been enabled");

    }

    @Override
    public void onDisable() {

        System.out.println("Bosses plugin has been disabled");

    }
}
