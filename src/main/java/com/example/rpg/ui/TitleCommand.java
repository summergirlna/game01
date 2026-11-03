package com.example.rpg.ui;

/// タイトル画面のコマンド
public enum TitleCommand {
    NEW_GAME,
    LOAD_GAME,
    QUIT,
    INVALID;

    /// コマンドを入力から取得する
    /// @param input 入力
    /// @return コマンド
    public static TitleCommand fromInput(String input) {
        return switch (input) {
            case "1" -> NEW_GAME;
            case "2" -> LOAD_GAME;
            case "3" -> QUIT;
            default -> INVALID;
        };
    }
}
