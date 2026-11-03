package com.example.rpg.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// マップ上の座標を表す
@Getter
@RequiredArgsConstructor
public class Position {
    private final int x;

    private final int y;

    /// 上に移動した座標を取得する
    /// @return 上に移動した座標
    public Position up() {
        return new Position(x, y - 1);
    }

    /// 下に移動した座標を取得する
    /// @return 下に移動した座標
    public Position down() {
        return new Position(x, y + 1);
    }

    /// 左に移動した座標を取得する
    /// @return 左に移動した座標
    public Position left() {
        return new Position(x - 1, y);
    }

    /// 右に移動した座標を取得する
    /// @return 右に移動した座標
    public Position right() {
        return new Position(x + 1, y);
    }
}
