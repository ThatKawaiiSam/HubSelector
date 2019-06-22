package io.github.thatkawaiisam.hubselector.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import io.github.thatkawaiisam.hubselector.HubSelectorPlugin;
import org.bukkit.entity.Player;

@CommandAlias("hubselector|lobbyselector")
public class HubSelectorCommand extends BaseCommand {

    @Default
    public void selectServer(Player player) {
        player.sendMessage(HubSelectorPlugin.getInstance().getLanguage().getValue("Opening-Selector", true));
        HubSelectorPlugin.getInstance().getMenu().openMenu(player);
    }

}
