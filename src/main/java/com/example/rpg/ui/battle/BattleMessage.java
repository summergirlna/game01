package com.example.rpg.ui.battle;

import com.example.rpg.character.GameCharacter;
import com.example.rpg.character.Player;
import com.example.rpg.item.ItemType;

/// 戦闘メッセージ
public class BattleMessage {

    /// モンスター出現メッセージ
    /// @param character モンスター
    public void showAppear(GameCharacter character) {
        System.out.println(character.getName() + "が現れた!");
    }

    /// コマンド選択メッセージ
    public void showCommandMenu() {
        System.out.println();
        System.out.println("どうする？");
        System.out.println("1. 攻撃する");
        System.out.println("2. 薬草を使う");
        System.out.println("3. 逃げる");
        System.out.print("> ");
    }

    /// 攻撃メッセージ
    /// @param attacker 攻撃者
    /// @param target 相手
    /// @param damage ダメージ
    public void showAttack(GameCharacter attacker, GameCharacter target, int damage) {
        System.out.println(attacker.getName() + "の攻撃!");
        System.out.println(target.getName() + "に" + damage + "ダメージ!");
    }

    /// 回復メッセージ
    /// @param character 回復するキャラクター
    /// @param amount 回復量
    public void showHeal(GameCharacter character, int amount) {
        System.out.println(character.getName() + "はHPを" + amount + "回復した!");
    }

    /// アイテムを所持していない時のメッセージを表示する
    /// @param itemType アイテム種別
    public void showNoItem(ItemType itemType) {
        System.out.println(itemType.getDisplayName() + "を持っていません。");
    }

    /// アイテム使用メッセージを表示する
    /// @param character 使用したキャラクター
    /// @param itemType アイテム種別
    public void showUseItem(GameCharacter character, ItemType itemType) {
        System.out.println(character.getName() + "は" + itemType.getDisplayName() + "を使った！");
        System.out.println(character.getName() + "のHPが" + itemType.getHealAmount() + "回復した！");
    }

    /// 逃走メッセージ
    /// @param character 逃げるキャラクター
    public void showEscape(GameCharacter character) {
        System.out.println(character.getName() + "は逃げ出した!");
    }

    /// 入力ミスメッセージ
    public void showInvalidCommand() {
        System.out.println("正しいコマンドを入力してください。");
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

    /// 報酬獲得メッセージ
    /// @param exp 獲得経験値
    /// @param gold 獲得ゴールド
    public void showReward(int exp, int gold) {
        System.out.println("経験値を" + exp + "獲得した!");
        System.out.println(gold + "ゴールドを手に入れた!");
    }

    /// レベルアップメッセージ
    /// @param player プレイヤー
    public void showLevelUp(Player player) {
        System.out.println(player.getName() + "はレベル" + player.getLevel().getValue() + "に上がった!");
        System.out.println("最大HPが" + player.getLastMaxHpIncrease() + "上がった!");
        System.out.println("攻撃力が" + player.getLastAttackIncrease() + "上がった!");
        System.out.println("最大HP: " + player.getMaxHp());
        System.out.println("攻撃力: " + player.getAttack());
    }

    /// 敗北メッセージ
    /// @param player プレイヤー
    public void showLose(GameCharacter player) {
        System.out.println(player.getName() + "は倒れてしまった...");
        System.out.println("ゲームオーバー");
    }
}
