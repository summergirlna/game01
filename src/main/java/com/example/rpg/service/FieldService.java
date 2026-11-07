package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// フィールド移動処理を担当するクラス
@RequiredArgsConstructor
@Service
public class FieldService {

    /// 次のエリアへ移動する
    ///
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult moveToNextArea(Player player) {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case TOWN -> FieldArea.GRASSLAND;
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE -> FieldArea.TOWN;
        };

        player.moveTo(nextArea);
        return GameActionResult.message(nextArea.getDisplayName() + "へ移動した。");
    }
}
