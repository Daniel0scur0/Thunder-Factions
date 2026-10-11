package com.massivecraft.factions.compat.modern;

import com.massivecraft.factions.compat.CompatibilityContext;
import com.massivecraft.factions.compat.CompatibilityModule;
import com.massivecraft.factions.listeners.vspecific.ConsumableListener;
import com.massivecraft.factions.missions.impl.MissionHandlerModern;
import me.lucko.commodore.CommodoreProvider;

public final class ModernCompatibilityModule implements CompatibilityModule {

    @Override
    public String getName() {
        return "modern";
    }

    @Override
    public boolean supports(short minecraftVersion) {
        return minecraftVersion > 8;
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean supportsBrigadier() {
        return CommodoreProvider.isSupported();
    }

    @Override
    public void onEnable(CompatibilityContext context) {
        if (context.getPlugin().getConfig().getBoolean("disable-chorus-teleport-in-territory", true)) {
            context.registerListener(new ConsumableListener());
        }

        context.registerListener(new MissionHandlerModern());
    }
}
