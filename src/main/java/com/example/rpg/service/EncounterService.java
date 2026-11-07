package com.example.rpg.service;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import lombok.RequiredArgsConstructor;

import java.util.Random;

/// エンカウント処理を担当するクラス
@RequiredArgsConstructor
public class EncounterService {
    private static final int ENCOUNTER_RATE = 30;

    private final Player player;

    private final MonsterFactory monsterFactory;

    private final Random random = new Random();

    /// モンスターと遭遇した場合、戦闘を開始する
    ///
    /// @return 戦闘が発生した場合は戦闘結果、発生しなかった場合はnull
    public BattleResult encounter() {
        if (!encounterMonster()) {
            return null;
        }

        Monster monster = monsterFactory.createRandomMonster(player.getCurrentArea());
        Battle battle = new Battle(player, monster);
        return battle.start();
    }

    /// モンスターに遭遇するかどうかを判定する
    ///
    /// @return モンスターと遭遇した場合はtrue、そうでない場合はfalse
    private boolean encounterMonster() {
        return random.nextInt(100) < ENCOUNTER_RATE;
    }
}
