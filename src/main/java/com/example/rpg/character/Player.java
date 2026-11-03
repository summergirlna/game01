package com.example.rpg.character;

import lombok.Getter;

/// プレイヤークラス
@Getter
public class Player extends GameCharacter {
    private int exp;

    private int gold;

    public Player(String name, int hp, int attack) {
        super(name, hp, attack);
        this.exp = 0;
        this.gold = 0;
    }

    /// 経験値を獲得する
    /// @param exp 獲得する経験値
    public void addExp(int exp) {
        this.exp += exp;
    }

    /// ゴールドを獲得する
    /// @param gold 獲得するゴールド
    public void addGold(int gold) {
        this.gold += gold;
    }
}
