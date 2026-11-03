package com.example.rpg.ui;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;

/// ステータス表示を担当するクラス
public class StatusMessage {

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
        System.out.println("銅の剣所持: " + (player.getEquipment().getInventory().hasWeapon(WeaponType.COPPER_SWORD) ? "あり" : "なし"));
        System.out.println("経験値: " + player.getExp());
        System.out.println("ゴールド: " + player.getGold());
        System.out.println("薬草: " + player.getInventory().getAmount(ItemType.HERB));
    }
}
