package ru.emrass.scripts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import ru.emrass.BossPlugin;
import ru.emrass.bossapi.bosses.BossProtectionType;
import ru.emrass.bossapi.bosses.CustomBoss;
import ru.emrass.bossapi.bosses.scripts.BossScript;

public class TearerBoss extends BossScript {

    private int phase = 0;
    private BukkitTask task;
    private boolean rage;
    public TearerBoss() {
        super("tearer");
    }

    @Override
    public void onSpawn(CustomBoss customBoss) {
        customBoss.getProtectionTypeList().add(BossProtectionType.ANTIKNOCKBACK);
        ItemStack item = customBoss.getLe().getEquipment().getItemInMainHand();
        item.addEnchantment(Enchantment.MULTISHOT,1);
        item.addEnchantment(Enchantment.PIERCING,4);
        customBoss.getLe().getEquipment().setItemInMainHand(item);
    }

    @Override
    public void onDeath(CustomBoss customBoss) {
        task.cancel();
        phase = 0;
    }

    @Override
    public void onDamageBoss(CustomBoss customBoss, Player player, double v) {
        if(phase == 0){
            task = Bukkit.getScheduler().runTaskTimer(BossPlugin.getInstance(),() -> {
                if(customBoss == null) return;
                customBoss.getLe().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,25,2));
                if(rage){
                    customBoss.getLe().setVelocity(customBoss.getLe().getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1.7));
                }

            },1200,1200);
            phase++;
        }
        if(customBoss.getHealth() <= customBoss.getMaxHealth() / 2){
            rage = true;
            customBoss.getLe().getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
        }
    }
}
