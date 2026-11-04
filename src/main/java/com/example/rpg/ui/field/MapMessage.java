package com.example.rpg.ui.field;

import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.value.Position;

/// マップメッセージ表示を担当するクラス
public class MapMessage {

    /// マップを表示する
    /// @param currentArea 現在のフィールドエリア
    /// @param gameMap マップ情報
    public void showMap(FieldArea currentArea, GameMap gameMap) {
        System.out.println();
        System.out.println("現在地: " + currentArea.getDisplayName());
        System.out.println();

        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                Position position = new Position(x, y);

                if (gameMap.isPlayerPosition(position)) {
                    System.out.print("P");
                } else if (gameMap.isBossPosition(currentArea, position)) {
                    System.out.print("B");
                } else if (gameMap.isTownPosition(position)) {
                    System.out.print("T");
                } else {
                    System.out.print(".");
                }

            }

            System.out.println();
        }

        System.out.println();
    }

    /// マップ操作メニューを表示する
    public void showCommandMenu() {
        System.out.println("w: 上  s: 下  a: 左  d: 右  m: メニュー");
        System.out.print("> ");
    }

    /// マップ範囲外へ移動しようとした時のメッセージを表示する
    public void showCannotMove() {
        System.out.println("これ以上そちらには進めません。");
    }

    /// 不正なコマンドが入力された時のメッセージを表示する
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
    }

    /// 通常メニューへ戻るメッセージを表示する
    public void showBackToMenu() {
        System.out.println("メニューに戻ります。");
    }

    /// 町へ戻るメッセージを表示する
    public void showReturnToTown() {
        System.out.println("町へ戻った。");
    }

    /// ボス戦開始メッセージを表示する
    public void showBossEncounter() {
        System.out.println("強大な敵が立ちはだかった！");
    }

    /// ゲームクリアメッセージを表示する
    public void showGameClear() {
        System.out.println("ボスを倒した！");
        System.out.println("世界に平和が戻った。");
        System.out.println("=== GAME CLEAR ===");
    }
}
