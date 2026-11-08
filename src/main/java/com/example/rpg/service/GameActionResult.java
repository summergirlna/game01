package com.example.rpg.service;

import com.example.rpg.field.MapMode;

/// Webゲーム操作結果
///
/// @param message メッセージ
/// @param redirectPath リダイレクト先
public record GameActionResult(String message, String redirectPath) {
    public static GameActionResult message(String message) {
        return new GameActionResult(message, null);
    }

    public static GameActionResult redirect(String message, String redirectPath) {
        return new GameActionResult(message, redirectPath);
    }

    public boolean hasRedirect() {
        return redirectPath != null;
    }
}
