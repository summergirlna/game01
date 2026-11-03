package com.example.rpg.character;

import lombok.Getter;

/// プレイヤークラス
@Getter
public class Player extends GameCharacter {
    private final LevelTable levelTable;

    private Level level;

    private int exp;

    private int gold;

    private boolean leveledUp;

    private int lastMaxHpIncrease;

    private int lastAttackIncrease;

    public Player(String name, int hp, int attack) {
        super(name, hp, attack);
        this.levelTable = new LevelTable();
        this.level = Level.initial();
        this.exp = 0;
        this.gold = 0;
        this.leveledUp = false;
        this.lastMaxHpIncrease = 0;
        this.lastAttackIncrease = 0;
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
        lastMaxHpIncrease = 0;
        lastAttackIncrease = 0;
    }

    /// レベルアップを判定する
    private void checkLevelUp() {
        leveledUp = false;
        lastMaxHpIncrease = 0;
        lastAttackIncrease = 0;

        while (exp >= levelTable.getRequiredExpForNextLevel(level)) {
            levelUp();
        }
    }

    /// レベルアップ処理
    private void levelUp() {
        Level nextLevel = level.next();

        int maxHpIncrease = levelTable.getMaxHpIncrease(nextLevel);
        int attackIncrease = levelTable.getAttackIncrease(nextLevel);

        level = nextLevel;
        leveledUp = true;

        increaseMaxHp(levelTable.getMaxHpIncrease(nextLevel));
        increaseAttack(levelTable.getAttackIncrease(nextLevel));

        lastMaxHpIncrease += maxHpIncrease;
        lastAttackIncrease += attackIncrease;
    }
}
