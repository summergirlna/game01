package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.item.ItemMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// アイテムの使用処理を担当するクラス
@RequiredArgsConstructor
@Service
public class ItemService {

    /// 薬草を使う
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult useHerb(Player player) {
        ItemType itemType = ItemType.HERB;

        if (!player.getInventory().use(itemType)) {
            return new GameActionResult(itemType.getDisplayName() + "を持っていません。");
        }

        player.heal(itemType.getHealAmount());
        return new GameActionResult(itemType.getDisplayName() + "を使った。HPが " + itemType.getHealAmount() + " 回復した。");
    }
}
