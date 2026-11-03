package com.example.rpg.field;

import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.value.Position;
import lombok.Getter;

/// フィールドマップを表すクラス
@Getter
public class GameMap {
    private final int width;

    private final int height;

    private Position playerPosition;

    public GameMap(int width, int height, Position playerPosition) {
        if (width <= 0) {
            throw new IllegalArgumentException("マップの幅は1以上である必要があります。");
        }

        if (height <= 0) {
            throw new IllegalArgumentException("マップの高さは1以上である必要があります。");
        }

        this.width = width;
        this.height = height;

        if (!isInBounds(playerPosition)) {
            throw new IllegalArgumentException("プレイヤーの初期位置がマップ範囲外です。");
        }

        this.playerPosition = playerPosition;
    }

    /// 初期マップ生成
    /// @return 初期マップ
    public static GameMap initial() {
        return new GameMap(5, 5, new Position(2, 2));
    }

    /// 指定した方向へ移動する
    /// @param command マップ操作コマンド
    /// @return 移動できた場合はtrue、移動できなかった場合はfalse
    public boolean move(MapCommand command) {
        Position nextPosition = switch (command) {
            case UP -> playerPosition.up();
            case DOWN -> playerPosition.down();
            case LEFT -> playerPosition.left();
            case RIGHT -> playerPosition.right();
            case MENU, INVALID -> playerPosition;
        };

        if (!isInBounds(nextPosition)) {
            return false;
        }

        playerPosition = nextPosition;
        return true;
    }

    /// 指定した座標がマップ範囲内か判定する
    /// @param position 座標
    /// @return 範囲内の場合はtrue、範囲外の場合はfalse
    public boolean isInBounds(Position position) {
        return position.getX() >= 0
                && position.getX() < width
                && position.getY() >= 0
                && position.getY() < height;
    }

    /// 指定した座標がプレイヤーの位置か判定する
    /// @param position 座標
    /// @return プレイヤーの位置の場合はtrue、プレイヤーの位置でない場合はfalse
    public boolean isPlayerPosition(Position position) {
        return playerPosition.getX() == position.getX()
                && playerPosition.getY() == position.getY();
    }
}
