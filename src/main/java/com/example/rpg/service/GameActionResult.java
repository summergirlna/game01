package com.example.rpg.service;

/// Webゲーム操作結果
///
/// @param message メッセージ
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
