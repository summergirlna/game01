package com.example.rpg.field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// フィールドエリア
@Getter
@RequiredArgsConstructor
public enum FieldArea {
    GRASSLAND("草原"),
    FOREST("森"),
    CAVE("洞窟");

    private final String displayName;
}
