package com.example.rpg.ui;

/// ロードメッセージの表示を担当するクラス
public class LoadMessage {

    /// ロード成功メッセージを表示する
    /// @param fileName ロードしたファイル名
    public void showLoadSuccess(String fileName) {
        System.out.println(fileName + " からロードしました。");
    }

    /// ロード失敗メッセージを表示する
    public void showLoadFailed() {
        System.out.println("ロードに失敗しました。");
    }

    /// セーブファイルが存在しない時のメッセージを表示する
    public void showSaveFileNotFound() {
        System.out.println("セーブファイルが見つかりません。");
    }

}
