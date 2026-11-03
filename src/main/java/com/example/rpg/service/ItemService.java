package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.ItemMessage;
import lombok.RequiredArgsConstructor;

/// アイテムの使用処理を担当するクラス
@RequiredArgsConstructor
public class ItemService {

    private final ItemMessage message;

    /// 薬草を使う
    /// @param player プレイヤー
    public void useHerb(Player player) {
        ItemType itemType = ItemType.HERB;

        if (!player.getInventory().use(itemType)) {
            message.showNoItem(itemType);
            return;
        }

        player.heal(itemType.getHealAmount());
        message.showUseItem(itemType);
    }
}
