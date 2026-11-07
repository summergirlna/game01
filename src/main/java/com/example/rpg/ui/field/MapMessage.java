package com.example.rpg.ui.field;

import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.value.Position;

/// マップメッセージ表示を担当するクラス
public class MapMessage {

    /// マップを表示する
    ///
    /// @param currentArea 現在のフィールドエリア
    /// @param gameMap     マップ情報
    public void showMap(FieldArea currentArea, GameMap gameMap) {
        System.out.println();
        System.out.println("現在地: " + currentArea.getDisplayName());
        System.out.println();

        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                Position position = new Position(x, y);

                if (gameMap.isPlayerPosition(position)) {
                    System.out.print("P");
                    continue;
                }

                MapEvent event = gameMap.getEventAt(currentArea, position);

                if (event != null) {
                    System.out.print(event.getSymbol());
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
        System.out.println("P: 現在地  T: 町  N: 次のエリア  R: 前のエリア  B: ボス  C: 宝箱");
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

    /// エリア移動メッセージを表示する
    ///
    /// @param area 移動先エリア
    public void showMoveArea(FieldArea area) {
        System.out.println(area.getDisplayName() + "へ移動した。");
    }

    /// ボス戦開始メッセージを表示する
    public void showBossEncounter() {
        System.out.println("強大な敵が立ちはだかった！");
    }

    /// 宝箱を開けた時のメッセージを表示する
    ///
    /// @param gold       獲得ゴールド
    /// @param herbAmount 獲得した薬草の数
    public void showOpenChest(int gold, int herbAmount) {
        System.out.println("宝箱を開けた！");
        System.out.println(gold + " ゴールドを手に入れた。");
        System.out.println("薬草を " + herbAmount + " 個手に入れた。");
    }

    /// 開封済みの宝箱を調べた時のメッセージを表示する
    public void showOpenedChest() {
        System.out.println("宝箱は空だった。");
    }

    /// ゲームクリアメッセージを表示する
    public void showGameClear() {
        System.out.println("ボスを倒した！");
        System.out.println("世界に平和が戻った。");
        System.out.println("=== GAME CLEAR ===");
    }
}
