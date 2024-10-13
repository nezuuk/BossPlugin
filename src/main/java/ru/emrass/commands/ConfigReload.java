package ru.emrass.commands;

import net.kyori.adventure.text.Component;
import ru.emrass.BossPlugin;
import ru.emrass.bossapi.BossApiCore;
import ru.emrass.bossapi.command.BaseCommand;
import ru.emrass.bossapi.command.context.ContextCommand;

import java.io.File;
import java.util.List;

public class ConfigReload extends BaseCommand {
    public ConfigReload() {
        super("cfgreload", "config reload", "/cfgreload", List.of());
    }

    @Override
    public void execute(ContextCommand ctx) {
        BossApiCore.getInstance().getConfigManager().reloadConfig(new File(BossPlugin.getInstance().getDataFolder() + File.separator + "localization.yml") ,BossApiCore.getInstance().getConfigManager().getLocalizationConfig());
        ctx.getPlayer().sendMessage(Component.text("Конфиг перезагружен!"));
    }
}
