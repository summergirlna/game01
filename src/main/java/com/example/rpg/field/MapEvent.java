package com.example.rpg.field;

import com.example.rpg.value.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// マップイベント
@Getter
@RequiredArgsConstructor
public enum MapEvent {
    EXIT('出', new Position(4, 0)),
    INN('宿', new Position(0, 2)),
    ITEM_SHOP('道', new Position(4, 2)),
    WEAPON_SHOP('武', new Position(0, 0)),
    SAVE_POINT('記', new Position(2, 4)),
    BOSS('B', new Position(0, 0)),
    CHEST('C', new Position(2, 1));

    private final char symbol;

    private final Position position;

    /// 指定したエリアで表示されるイベントか判定する
    ///
    /// @param currentArea 現在地
    /// @return 表示される場合はtrue、そうでない場合はfalse
    public boolean isVisibleIn(FieldArea currentArea) {
        return switch (this) {
            case EXIT -> true;
            case INN, ITEM_SHOP, WEAPON_SHOP, SAVE_POINT -> currentArea == FieldArea.TOWN;
            case BOSS -> currentArea == FieldArea.CAVE;
            case CHEST -> currentArea == FieldArea.FOREST;
        };
    }

    /// 指定した座標にあるイベントか判定する
    ///
    /// @param position 座標
    /// @return 同じ座標の場合はtrue、そうでない場合はfalse
    public boolean isAt(Position position) {
        return this.position.getX() == position.getX() && this.position.getY() == position.getY();
    }
}
