package com.example.rpg.character;

import lombok.Getter;

/// プレイヤークラス
@Getter
public class Player extends GameCharacter {
    private Level level;

    private int exp;

    private int gold;

    private boolean leveledUp;

    public Player(String name, int hp, int attack) {
        super(name, hp, attack);
        this.level = Level.initial();
        this.exp = 0;
        this.gold = 0;
        this.leveledUp = false;
    }

    /// 経験値を獲得する
    /// @param exp 獲得する経験値
    public void addExp(int exp) {
        this.exp += exp;
        checkLevelUp();
    }

    /// ゴールドを獲得する
    /// @param gold 獲得するゴールド
    public void addGold(int gold) {
        this.gold += gold;
    }

    /// レベルアップしたかどうかを返す
    /// @return レベルアップした場合はtrue
    public boolean hasLeveledUp() {
        return leveledUp;
    }

    /// レベルアップ状態をリセットする
    public void resetLeveledUp() {
        leveledUp = false;
    }

    /// レベルアップを判定する
    private void checkLevelUp() {
        leveledUp = false;

        while (exp >= level.getRequiredExpForNextLevel()) {
            levelUp();
        }
    }

    /// レベルアップ処理
    private void levelUp() {
        level = level.next();
        leveledUp = true;

        increaseMaxHp(5);
        increaseAttack(2);
    }
}
