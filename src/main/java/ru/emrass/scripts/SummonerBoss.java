package ru.emrass.scripts;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import ru.emrass.BossPlugin;
import ru.emrass.bossapi.bosses.BossProtectionType;
import ru.emrass.bossapi.bosses.CustomBoss;
import ru.emrass.bossapi.bosses.scripts.BossScript;

import java.util.Random;

public class SummonerBoss extends BossScript {

    private int phase = 0;
    private BukkitTask task;
    private BukkitTask task2;
    public SummonerBoss() {
        super("summoner");
    }

    @Override
    public void onSpawn(CustomBoss customBoss) {
        customBoss.getProtectionTypeList().add(BossProtectionType.POISON);
        customBoss.getProtectionTypeList().add(BossProtectionType.PROJECTILE);
    }

    @Override
    public void onDeath(CustomBoss customBoss) {
        phase = 0;
        task.cancel();
        task2.cancel();
    }

    @Override
    public void onDamageBoss(CustomBoss customBoss, Player player, double v) {
        if(phase == 0 ){
           task = Bukkit.getScheduler().runTaskTimer(BossPlugin.getInstance(),() -> {
                if(customBoss == null) return;
                int count = r.nextInt(1,3);
                LivingEntity le = customBoss.getLe();
                Location loc = le.getLocation();
                World world = loc.getWorld();
                for(int i = 1; i <= count; i++){
                    Zombie zombie = world.spawn(loc, Zombie.class);
                    zombie.setBaby();
                    zombie.setTarget(player);
                }
            },1200,1200);
           task2 = Bukkit.getScheduler().runTaskTimer(BossPlugin.getInstance(),() -> {
               if(customBoss == null) return;
               LivingEntity le = customBoss.getLe();
               ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET);
               ItemStack leatherchestt = new ItemStack(Material.LEATHER_CHESTPLATE);
               ItemStack leatherlegg = new ItemStack(Material.LEATHER_LEGGINGS);
               ItemStack leatherboots = new ItemStack(Material.LEATHER_BOOTS);
               ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
               leatherHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
               leatherchestt.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
               leatherlegg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
               leatherboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
               stoneSword.addEnchantment(Enchantment.DAMAGE_ALL,4);
               le.getEquipment().setHelmet(leatherHelmet);
               le.getEquipment().setChestplate(leatherchestt);
               le.getEquipment().setLeggings(leatherlegg);
               le.getEquipment().setBoots(leatherboots);
               le.getEquipment().setItemInMainHand(stoneSword);
               customBoss.getLe().getLocation().getNearbyEntitiesByType(Player.class,6).forEach(player1 -> {
                   player1.setVelocity(player1.getLocation().toVector().subtract(customBoss.getLe().getLocation().toVector()).normalize().multiply(1.7));
               });
               Bukkit.getScheduler().runTaskLater(BossPlugin.getInstance(),() -> {
                    le.getEquipment().clear();
                    le.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
               },300);
           },600,600);
            phase++;
        }
    }


}
