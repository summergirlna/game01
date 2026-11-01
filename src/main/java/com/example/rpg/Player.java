package com.example.rpg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/// プレイヤークラス
public class Player extends GameCharacter {
    public Player(String name, int hp, int attack) {
        super(name, hp, attack);
    }
}
