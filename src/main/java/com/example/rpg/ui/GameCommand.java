package com.example.rpg.ui;

/// ゲーム中のコマンド
public enum GameCommand {
    EXPLORE,
    STATUS,
    INN,
    ITEM,
    EQUIPMENT,
    MOVE,
    SHOP,
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
            case "4" -> ITEM;
            case "5" -> EQUIPMENT;
            case "6" -> MOVE;
            case "7" -> SHOP;
            case "8" -> QUIT;
            default -> INVALID;
        };
    }
}
