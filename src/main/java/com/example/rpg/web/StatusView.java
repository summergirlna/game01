package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;

/// ステータス画面表示用モデル
/// @param name 名前
/// @param area 場所
/// @param level レベル
/// @param hp 現在のHP
/// @param maxHp 最大HP
/// @param attack 攻撃力
/// @param weapon 武器
/// @param hasCopperSword 鉄の剣を持っているかどうか
/// @param exp 経験値
/// @param gold 所持金
/// @param herbAmount 薬草の所持数
public record StatusView(
        String name,
        String area,
        int level,
        int hp,
        int maxHp,
        int attack,
        String weapon,
        boolean hasCopperSword,
        int exp,
        int gold,
        int herbAmount
) {

    /// プレイヤーからステータス画面表示用モデルを作成する
    /// @param player プレイヤー
    /// @return ステータス画面表示用モデル
    public static StatusView from(Player player) {
        return new StatusView(
                player.getName(),
                player.getCurrentArea().getDisplayName(),
                player.getLevel().getValue(),
                player.getHp(),
                player.getMaxHp(),
                player.getAttack(),
                player.getEquipment().getWeapon().getDisplayName(),
                player.getEquipment().getInventory().hasWeapon(WeaponType.COPPER_SWORD),
                player.getExp(),
                player.getGold(),
                player.getInventory().getAmount(ItemType.HERB)
        );
    }
}
