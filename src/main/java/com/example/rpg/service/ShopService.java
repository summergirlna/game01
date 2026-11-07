package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// 店の処理を担当するクラス
@RequiredArgsConstructor
@Service
public class ShopService {
    private static final int HERB_PRICE = 5;

    /// 薬草を購入する
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult buyHerb(Player player) {
        ItemType itemType = ItemType.HERB;

        if (player.getCurrentArea() != FieldArea.TOWN) {
            return GameActionResult.message("ここには道具屋がない。");
        }

        if (!player.payGold(HERB_PRICE)) {
            return GameActionResult.message("ゴールドが足りません。必要なゴールド: " + HERB_PRICE);
        }

        player.getInventory().add(itemType, 1);
        return GameActionResult.message(itemType.getDisplayName() + "を買った。" + HERB_PRICE + " ゴールドを支払った。");
    }
}
