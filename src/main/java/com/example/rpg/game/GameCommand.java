package com.example.rpg.game;

/// ゲーム中のコマンド
public enum GameCommand {
    EXPLORE,
    STATUS,
    INN,
    QUIT,
    INVALID;

    /// コマンドを入力から取得する
    /// @param input 入力
    /// @return コマンド
    public static GameCommand fromInput(String input) {
        return switch (input) {
            case "1" -> EXPLORE;
            case "2" -> STATUS;
            case "3" -> INN;
            case "4" -> QUIT;
            default -> INVALID;
        };
    }
}
