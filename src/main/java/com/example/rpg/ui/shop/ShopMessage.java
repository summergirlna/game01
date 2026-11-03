package com.example.rpg.ui.shop;

import com.example.rpg.item.ItemType;

/// 店メッセージ表示を担当するクラス
public class ShopMessage {

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

    /// ゴールドが足りない時のメッセージを表示する
    /// @param requiredGold 必要なゴールド
    public void showNotEnoughGold(int requiredGold) {
        System.out.println("ゴールドが足りません。");
        System.out.println("必要なゴールド: " + requiredGold);
    }
}
