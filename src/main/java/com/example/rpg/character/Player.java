package com.example.rpg.character;

import com.example.rpg.equipment.Equipment;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.Inventory;
import com.example.rpg.item.ItemType;
import com.example.rpg.value.Gold;
import lombok.Getter;

/// プレイヤークラス
@Getter
public class Player extends GameCharacter {
    private final LevelTable levelTable;

    private Level level;

    private final Inventory inventory;

    private final Equipment equipment;

    private FieldArea currentArea;

    private int exp;

    private Gold gold;

    private boolean leveledUp;

    private int lastMaxHpIncrease;

    private int lastAttackIncrease;

    public Player(String name, int hp, int attack) {
        super(name, hp, attack);
        this.levelTable = new LevelTable();
        this.level = Level.initial();
        this.inventory = new Inventory();
        // 初期は薬草を3つもたせる
        this.inventory.add(ItemType.HERB, 3);
        this.equipment = new Equipment();
        // 初期は町にいることにする
        this.currentArea = FieldArea.TOWN;
        this.exp = 0;
        this.gold = Gold.zero();
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

    /// ゴールドを返す
    /// @return ゴールド
    public int getGold() {
        return gold.getValue();
    }

    /// ゴールドを獲得する
    /// @param amount 獲得するゴールド
    public void addGold(int amount) {
        gold = gold.add(amount);
    }

    /// ゴールドを払う
    /// @param amount 払うゴールドの量
    /// @return 払い成功した場合はtrue、そうでない場合はfalse
    public boolean payGold(int amount) {
        if (!gold.hasEnough(amount)) {
            return false;
        }

        gold = gold.subtract(amount);
        return true;
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

        increaseMaxHp(maxHpIncrease);
        increaseAttack(attackIncrease);

        lastMaxHpIncrease += maxHpIncrease;
        lastAttackIncrease += attackIncrease;
    }

    /// 基礎攻撃力を取得する
    /// @return 装備補正を含まない攻撃力
    public int getBaseAttack() {
        return super.getAttack();
    }

    /// 装備込みの攻撃力を取得する
    /// @return 装備込みの攻撃力
    @Override
    public int getAttack() {
        return super.getAttack() + equipment.getAttackBonus();
    }

    /// 現在地を変更する
    /// @param currentArea 現在地
    public void moveTo(FieldArea currentArea) {
        this.currentArea = currentArea;
    }
}
