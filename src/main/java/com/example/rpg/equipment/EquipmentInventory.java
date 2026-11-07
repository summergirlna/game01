package com.example.rpg.equipment;

import java.util.EnumSet;
import java.util.Set;

/// 所持装備を管理するクラス
public class EquipmentInventory {
    private final Set<WeaponType> weapons = EnumSet.noneOf(WeaponType.class);

    /// 武器を追加する
    ///
    /// @param weapon 武器
    public void addWeapon(WeaponType weapon) {
        weapons.add(weapon);
    }

    /// 武器を所持しているか判定する
    ///
    /// @param weapon 武器
    /// @return 所持している場合はtrue、そうでない場合はfalse
    public boolean hasWeapon(WeaponType weapon) {
        return weapons.contains(weapon);
    }

    /// 所持武器を全て削除する
    public void clearWeapons() {
        weapons.clear();
    }
}
