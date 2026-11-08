package com.example.rpg.service;

import com.example.rpg.field.MapMode;

/// Webゲーム操作結果
///
/// @param message メッセージ
/// @param redirectPath リダイレクト先
/// @param nextMapMode 次のマップ操作モード。変更しない場合はnull
public record GameActionResult(String message, String redirectPath, MapMode nextMapMode) {
    public static GameActionResult message(String message) {
        return new GameActionResult(message, null, null);
    }

    public static GameActionResult message(String message, MapMode nextMapMode) {
        return new GameActionResult(message, null, nextMapMode);
    }

    public static GameActionResult redirect(String message, String redirectPath) {
        return new GameActionResult(message, redirectPath, null);
    }

    public boolean hasRedirect() {
        return redirectPath != null;
    }

    public boolean hasNextMapMode() {
        return nextMapMode != null;
    }
}
