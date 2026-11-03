package com.example.rpg.equipment;

import lombok.Getter;

/// 装備を管理するクラス
@Getter
public class Equipment {

    private final EquipmentInventory inventory;

    private WeaponType weapon;

    public Equipment() {
        this.inventory = new EquipmentInventory();
        this.inventory.addWeapon(WeaponType.WOODEN_SWORD);
        this.weapon = WeaponType.WOODEN_SWORD;
    }

    /// 武器を追加する
    /// @param weapon 追加する武器
    public void addWeapon(WeaponType weapon) {
        inventory.addWeapon(weapon);
    }

    /// 武器を装備する
    /// @param weapon 装備する武器
    /// @return 装備できた場合はtrue、所持していない場合はfalse
    public boolean equipWeapon(WeaponType weapon) {
        if (!inventory.hasWeapon(weapon)) {
            return false;
        }

        this.weapon = weapon;
        return true;
    }

    /// 攻撃力補正を取得する
    /// @return 攻撃力補正
    public int getAttackBonus() {
        return weapon.getAttackBonus();
    }

    /// 装備情報を復元する
    /// @param weapon 装備中の武器
    /// @param hasWoodenSword 木の剣を所持しているか
    /// @param hasCopperSword 銅の剣を所持しているか
    public void restore(WeaponType weapon, boolean hasWoodenSword, boolean hasCopperSword) {
        inventory.clearWeapons();

        if (hasWoodenSword) {
            inventory.addWeapon(WeaponType.WOODEN_SWORD);
        }

        if (hasCopperSword) {
            inventory.addWeapon(WeaponType.COPPER_SWORD);
        }

        if (!inventory.hasWeapon(weapon)) {
            throw new IllegalArgumentException("装備中の武器を所持していません。");
        }

        this.weapon = weapon;
    }
}
