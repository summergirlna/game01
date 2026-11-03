package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.ui.EquipmentMessage;
import lombok.RequiredArgsConstructor;

/// 装備処理を担当するクラス
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentMessage message;

    /// 銅の剣を装備する
    /// @param player プレイヤー
    public void equipCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (!player.getEquipment().equipWeapon(weapon)) {
            message.showNoWeapon(weapon);
            return;
        }

        message.showEquipWeapon(weapon);
    }
}
