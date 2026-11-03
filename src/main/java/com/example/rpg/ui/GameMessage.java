package com.example.rpg.ui;

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
    public void showMenu() {
        System.out.println();
        System.out.println("何をしますか？");
        System.out.println("1. 探索する");
        System.out.println("2. ステータスを見る");
        System.out.println("3. 宿屋に泊まる");
        System.out.println("4. アイテムを使う");
        System.out.println("5. 装備を変更する");
        System.out.println("6. 移動する");
        System.out.println("7. 道具屋で買う");
        System.out.println("8. 終了する");
        System.out.print("> ");
    }

    /// 不正なコマンドが入力された時のメッセージを表示する
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
    }

}
