package com.blebdapleb.bosses.events;

import com.blebdapleb.bosses.BossesMain;
import com.blebdapleb.bosses.effects.BossBar;
import com.blebdapleb.bosses.effects.BossShieldParticles;
import com.blebdapleb.bosses.effects.TridentStorm;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.concurrent.ThreadLocalRandom;

public class OnDrownedSummonEvent {

    private BossesMain plugin;
    public OnDrownedSummonEvent(BossesMain plugin) {

        this.plugin = plugin;

    }

    public static Skeleton boss;

    public static boolean drownedBossAlive;
    public static boolean bossHasShield;

    public void summonDrown() {

        Bukkit.getServer().broadcastMessage(BossesMain.getInstance().getConfig().getString("BossSummonedMsg"));

        double xCoordPath = BossesMain.getInstance().getConfig().getDouble("XCoord");
        double yCoordPath = BossesMain.getInstance().getConfig().getDouble("YCoord");
        double zCoordPath = BossesMain.getInstance().getConfig().getDouble("ZCoord");
        // Drowned boss
        double drownedSpeed = BossesMain.getInstance().getConfig().getDouble("DrownedSpeed");
        double drownedHealth = BossesMain.getInstance().getConfig().getDouble("DrownedHealth");
        double henchmenHealth = BossesMain.getInstance().getConfig().getDouble("HenchmenHealth");
        double henchmenSpeed = BossesMain.getInstance().getConfig().getDouble("HenchmenSpeed");

        Location summonLoc = new Location(Bukkit.getWorld(BossesMain.getInstance().getConfig().getString("World")), xCoordPath, yCoordPath, zCoordPath).add(0.5, 0.5, 0.5);

        ItemStack trident = new ItemStack(Material.TRIDENT, 1);
        ItemMeta tridentMeta = trident.getItemMeta();
        tridentMeta.setDisplayName(ChatColor.BLUE + "The Trident of Poseidon");
        tridentMeta.addEnchant(Enchantment.LOYALTY, 255, true);
        tridentMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        trident.setItemMeta(tridentMeta);

        ItemStack drownedChestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta drownedChestPlateMeta = drownedChestPlate.getItemMeta();
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) drownedChestPlateMeta;
        leatherArmorMeta.setColor(Color.BLUE);
        leatherArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, BossesMain.getInstance().getConfig().getInt("Protection"), true);
        leatherArmorMeta.setUnbreakable(true);
        drownedChestPlate.setItemMeta(leatherArmorMeta);

        ItemStack drownedLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta drownedLeggingsMeta = drownedLeggings.getItemMeta();
        LeatherArmorMeta leatherArmorMeta2 = (LeatherArmorMeta) drownedLeggingsMeta;
        leatherArmorMeta2.setColor(Color.BLUE);
        leatherArmorMeta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, BossesMain.getInstance().getConfig().getInt("Protection"), true);
        leatherArmorMeta2.setUnbreakable(true);
        drownedLeggings.setItemMeta(leatherArmorMeta2);

        ItemStack drownedBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta drownedBootsMeta = drownedBoots.getItemMeta();
        LeatherArmorMeta leatherArmorMeta3 = (LeatherArmorMeta) drownedBootsMeta;
        leatherArmorMeta3.setColor(Color.BLUE);
        leatherArmorMeta3.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, BossesMain.getInstance().getConfig().getInt("Protection"), true);
        leatherArmorMeta3.setUnbreakable(true);
        drownedBoots.setItemMeta(leatherArmorMeta3);

        Skeleton drowned = Bukkit.getWorld(BossesMain.getInstance().getConfig().getString("World")).spawn(summonLoc, Skeleton.class);
        drowned.setCustomName(BossesMain.getInstance().getConfig().getString("DrownedBossName"));
        drowned.setCustomNameVisible(true);
        drowned.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(drownedSpeed);
        drowned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(drownedHealth);
        drowned.setHealth(drownedHealth);drowned.getEquipment().setItemInMainHand(trident);
        drowned.getEquipment().setChestplate(drownedChestPlate);
        drowned.getEquipment().setLeggings(drownedLeggings);drowned.getEquipment().setBoots(drownedBoots);
        drowned.setMetadata("DrownedBoss", new FixedMetadataValue(plugin, "drownedboss"));

        boss = drowned;

        // Scoreboard
        Scoreboard health = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective healthObj = health.registerNewObjective("health", "dummy", "Health");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);

        // Instances
        BossShieldParticles bossShieldParticles = new BossShieldParticles(boss);
        TridentStorm tridentStorm = new TridentStorm();
        BossBar bossBar = new BossBar(boss);
        bossBar.showBossBar();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(BossesMain.getInstance(), new Runnable() {
            @Override
            public void run() {

                if (drownedBossAlive) {

                    int random = ThreadLocalRandom.current().nextInt(10);

                        int randomInt = ThreadLocalRandom.current().nextInt(5, 10);

                        if (boss.getNearbyEntities(30, 0, 30) instanceof Player){

                            for (int i = 0; i < randomInt; i++) {

                                Location drownedLoc = boss.getLocation();
                                Drowned henchmen = boss.getWorld().spawn(drownedLoc, Drowned.class);
                                henchmen.setCustomName(BossesMain.getInstance().getConfig().getString("HenchmenName"));
                                henchmen.setAdult();
                                henchmen.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(henchmenHealth);
                                henchmen.setHealth(henchmenHealth);
                                henchmen.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(henchmenSpeed);
                                henchmen.getEquipment().setBoots(drownedBoots);
                                henchmen.getEquipment().setLeggings(drownedLeggings);
                                henchmen.getEquipment().setChestplate(drownedChestPlate);
                                henchmen.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                                henchmen.setMetadata("DrownedHenchmen", new FixedMetadataValue(plugin, "drownedhenchmen"));

                            }

                        }

                }
            }
        }, 0, 30 * 20);

        if (CombatMode.bossCombat){

            Bukkit.getScheduler().scheduleSyncRepeatingTask(BossesMain.getInstance(), new Runnable() {
                @Override
                public void run() {

                    if (drownedBossAlive) {

                        int random = ThreadLocalRandom.current().nextInt(10);
                        if (random > 3) {

                            bossHasShield = true;
                            bossShieldParticles.startParticles();
                            tridentStorm.tridentStorm();

                        }

                    }
                }
            }, 0, 30 * 20);

        }

    }

}
