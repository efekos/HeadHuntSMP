package me.efekos.headhuntsmp.menu.items;

import me.efekos.headhuntsmp.HeadHuntSMP;
import me.efekos.headhuntsmp.classes.PlayerData;
 
import me.efekos.headhuntsmp.files.PlayerDataManager;
import me.efekos.simpler.annotations.RightClick;
import me.efekos.simpler.translation.TranslateManager;
import me.efekos.simpler.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TransferQueue;

public class ExtraHead extends CustomItem {


    @RightClick
    public void onRightClick(CustomItem item,PlayerInteractEvent event) {
        ItemStack stack = event.getItem();
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.fetch(player);
        if(player.isSneaking()){
            data.setRemainingHeads(data.getRemainingHeads()+stack.getAmount());

            player.sendMessage(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("messages.new-heads","&aAdded &2%added% &anew heads! You have &2%new% &aheads now.").replace("%added%",stack.getAmount()+"").replace("%new%",data.getRemainingHeads()+"")));

            stack.setType(Material.AIR);
        } else {
            data.setRemainingHeads(data.getRemainingHeads()+1);

            player.sendMessage(TranslateManager.translateColors(HeadHuntSMP.gameConfig.getString("messages.new-heads","&aAdded &2%added% &anew heads! You have &2%new% &aheads now.").replace("%added%","1").replace("%new%",data.getRemainingHeads()+"")));

            stack.setAmount(stack.getAmount()-1);
        }
        PlayerDataManager.update(data.getUuid(),data);
    }

    @Override
    public @NotNull String getId() {
        return "extrahead";
    }

    @Override
    public @NotNull ItemMeta getDefaultMeta() {
        ItemMeta meta = new ItemStack(getMaterial()).getItemMeta();

        meta.setDisplayName(TranslateManager.translateColors(Objects.requireNonNull(GameConfig.get().getString("extra-head.name"))));
        meta.setLore(Arrays.asList(TranslateManager.translateColors(Objects.requireNonNull(GameConfig.get().getString("extra-head.description")))));

        return meta;
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.PLAYER_HEAD;
    }


}
