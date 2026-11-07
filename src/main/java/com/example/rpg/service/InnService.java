package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// 宿屋の処理を担当するクラス
@RequiredArgsConstructor
@Service
public class InnService {
    private static final int COST = 10;

    /// 宿屋に泊まる
    /// @param player プレイヤー
    public GameActionResult stay(Player player) {
        if (player.getCurrentArea() != FieldArea.TOWN) {
            return GameActionResult.message("ここには宿屋がない。");
        }

        if (!player.payGold(COST)) {
            return GameActionResult.message("ゴールドが足りません。必要なゴールド: " + COST);
        }

        player.healFull();
        return GameActionResult.message("宿屋に泊まった。HPが全回復した。");
    }
}
