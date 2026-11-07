package com.example.rpg.character;

import com.example.rpg.field.FieldArea;
import org.springframework.stereotype.Component;

import java.util.Random;

/// モンスターを生成するファクトリークラス
@Component
public class MonsterFactory {
    private final Random random;

    public MonsterFactory() {
        this.random = new Random();
    }

    /// エリアに応じたランダムなモンスターを生成する
    ///
    /// @param area エリア
    /// @return モンスター
    public Monster createRandomMonster(FieldArea area) {
        return switch (area) {
            case GRASSLAND -> createGrasslandMonster();
            case FOREST -> createForestMonster();
            case CAVE -> createCaveMonster();
            case TOWN -> throw new IllegalArgumentException("町ではモンスターを生成できません。");
        };
    }

    /// 草原に出現するモンスターを生成する
    ///
    /// @return モンスター
    private Monster createGrasslandMonster() {
        int monsterType = random.nextInt(2);

        return switch (monsterType) {
            case 0 -> new Monster("スライム", 20, 5, 10, 5);
            case 1 -> new Monster("こうもり", 18, 7, 12, 8);
            default -> throw new IllegalStateException("想定外のモンスター種別です。");
        };
    }

    /// 森に出現するモンスターを生成する
    ///
    /// @return モンスター
    private Monster createForestMonster() {
        int monsterType = random.nextInt(2);

        return switch (monsterType) {
            case 0 -> new Monster("こうもり", 18, 7, 12, 8);
            case 1 -> new Monster("ゴブリン", 28, 9, 20, 12);
            default -> throw new IllegalStateException("想定外のモンスター種別です。");
        };
    }

    /// 洞窟に出現するモンスターを生成する
    ///
    /// @return モンスター
    private Monster createCaveMonster() {
        int monsterType = random.nextInt(2);

        return switch (monsterType) {
            case 0 -> new Monster("ゴブリン", 28, 9, 20, 12);
            case 1 -> new Monster("オーク", 40, 12, 35, 20);
            default -> throw new IllegalStateException("想定外のモンスター種別です。");
        };
    }

    /// ボスモンスターを生成する
    ///
    /// @return ボスモンスター
    public Monster createBoss() {
        return new Monster("魔王", 80, 16, 100, 100);
    }
}
