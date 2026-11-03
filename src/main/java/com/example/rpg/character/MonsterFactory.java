package com.example.rpg.character;

import java.util.Random;

/// モンスターを生成するファクトリークラス
public class MonsterFactory {
    private final Random random;

    public MonsterFactory() {
        this.random = new Random();
    }

    public Monster createRandomMonster() {
        int monsterType = random.nextInt(3);

        return switch (monsterType) {
            case 0 -> new Monster("スライム", 20, 5, 10, 5);
            case 1 -> new Monster("こうもり", 18, 7, 12, 8);
            case 2 -> new Monster("ゴブリン", 28, 9, 20, 12);
            default -> throw new IllegalStateException("想定外のモンスター種別です。");
        };
    }
}
