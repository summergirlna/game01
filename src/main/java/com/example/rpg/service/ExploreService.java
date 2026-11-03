package com.example.rpg.service;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.ui.ExploreMessage;
import lombok.RequiredArgsConstructor;

import java.util.Random;

/// 探索処理を担当するクラス
@RequiredArgsConstructor
public class ExploreService {
    private final Player player;

    private final MonsterFactory monsterFactory;

    private final ExploreMessage message;

    private final Random random = new Random();

    /// 探索する
    /// @return 探索中に発生した戦闘結果。戦闘が発生しなかった場合はnull
    public BattleResult explore() {
        message.showExploreArea(player.getCurrentArea());

        if (player.getCurrentArea() == FieldArea.TOWN) {
            message.showSafeTown();
            return null;
        }

        if (!encounterMonster()) {
            message.showNoEvent();
            return null;
        }

        Monster monster = monsterFactory.createRandomMonster(player.getCurrentArea());

        Battle battle = new Battle(player, monster);
        BattleResult result = battle.start();

        message.showBattleResult(result);

        return result;
    }

    /// モンスターに遭遇するかどうかを判定する
    /// @return モンスターと遭遇した場合はtrue、そうでない場合はfalse
    private boolean encounterMonster() {
        int encounterRate = 60;
        return random.nextInt(100) < encounterRate;
    }
}
