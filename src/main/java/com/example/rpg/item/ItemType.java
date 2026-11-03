package com.example.rpg.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// アイテム種別
@Getter
@RequiredArgsConstructor
public enum ItemType {
    HERB("薬草", 10);

    private final String displayName;

    private final int healAmount;
}
