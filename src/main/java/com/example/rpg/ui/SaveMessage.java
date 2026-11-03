package com.example.rpg.ui;

/// セーブメッセージ表示を担当するクラス
public class SaveMessage {
    /// セーブ成功メッセージを表示する
    /// @param fileName セーブファイル名
    public void showSaveSuccess(String fileName) {
        System.out.println(fileName + " にセーブしました。");
    }

    /// セーブ失敗メッセージを表示する
    public void showSaveFailed() {
        System.out.println("セーブに失敗しました。");
    }
}
