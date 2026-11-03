package com.example.rpg.ui.equipment;

import com.example.rpg.equipment.WeaponType;

/// 装備メッセージ表示を担当するクラス
public class EquipmentMessage {

    /// 武器を装備した時のメッセージを表示する
    /// @param weapon 装備した武器
    public void showEquipWeapon(WeaponType weapon) {
        System.out.println(weapon.getDisplayName() + "を装備した。");
        System.out.println("攻撃力が " + weapon.getAttackBonus() + " 上がる。");
    }

    /// 武器を持っていない時のメッセージを表示する
    /// @param weapon 所持していない武器
    public void showNoWeapon(WeaponType weapon) {
        System.out.println(weapon.getDisplayName() + "を持っていません。");
    }
}
