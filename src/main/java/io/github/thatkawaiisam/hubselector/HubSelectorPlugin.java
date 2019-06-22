package io.github.thatkawaiisam.hubselector;

import io.github.plugintemplate.PluginTemplate;
import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.handler.impl.CommandHandler;
import io.github.plugintemplate.handler.impl.ConfigHandler;
import io.github.plugintemplate.handler.impl.LanguageHandler;
import io.github.thatkawaiisam.menus.MenuManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.Messenger;

import java.util.ArrayList;

@Getter
public class HubSelectorPlugin extends PluginTemplate {

    //Instance
    @Getter private static HubSelectorPlugin instance;

    private MenuManager menuManager;
    private HubSelectorMenu menu;

    @Override
    public void onEnable() {
        instance = this;

        menuManager = new MenuManager(this);
        menu = new HubSelectorMenu(menuManager);

        if (getHandlers() == null) {
            setHandlers(new ArrayList<>());
            getHandlers().add(new ConfigHandler(HubSelectorConstants.CONFIG_VERSION, this));
            getHandlers().add(new LanguageHandler(this));
            getHandlers().add(new CommandHandler(HubSelectorConstants.PACKAGE_NAME + ".commands", this));
            getHandlers().add(new Handler("channel", false, this) {
                Messenger messenger = Bukkit.getServer().getMessenger();

                @Override
                public void onEnable() {
                    messenger.registerOutgoingPluginChannel(instance, "BungeeCord");
                }

                @Override
                public void onDisable() {
                    messenger.unregisterOutgoingPluginChannel(instance, "BungeeCord");
                }
            });
        }

        getHandlers().forEach(Handler::enable);
    }

    @Override
    public void onDisable() {
        menuManager.cleanup();
        getHandlers().forEach(Handler::disable);
    }
}
