package com.example.rpg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/// モンスタークラス
public class Monster extends GameCharacter {
    public Monster(String name, int hp, int attack) {
        super(name, hp, attack);
    }
}
