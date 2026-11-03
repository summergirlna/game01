package com.example.rpg.ui;

import com.example.rpg.field.FieldArea;

/// ゲーム画面のメッセージ表示を担当するクラス
public class GameMessage {

    /// ゲーム開始メッセージを表示する
    public void showStart() {
        System.out.println("=== ゲーム開始 ===");
    }

    /// ゲーム終了メッセージを表示する
    public void showEnd() {
        System.out.println("ゲームを終了します。");
    }

    /// メニューを表示する
    /// @param currentArea 現在のエリア
    public void showMenu(FieldArea currentArea) {
        if (currentArea == FieldArea.TOWN) {
            showTownMenu();
            return;
        }

        showFieldMenu();
    }

    /// 町メニューを表示する
    private void showTownMenu() {
        System.out.println();
        System.out.println("何をしますか？");
        System.out.println("1. ステータスを見る");
        System.out.println("2. 宿屋に泊まる");
        System.out.println("3. アイテムを使う");
        System.out.println("4. 装備を変更する");
        System.out.println("5. 道具屋で買う");
        System.out.println("6. 武器屋で買う");
        System.out.println("7. 移動する");
        System.out.println("8. セーブする");
        System.out.println("9. 終了する");
        System.out.print("> ");
    }

    /// フィールドメニューを表示する
    private void showFieldMenu() {
        System.out.println();
        System.out.println("何をしますか？");
        System.out.println("1. 探索する");
        System.out.println("2. ステータスを見る");
        System.out.println("3. アイテムを使う");
        System.out.println("4. 装備を変更する");
        System.out.println("5. 移動する");
        System.out.println("6. セーブする");
        System.out.println("7. 終了する");
        System.out.print("> ");
    }

    /// 不正なコマンドが入力された時のメッセージを表示する
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
    }

}
