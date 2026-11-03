package com.example.rpg.character;

import lombok.Getter;

/// モンスタークラス
@Getter
public class Monster extends GameCharacter {
    private final int exp;

    private final int gold;

    public Monster(String name, int hp, int attack, int exp, int gold) {
        super(name, hp, attack);
        this.exp = exp;
        this.gold = gold;
    }
}
