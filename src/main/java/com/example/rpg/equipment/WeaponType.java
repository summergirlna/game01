package com.example.rpg.equipment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// 武器種別
@Getter
@RequiredArgsConstructor
public enum WeaponType {
    WOODEN_SWORD("木の剣", 2),
    COPPER_SWORD("銅の剣", 5);

    private final String displayName;

    private final int attackBonus;
}
