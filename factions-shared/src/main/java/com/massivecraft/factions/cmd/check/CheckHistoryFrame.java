package com.massivecraft.factions.cmd.check;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.zcore.frame.FactionGUI;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.*;

public class CheckHistoryFrame implements FactionGUI {

    /**
     * @author Driftay
     */

    private FactionsPlugin plugin;
    private Faction faction;
    private Inventory inventory;
    private SimpleDateFormat simpleDateFormat;

    public CheckHistoryFrame(FactionsPlugin plugin, Faction faction) {
        this.simpleDateFormat = new SimpleDateFormat(Conf.dateFormat);
        this.plugin = plugin;
        this.faction = faction;
        this.inventory = plugin.getServer().createInventory(this, 54, TL.CHECK_HISTORY_GUI_TITLE.toString());
    }

    public void onClick(int slot, ClickType action) {
    }

    @Override
    public void onClose(HumanEntity player) {
    }

    @Override
    public void build(boolean initialOpen) {
        int currentSlot = 0;
        for (Map.Entry<Long, String> entry : Lists.reverse(new ArrayList<>(faction.getChecks().entrySet()))) {
            if (currentSlot >= 54) {
                continue;
            }

            ItemStack itemStack;
            if (entry.getValue().startsWith("U")) {
                itemStack = XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(TL.CHECK_WALLS_CHECKED_GUI_ICON.toString());
                itemMeta.setLore(Arrays.asList(TL.CHECK_TIME_LORE_LINE.format(simpleDateFormat.format(new Date(entry.getKey()))), TL.CHECK_PLAYER_LORE_LINE.format(entry.getValue().substring(1))));
                itemStack.setItemMeta(itemMeta);
            } else if (entry.getValue().startsWith("Y")) {
                itemStack = XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(TL.CHECK_BUFFERS_CHECKED_GUI_ICON.toString());
                itemMeta.setLore(Arrays.asList(TL.CHECK_TIME_LORE_LINE.format(simpleDateFormat.format(new Date(entry.getKey()))), TL.CHECK_PLAYER_LORE_LINE.format(entry.getValue().substring(1))));
                itemStack.setItemMeta(itemMeta);
            } else if (entry.getValue().startsWith("J")) {
                itemStack = XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(TL.CHECK_WALLS_UNCHECKED_GUI_ICON.toString());
                itemMeta.setLore(Collections.singletonList(TL.CHECK_TIME_LORE_LINE.format(simpleDateFormat.format(new Date(entry.getKey())))));
                itemStack.setItemMeta(itemMeta);
            } else if (entry.getValue().startsWith("H")) {
                itemStack = XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(TL.CHECK_BUFFERS_UNCHECKED_GUI_ICON.toString());
                itemMeta.setLore(Collections.singletonList(TL.CHECK_TIME_LORE_LINE.format(simpleDateFormat.format(new Date(entry.getKey())))));
                itemStack.setItemMeta(itemMeta);
            } else {
                continue;
            }

            inventory.setItem(currentSlot, itemStack);
            ++currentSlot;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
