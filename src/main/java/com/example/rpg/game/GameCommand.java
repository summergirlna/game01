package com.example.rpg.game;

/// ゲーム中のコマンド
public enum GameCommand {
    EXPLORE,
    STATUS,
    QUIT,
    INVALID;

    public static GameCommand fromInput(String input) {
        return switch (input) {
            case "1" -> EXPLORE;
            case "2" -> STATUS;
            case "3" -> QUIT;
            default -> INVALID;
        };
    }
}
