package com.example.rpg.field;

import lombok.AllArgsConstructor;
import lombok.Getter;

/// マップ探索状態
@Getter
public class MapState {
    private MapMode mode;

    private final WorldMap worldMap;

    private final GameMap gameMap;

    public MapState(WorldMap worldMap, GameMap gameMap, MapMode mode) {
        this.worldMap = worldMap;
        this.gameMap = gameMap;
        this.mode = mode;
    }

    /// 初期マップ探索状態を作成する
    ///
    /// @return 初期マップ探索状態
    public static MapState initial() {
        return new MapState(WorldMap.initial(), GameMap.initial(), MapMode.WORLD);
    }

    /// 世界マップ操作中か判定する
    ///
    /// @return 世界マップ操作中の場合はtrue
    public boolean isWorldMode() {
        return mode == MapMode.WORLD;
    }

    /// 個別マップ操作中か判定する
    ///
    /// @return 個別マップ操作中の場合はtrue
    public boolean isFieldMode() {
        return mode == MapMode.FIELD;
    }

    /// 世界マップ操作へ切り替える
    public void switchToWorld() {
        mode = MapMode.WORLD;
    }

    /// 個別マップ操作へ切り替える
    public void switchToField() {
        mode = MapMode.FIELD;
    }
}
