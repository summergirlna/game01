package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.ui.FieldMessage;
import lombok.RequiredArgsConstructor;

/// フィールド移動処理を担当するクラス
@RequiredArgsConstructor
public class FieldService {
    private final FieldMessage message;

    /// 次のエリアへ移動する
    /// @param player プレイヤー
    public void moveToNextArea(Player player) {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case TOWN -> FieldArea.GRASSLAND;
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE -> FieldArea.TOWN;
        };

        player.moveTo(nextArea);
        message.showMoveArea(nextArea);
    }
}
