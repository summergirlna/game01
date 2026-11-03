package com.example.rpg.ui;

import com.example.rpg.field.FieldArea;

/// ゲーム中のコマンド
public enum GameCommand {
    EXPLORE,
    STATUS,
    INN,
    ITEM,
    EQUIPMENT,
    MOVE,
    SHOP,
    WEAPON_SHOP,
    SAVE,
    QUIT,
    INVALID;

    /// コマンドを入力から取得する
    /// @param input 入力
    /// @param currentArea 現在地
    /// @return コマンド
    public static GameCommand fromInput(String input, FieldArea currentArea) {
        if (currentArea == FieldArea.TOWN) {
            return fromTownInput(input);
        }

        return fromFieldInput(input);
    }

    /// 町用コマンドを入力から取得する
    /// @param input 入力
    /// @return コマンド
    private static GameCommand fromTownInput(String input) {
        return switch (input) {
            case "1" -> STATUS;
            case "2" -> INN;
            case "3" -> ITEM;
            case "4" -> EQUIPMENT;
            case "5" -> SHOP;
            case "6" -> WEAPON_SHOP;
            case "7" -> MOVE;
            case "8" -> SAVE;
            case "9" -> QUIT;
            default -> INVALID;
        };
    }

    /// フィールド用コマンドを入力から取得する
    /// @param input 入力
    /// @return コマンド
    private static GameCommand fromFieldInput(String input) {
        return switch (input) {
            case "1" -> EXPLORE;
            case "2" -> STATUS;
            case "3" -> ITEM;
            case "4" -> EQUIPMENT;
            case "5" -> MOVE;
            case "6" -> SAVE;
            case "7" -> QUIT;
            default -> INVALID;
        };
    }
}
