package com.example.rpg.ui.command;

/// マップ操作コマンド
public enum MapCommand {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    MENU,
    INVALID;

    /// コマンドを入力から取得する
    ///
    /// @param input 入力
    /// @return マップ操作コマンド
    public static MapCommand fromInput(String input) {
        return switch (input) {
            case "w" -> UP;
            case "s" -> DOWN;
            case "a" -> LEFT;
            case "d" -> RIGHT;
            case "m" -> MENU;
            default -> INVALID;
        };
    }
}
