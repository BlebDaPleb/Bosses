package com.blebdapleb.bosses.commands;

import com.blebdapleb.bosses.BossesMain;
import com.blebdapleb.bosses.events.CombatMode;
import com.blebdapleb.bosses.events.OnDrownedSummonEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class SummonDrownedBoss implements CommandExecutor {

    private OnDrownedSummonEvent onDrownedSummonEvent;
    public SummonDrownedBoss(OnDrownedSummonEvent onDrownedSummonEvent){

        this.onDrownedSummonEvent = onDrownedSummonEvent;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("summonboss.drowned")){

            onDrownedSummonEvent.summonDrown();
            OnDrownedSummonEvent.drownedBossAlive = true;
            OnDrownedSummonEvent.bossHasShield = false;
            CombatMode.bossCombat = false;

        } else {

            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

        }

        return true;
    }
}
