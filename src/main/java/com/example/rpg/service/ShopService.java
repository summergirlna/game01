package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.ShopMessage;
import lombok.RequiredArgsConstructor;

/// 店の処理を担当するクラス
@RequiredArgsConstructor
public class ShopService {
    private static final int HERB_PRICE = 5;

    private final ShopMessage message;

    /// 薬草を購入する
    /// @param player プレイヤー
    public void buyHerb(Player player) {
        if (player.getCurrentArea() != FieldArea.TOWN) {
            message.showNoShop();
            return;
        }

        if (!player.payGold(HERB_PRICE)) {
            message.showNotEnoughGold(HERB_PRICE);
            return;
        }

        player.getInventory().add(ItemType.HERB, 1);
        message.showBuyItem(ItemType.HERB, HERB_PRICE);
    }
}
