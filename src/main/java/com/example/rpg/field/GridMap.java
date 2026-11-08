package com.example.rpg.field;

import com.example.rpg.value.Position;
import lombok.Getter;

/// グリッド形式のマップ基底クラス
@Getter
public abstract class GridMap {
    private final int width;

    private final int height;

    protected GridMap(int width, int height, String mapName) {
        if (width <= 0) {
            throw new IllegalArgumentException(mapName + "の幅は1以上である必要があります。");
        }

        if (height <= 0) {
            throw new IllegalArgumentException(mapName + "の高さは1以上である必要があります。");
        }

        this.width = width;
        this.height = height;
    }

    /// 指定した座標がマップ範囲内か判定する
    ///
    /// @param position 座標
    /// @return 範囲内の場合はtrue、範囲外の場合はfalse
    public boolean isInBounds(Position position) {
        return position.getX() >= 0
                && position.getX() < width
                && position.getY() >= 0
                && position.getY() < height;
    }
}
