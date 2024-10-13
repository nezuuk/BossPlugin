package ru.emrass.commands;

import org.bukkit.entity.Player;
import ru.emrass.bossapi.bosses.BossManagerAPI;
import ru.emrass.bossapi.bosses.CustomBoss;
import ru.emrass.bossapi.command.BaseCommand;
import ru.emrass.bossapi.command.context.ContextCommand;

import java.util.List;

public class BossSpawn extends BaseCommand {
    public BossSpawn() {
        super("test", "spawn boss", "/test boss_id saveDataBase?", List.of("t"));
    }

    @Override
    public void execute(ContextCommand ctx) {
        Player p = ctx.getPlayer();
        int bossid = Integer.parseInt(ctx.getArg(0).toString());
        boolean save =  Boolean.parseBoolean(ctx.getArg(1).toString());
        CustomBoss boss = BossManagerAPI.getBossFromID(bossid);
        if(boss == null) return;
        BossManagerAPI.spawn(boss,p.getLocation(),save);

    }
}
