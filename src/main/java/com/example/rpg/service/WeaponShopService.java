package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import com.example.rpg.ui.WeaponShopMessage;
import lombok.RequiredArgsConstructor;

/// 武器屋の処理を担当するクラス
@RequiredArgsConstructor
public class WeaponShopService {

    private static final int COPPER_SWORD_PRICE = 30;

    private final WeaponShopMessage message;

    /// 銅の剣を購入する
    /// @param player プレイヤー
    public void buyCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (player.getCurrentArea() != FieldArea.TOWN) {
            message.showNoWeaponShop();
            return;
        }

        if (player.getEquipment().getInventory().hasWeapon(weapon)) {
            message.showAlreadyHasWeapon(weapon);
            return;
        }

        if (!player.payGold(COPPER_SWORD_PRICE)) {
            message.showNotEnoughGold(COPPER_SWORD_PRICE);
            return;
        }

        player.getEquipment().addWeapon(weapon);
        message.showBuyWeapon(weapon, COPPER_SWORD_PRICE);
    }
}
