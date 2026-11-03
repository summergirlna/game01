package com.example.rpg;

/// 戦闘中のプレイヤーコマンド
public enum BattleCommand {
    ATTACK,
    HEAL,
    ESCAPE,
    INVALID;

    public static BattleCommand fromInput(String input) {
        return switch (input) {
            case "1" -> ATTACK;
            case "2" -> HEAL;
            case "3" -> ESCAPE;
            default -> INVALID;
        };
    }
}
