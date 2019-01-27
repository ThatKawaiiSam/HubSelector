package io.github.thatkawaiisam.hubselector.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import io.github.thatkawaiisam.hubselector.HubSelectorPlugin;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.entity.Player;

@CommandAlias("hubselector")
public class HubSelectorCommand extends BaseCommand {

    @Default
    public void selectServer(Player player) {
        player.sendMessage(MessageUtility.formatMessage("&aOpening hub selector menu..."));
        HubSelectorPlugin.getInstance().getMenu().openMenu(player);
    }

}
