package com.example.rpg.ui;

/// 宿屋メッセージ表示を担当するクラス
public class InnMessage {

    /// 宿屋に泊まった時のメッセージを表示する
    /// @param cost 宿泊料金
    public void showStayInn(int cost) {
        System.out.println("宿屋に泊まった。");
        System.out.println(cost + " ゴールドを支払った。");
        System.out.println("HPが全回復した。");
    }

    /// ゴールドが足りない時のメッセージを表示する
    /// @param requiredGold 必要なゴールド
    public void showNotEnoughGold(int requiredGold) {
        System.out.println("ゴールドが足りません。");
        System.out.println("必要なゴールド: " + requiredGold);
    }
}
