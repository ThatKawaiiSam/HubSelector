package io.github.thatkawaiisam.hubselector;

import io.github.thatkawaiisam.menus.Button;
import io.github.thatkawaiisam.menus.Menu;
import io.github.thatkawaiisam.menus.MenuManager;
import io.github.thatkawaiisam.redstone.bukkit.RedstoneBukkitAPI;
import io.github.thatkawaiisam.redstone.shared.RedstoneSharedAPI;
import io.github.thatkawaiisam.redstone.shared.server.RedstoneServer;
import io.github.thatkawaiisam.redstone.shared.server.ServerState;
import io.github.thatkawaiisam.utils.BungeeUtility;
import io.github.thatkawaiisam.utils.ItemBuilder;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HubSelectorMenu extends Menu {

    public HubSelectorMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public String getTitle(Player player) {
        return MessageUtility.formatMessage("&6Select a hub...");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int button = 0;
        for (RedstoneServer redstoneServer : RedstoneSharedAPI.getServersFromGroup("Lobby")) {
            buttons.put(button++, new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder()
                            .material(Material.BOOK)
                            .title("&6&l" + redstoneServer.getServerID().toUpperCase())
                            .lores(
                                    Arrays.asList(
                                            "&7&m----------------------",
                                            getStatus(redstoneServer) + " &7(" + redstoneServer.getData().getOnlinePlayers() + "/" + redstoneServer.getData().getMaxPlayers() + ")",
                                            "",
                                            getBottomText(redstoneServer),
                                            "&7&m----------------------"
                                    )
                            )
                            .build();
                }

                @Override
                public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                    player.closeInventory();
                    if (redstoneServer.getServerID().equals(RedstoneBukkitAPI.getCurrentServerName())) {
                        player.sendMessage(HubSelectorPlugin.getInstance().getLanguage().getValue("Already-Connected", true));
                    } else {
                        if (redstoneServer.getData().getState() == ServerState.OFFLINE) {
                            player.sendMessage(HubSelectorPlugin.getInstance().getLanguage().getValue("Hub-Offline", true));
                        } else {
                            BungeeUtility.sendPlayerToServer(HubSelectorPlugin.getInstance(), player, redstoneServer.getServerID());
                        }
                    }
                }
            });
        }

        return buttons;
    }

    private String getBottomText(RedstoneServer server) {
        if (server.getServerID().equals(RedstoneBukkitAPI.getCurrentServerName())) {
            return "&cYou are here!";
        } else {
            return "&eClick here to connect!";
        }
    }

    private String getStatus(RedstoneServer server) {
        ServerState serverState = server.getData().getState();
        if (serverState == ServerState.ONLINE) {
            return HubSelectorPlugin.getInstance().getLanguage().getValue("Status.Online", true);
        } else if (serverState == ServerState.WHITELISTED) {
            return HubSelectorPlugin.getInstance().getLanguage().getValue("Status.Whitelisted", true);
        } else {
            return HubSelectorPlugin.getInstance().getLanguage().getValue("Status.Offline", true);
        }
    }
}
