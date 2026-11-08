package com.example.rpg.field;

import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.value.Position;
import lombok.Getter;

import java.util.Arrays;

/// 世界マップを表すクラス
public class WorldMap extends GridMap {
    private static final WorldMapArea[] AREAS = {
            new WorldMapArea(FieldArea.TOWN, new Position(0, 1), '町'),
            new WorldMapArea(FieldArea.GRASSLAND, new Position(1, 1), '草'),
            new WorldMapArea(FieldArea.FOREST, new Position(2, 1), '森'),
            new WorldMapArea(FieldArea.CAVE, new Position(1, 2), '洞')
    };

    private Position playerPosition;

    public WorldMap(int width, int height, Position playerPosition) {
        super(width, height, "世界マップ");

        if (!isInBounds(playerPosition)) {
            throw new IllegalArgumentException("プレイヤーの初期位置が世界マップ範囲外です。");
        }

        this.playerPosition = playerPosition;
    }

    /// 初期世界マップ生成
    /// @return 初期世界マップ
    public static WorldMap initial() {
        return new WorldMap(3, 3, new Position(0, 1));
    }

    /// 指定した方向へ移動する
    ///
    /// @param command マップ操作コマンド
    /// @return 移動できた場合はtrue、移動できなかった場合はfalse
    public boolean move(MapCommand command) {
        Position nextPosition = switch (command) {
            case UP -> playerPosition.up();
            case DOWN -> playerPosition.down();
            case LEFT -> playerPosition.left();
            case RIGHT -> playerPosition.right();
            case ACTION, MENU, INVALID -> playerPosition;
        };

        if (!isInBounds(nextPosition)) {
            return false;
        }

        playerPosition = nextPosition;
        return true;
    }

    /// 指定した座標にある世界マップエリアを取得する
    /// @param position 座標
    /// @return 世界マップエリア。存在しない場合はnull
    public WorldMapArea getAreaAt(Position position) {
        return Arrays.stream(AREAS)
                .filter(area -> area.isAt(position))
                .findFirst()
                .orElse(null);
    }

    /// 指定したフィールドエリアの世界マップ座標を取得する
    ///
    /// @param fieldArea フィールドエリア
    /// @return 世界マップ座標
    public Position getPositionOf(FieldArea fieldArea) {
        return Arrays.stream(AREAS)
                .filter(area -> area.fieldArea() == fieldArea)
                .map(WorldMapArea::position)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("世界マップ上に存在しないエリアです。"));
    }

    /// 指定した座標が現在地か判定する
    ///
    /// @param position 座標
    /// @return 現在地の場合はtrue
    public boolean isPlayerPosition(Position position) {
        return playerPosition.getX() == position.getX()
                && playerPosition.getY() == position.getY();
    }

    /// プレイヤー位置にある世界マップエリアを取得する
    ///
    /// @return 世界マップエリア。存在しない場合はnull
    public WorldMapArea getPlayerArea() {
        return getAreaAt(playerPosition);
    }

    /// 世界マップ上のエリア
    /// @param fieldArea フィールドエリア
    /// @param position 世界マップ座標
    /// @param symbol 表示記号
    public record WorldMapArea(FieldArea fieldArea, Position position, char symbol) {

        /// 指定した座標にあるエリアか判定する
        /// @param targetPosition 座標
        /// @return 同じ座標の場合はtrue
        public boolean isAt(Position targetPosition) {
            return position.getX() == targetPosition.getX()
                    && position.getY() == targetPosition.getY();
        }
    }
}
