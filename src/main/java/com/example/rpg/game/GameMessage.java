package com.example.rpg.game;

import com.example.rpg.character.Player;

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
        System.out.println("4. 終了する");
        System.out.print("> ");
    }

    /// 不正なコマンドが入力された時のメッセージを表示する
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
    }

    /// ステータスを表示する
    /// @param player プレイヤー
    public void showStatus(Player player) {
        System.out.println("=== ステータス ===");
        System.out.println("名前: " + player.getName());
        System.out.println("レベル: " + player.getLevel().getValue());
        System.out.println("HP: " + player.getHp() + " / " + player.getMaxHp());
        System.out.println("攻撃力: " + player.getAttack());
        System.out.println("経験値: " + player.getExp());
        System.out.println("ゴールド: " + player.getGold());
    }

    /// 宿屋に泊まった時のメッセージを表示する
    /// @param cost 宿泊料金
    public void showStayInn(int cost) {
        System.out.println("宿屋に泊まった。");
        System.out.println(cost + " ゴールドを支払った。");
        System.out.println("HPが全回復した。");
    }

    /// ゴールドが足りない時のメッセージを表示する
    /// @param cost 必要なゴールド
    public void showNotEnoughGold(int cost) {
        System.out.println("ゴールドが足りません。");
        System.out.println("宿泊には " + cost + " ゴールド必要です。");
    }
}
