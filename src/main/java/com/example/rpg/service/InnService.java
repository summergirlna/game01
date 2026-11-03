package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.ui.inn.InnMessage;
import lombok.RequiredArgsConstructor;

/// 宿屋の処理を担当するクラス
@RequiredArgsConstructor
public class InnService {
    private static final int COST = 10;

    private final InnMessage message;

    /// 宿屋に泊まる
    /// @param player プレイヤー
    public void stay(Player player) {
        if (!player.payGold(COST)) {
            message.showNotEnoughGold(COST);
            return;
        }

        // 全回復させる
        player.healFull();
        message.showStayInn(COST);
    }
}
