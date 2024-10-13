package ru.emrass;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;
import ru.emrass.bossapi.BossApiCore;
import ru.emrass.bossapi.bosses.BossManagerAPI;
import ru.emrass.bossapi.bosses.scripts.BossScript;
import ru.emrass.bossapi.command.BaseCommand;
import ru.emrass.bossapi.database.DataBaseAPI;
import ru.emrass.commands.BossSpawn;
import ru.emrass.commands.ConfigReload;
import ru.emrass.scripts.SummonerBoss;
import ru.emrass.scripts.TearerBoss;

import java.util.Arrays;


public class BossPlugin extends JavaPlugin{
    @Getter
    public static BossPlugin instance;
    @Getter
    public BossApiCore bossApiCore;
    @Getter
    private DataBaseAPI dataBaseApi;

    @Override
    @SneakyThrows
    public void onEnable() {
        instance = this;
        bossApiCore = BossApiCore.getInstance();
        bossApiCore.getConfigManager().init(getDataFolder());
        dataBaseApi = DataBaseAPI.createDatabase(getDataFolder(),"bossesdata");
        BossManagerAPI.loadBosses(dataBaseApi.getConnection());
        registerCommand(
                BossSpawn.class,
                ConfigReload.class
        );
        BossScript.register("summoner",new SummonerBoss());
        BossScript.register("tearer",new TearerBoss());
        Bukkit.getWorld("world").setGameRule(GameRule.DO_MOB_LOOT,false);
        Bukkit.getWorld("world").setGameRule(GameRule.DO_MOB_SPAWNING,false);
    }
    void registerCommand(Class<?>... commands) {
        Arrays.asList(commands).forEach(BaseCommand::registerCommand);
    }

    @Override
    public void onDisable() {

    }
}
