package com.example.rpg.ui.title;

/// タイトル画面のメッセージ表示を担当するクラス
public class TitleMessage {

    /// タイトル画面を表示する
    public void showTitle() {
        System.out.println();
        System.out.println("=== RPG ===");
        System.out.println("1. はじめから");
        System.out.println("2. つづきから");
        System.out.println("3. 終了する");
        System.out.print("> ");
    }

    /// はじめから開始するメッセージを表示する
    public void showNewGame() {
        System.out.println("はじめから開始します。");
    }

    /// つづきから開始するメッセージを表示する
    public void showLoadGame() {
        System.out.println("つづきから開始します。");
    }

    /// 不正なコマンドが入力された時のメッセージを表示する
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
    }
}
