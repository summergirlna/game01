package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// 装備処理を担当するクラス
@RequiredArgsConstructor
@Service
public class EquipmentService {

    /// 銅の剣を装備する
    /// @param player プレイヤー
    public GameActionResult equipCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (!player.getEquipment().equipWeapon(weapon)) {
            return GameActionResult.message(weapon.getDisplayName() + "を持っていません。");
        }

        return GameActionResult.message(weapon.getDisplayName() + "を装備した。攻撃力が " + weapon.getAttackBonus() + " 上がる。");
    }
}
