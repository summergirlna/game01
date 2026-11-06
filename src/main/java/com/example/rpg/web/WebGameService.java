package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;
import org.springframework.stereotype.Service;

/// Web版ゲーム操作サービス
@Service
public class WebGameService {

    private static final int INN_COST = 10;

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

    /// 宿屋に泊まる
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult stayInn(Player player) {
        if (player.getCurrentArea() != FieldArea.TOWN) {
            return new GameActionResult("ここには宿屋がない。");
        }

        if (!player.payGold(INN_COST)) {
            return new GameActionResult("ゴールドが足りません。必要なゴールド: " + INN_COST);
        }

        player.healFull();
        return new GameActionResult("宿屋に泊まった。HPが全回復した。");
    }

    public GameActionResult moveToNextArea(Player player) {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case TOWN -> FieldArea.GRASSLAND;
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE -> FieldArea.TOWN;
        };

        player.moveTo(nextArea);
        return new GameActionResult(nextArea.getDisplayName() + "へ移動した。");

    }
}
