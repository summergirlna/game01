package com.example.rpg.field;

import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.value.Position;
import lombok.Getter;

/// フィールドマップを表すクラス
@Getter
public class GameMap {
    private final int width;

    private final int height;

    private final Position initialPlayerPosition;

    private final Position townPosition;

    private final Position bossPosition;

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
        this.initialPlayerPosition = playerPosition;
        // 町は右下
        this.townPosition = new Position(width - 1, height - 1);
        // ボスは左上
        this.bossPosition = new Position(0, 0);

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

    /// プレイヤー位置を初期位置に戻す
    public void resetPlayerPosition() {
        playerPosition = initialPlayerPosition;
    }

    /// 指定した座標が町へ戻る位置か判定する
    /// @param position 座標
    /// @return 町へ戻る位置の場合はtrue、町へ戻る位置でない場合はfalse
    public boolean isTownPosition(Position position) {
        return townPosition.getX() == position.getX()
                && townPosition.getY() == position.getY();
    }

    /// プレイヤーが町へ戻る位置にいるか判定する
    /// @return 町へ戻る位置にいる場合はtrue、そうでない場合はfalse
    public boolean isPlayerOnTownPosition() {
        return isTownPosition(playerPosition);
    }

    /// 指定した座標がボスの位置か判定する
    /// ボスは洞窟にいる仕様
    /// @param currentArea 現在のエリア
    /// @param position 座標
    /// @return ボスの位置の場合はtrue、ボスの位置でない場合はfalse
    public boolean isBossPosition(FieldArea currentArea, Position position) {
        return currentArea == FieldArea.CAVE
                && bossPosition.getX() == position.getX()
                && bossPosition.getY() == position.getY();
    }

    /// プレイヤーがボスにいるか判定する
    /// @param currentArea 現在のエリア
    /// @return ボスにいる場合はtrue、そうでない場合はfalse
    public boolean isPlayerOnBossPosition(FieldArea currentArea) {
        return isBossPosition(currentArea, playerPosition);
    }
}
