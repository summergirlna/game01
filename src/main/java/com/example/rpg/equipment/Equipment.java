package com.example.rpg.equipment;

import lombok.Getter;

/// 装備を管理するクラス
@Getter
public class Equipment {
    private WeaponType weapon;

    public Equipment() {
        this.weapon = WeaponType.WOODEN_SWORD;
    }

    /// 武器を装備する
    /// @param weapon 装備する武器
    public void equipWeapon(WeaponType weapon) {
        this.weapon = weapon;
    }

    /// 攻撃力補正を取得する
    /// @return 攻撃力補正
    public int getAttackBonus() {
        return weapon.getAttackBonus();
    }
}
