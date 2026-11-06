package com.example.rpg.web;

/// マップ表示用セル
public record MapCell(
        int x,
        int y,
        String symbol,
        String cssClass
) {
}
