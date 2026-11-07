package com.example.rpg.character;

/// レベルごとの成長情報を管理するクラス
public class LevelTable {

    /// 次のレベルに必要な累計経験値を取得する
    ///
    /// @param level 現在のレベル
    /// @return 次のレベルに必要な累計経験値
    public int getRequiredExpForNextLevel(Level level) {
        return switch (level.getValue()) {
            case 1 -> 10;
            case 2 -> 25;
            case 3 -> 50;
            case 4 -> 90;
            case 5 -> 140;
            default -> level.getValue() * 50;
        };
    }

    /// レベルアップ時の最大HP上昇量を取得する
    ///
    /// @param nextLevel 次のレベル
    /// @return 最大HP上昇量
    public int getMaxHpIncrease(Level nextLevel) {
        return switch (nextLevel.getValue()) {
            case 2 -> 5;
            case 3 -> 6;
            case 4 -> 8;
            case 5 -> 10;
            default -> 10;
        };
    }

    /// レベルアップ時の攻撃力上昇量を取得する
    ///
    /// @param nextLevel 次のレベル
    /// @return 攻撃力上昇量
    public int getAttackIncrease(Level nextLevel) {
        return switch (nextLevel.getValue()) {
            case 2 -> 2;
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 3;
            default -> 4;
        };
    }
}
