package com.example.rpg.ui;

import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;

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

    /// ステータスを表示する
    /// @param player プレイヤー
    public void showStatus(Player player) {
        System.out.println("=== ステータス ===");
        System.out.println("名前: " + player.getName());
        System.out.println("現在地: " + player.getCurrentArea().getDisplayName());
        System.out.println("レベル: " + player.getLevel().getValue());
        System.out.println("HP: " + player.getHp() + " / " + player.getMaxHp());
        System.out.println("攻撃力: " + player.getAttack());
        System.out.println("武器: " + player.getEquipment().getWeapon().getDisplayName());
        System.out.println("経験値: " + player.getExp());
        System.out.println("ゴールド: " + player.getGold());
        System.out.println("薬草: " + player.getInventory().getAmount(ItemType.HERB));
    }

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

    /// アイテムを使用した時のメッセージを表示する
    /// @param itemType 使用したアイテム
    public void showUseItem(ItemType itemType) {
        System.out.println(itemType.getDisplayName() + "を使った。");
        System.out.println("HPが " + itemType.getHealAmount() + " 回復した。");
    }

    /// アイテムを持っていない時のメッセージを表示する
    /// @param itemType 使用しようとしたアイテム
    public void showNoItem(ItemType itemType) {
        System.out.println(itemType.getDisplayName() + "を持っていません。");
    }

    /// 武器を装備した時のメッセージを表示する
    /// @param weapon 装備した武器
    public void showEquipWeapon(WeaponType weapon) {
        System.out.println(weapon.getDisplayName() + "を装備した。");
        System.out.println("攻撃力が " + weapon.getAttackBonus() + " 上がる。");
    }

    /// アイテムを購入した時のメッセージを表示する
    /// @param itemType 購入したアイテム
    /// @param price 価格
    public void showBuyItem(ItemType itemType, int price) {
        System.out.println(itemType.getDisplayName() + "を買った。");
        System.out.println(price + " ゴールドを支払った。");
    }

    /// 店がない時のメッセージを表示する
    public void showNoShop() {
        System.out.println("ここには道具屋がない。");
    }

    /// エリアを探索した時のメッセージを表示する
    /// @param area 探索するエリア
    public void showExploreArea(FieldArea area) {
        System.out.println(area.getDisplayName() + "を探索した。");
    }

    /// 町が安全であることを表示する
    public void showSafeTown() {
        System.out.println("町は安全だ。モンスターは出ない。");
    }

    /// 何も起きなかった時のメッセージを表示する
    public void showNoEvent() {
        System.out.println("特に何も起きなかった。");
    }

    /// 戦闘結果を表示する
    /// @param result 戦闘結果
    public void showBattleResult(BattleResult result) {
        System.out.println("戦闘結果: " + result);
    }

    /// エリア移動メッセージを表示する
    /// @param area 移動先エリア
    public void showMoveArea(FieldArea area) {
        System.out.println(area.getDisplayName() + "へ移動した。");
    }
}
