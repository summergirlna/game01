package com.example.rpg.ui;

import com.example.rpg.item.ItemType;

/// アイテムメッセージ表示を担当するクラス
public class ItemMessage {

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
}
