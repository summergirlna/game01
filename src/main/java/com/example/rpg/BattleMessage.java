package com.example.rpg;

/// 戦闘メッセージ
public class BattleMessage {
    /// RPG開始メッセージ
    public void showStart() {
        System.out.println("=== RPGを開始します! ===");
    }

    /// モンスター出現メッセージ
    /// @param character モンスター
    public void showAppear(GameCharacter character) {
        System.out.println(character.getName() + "が現れた!");
    }

    /// 攻撃メッセージ
    /// @param attacker 攻撃者
    /// @param target 相手
    /// @param damage ダメージ
    public void showAttack(GameCharacter attacker, GameCharacter target, int damage) {
        System.out.println(attacker.getName() + "の攻撃!");
        System.out.println(target.getName() + "に" + damage + "ダメージ!");
    }

    /// HP表示メッセージ
    /// @param player プレイヤー
    /// @param monster モンスター
    public void showHp(GameCharacter player, GameCharacter monster) {
        System.out.println(player.getName() + " HP: " + player.getHp());
        System.out.println(monster.getName() + " HP: " + monster.getHp());
        System.out.println();
    }

    /// 勝利メッセージ
    /// @param monster モンスター
    public void showWin(GameCharacter monster) {
        System.out.println(monster.getName() + "を倒した!");
        System.out.println("勝利!");
    }

    /// 敗北メッセージ
    /// @param player プレイヤー
    public void showLose(GameCharacter player) {
        System.out.println(player.getName() + "は倒れてしまった...");
        System.out.println("ゲームオーバー");
    }
}
