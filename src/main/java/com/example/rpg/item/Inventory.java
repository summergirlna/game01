package com.example.rpg.item;

import java.util.EnumMap;
import java.util.Map;

/// 所持アイテムを管理するクラス
public class Inventory {
    private final Map<ItemType, Integer> items = new EnumMap<>(ItemType.class);

    /// アイテムを追加する
    /// @param itemType アイテム種別
    /// @param amount 追加数
    public void add(ItemType itemType, int amount) {
        int currentAmount = getAmount(itemType);
        items.put(itemType, currentAmount + amount);
    }

    /// アイテムを使う
    /// @param itemType アイテム種別
    /// @return 使用できた場合はtrue、所持していない場合はfalse
    public boolean use(ItemType itemType) {
        int currentAmount = getAmount(itemType);

        if (currentAmount <= 0) {
            return false;
        }

        items.put(itemType, currentAmount -1);
        return true;
    }

    /// アイテムの所持数を取得する
    /// @param itemType アイテム種別
    /// @return 所持数
    public int getAmount(ItemType itemType) {
        return items.getOrDefault(itemType, 0);
    }

    /// アイテムの所持数を設定する
    /// @param itemType アイテム種別
    /// @param amount 所持数
    public void setAmount(ItemType itemType, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("アイテム数は0以上である必要があります。");
        }

        items.put(itemType, amount);
    }
}
