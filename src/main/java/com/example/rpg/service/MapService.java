package com.example.rpg.service;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.field.MapResult;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.ui.field.MapMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/// マップ移動処理を担当するクラス
@RequiredArgsConstructor
@Service
public class MapService {

    /// 移動する
    /// @param player プレイヤー
    /// @param gameMap マップ
    /// @param command マップ操作コマンド
    /// @return 操作結果
    public GameActionResult move(Player player, GameMap gameMap, MapCommand command) {
        if (!gameMap.move(command)) {
            return new GameActionResult("これ以上そちらには進めません。");
        }

        return new GameActionResult(player.getCurrentArea().getDisplayName() + "を探索している。");
    }
}
