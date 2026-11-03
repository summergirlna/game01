package com.example.rpg.ui.shop;

import com.example.rpg.equipment.WeaponType;

/// 武器屋メッセージ表示を担当するクラス
public class WeaponShopMessage {

    /// 武器を購入した時のメッセージを表示する
    /// @param weapon 購入した武器
    /// @param price 価格
    public void showBuyWeapon(WeaponType weapon, int price) {
        System.out.println(weapon.getDisplayName() + "を買った。");
        System.out.println(price + " ゴールドを支払った。");
    }

    /// 武器屋がない時のメッセージを表示する
    public void showNoWeaponShop() {
        System.out.println("ここには武器屋がない。");
    }

    /// ゴールドが足りない時のメッセージを表示する
    /// @param requiredGold 必要なゴールド
    public void showNotEnoughGold(int requiredGold) {
        System.out.println("ゴールドが足りません。");
        System.out.println("必要なゴールド: " + requiredGold);
    }

    /// すでに武器を持っている時のメッセージを表示する
    /// @param weapon すでに所持している武器
    public void showAlreadyHasWeapon(WeaponType weapon) {
        System.out.println(weapon.getDisplayName() + "はすでに持っています。");
    }
}
