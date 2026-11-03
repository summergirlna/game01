package com.example.rpg.game;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import lombok.RequiredArgsConstructor;

import java.util.Random;

/// 探索処理を担当するクラス
public class ExploreService {
    private final Player player;

    private final MonsterFactory monsterFactory;

    private final Random random;

    public ExploreService(Player player, MonsterFactory monsterFactory) {
        this.player = player;
        this.monsterFactory = monsterFactory;
        this.random = new Random();
    }

    /// 探索する
    /// @return 探索中に発生した戦闘結果。戦闘が発生しなかった場合はnull
    public BattleResult explore() {
        System.out.println("周囲を探索した。");

        if (!encounterMonster()) {
            System.out.println("特に何も起きなかった。");
            return null;
        }

        Monster monster = monsterFactory.createRandomMonster();

        Battle battle = new Battle(player, monster);
        BattleResult result = battle.start();

        System.out.println("戦闘結果: " + result);

        return result;
    }

    /// モンスターに遭遇するかどうかを判定する
    /// @return モンスターと遭遇した場合はtrue、そうでない場合はfalse
    private boolean encounterMonster() {
        int encounterRate = 60;
        return random.nextInt(100) < encounterRate;
    }
}
