package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// 武器屋の処理を担当するクラス
@RequiredArgsConstructor
@Service
public class WeaponShopService {

    private static final int COPPER_SWORD_PRICE = 30;

    /// 銅の剣を購入する
    ///
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult buyCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (player.getCurrentArea() != FieldArea.TOWN) {
            return GameActionResult.message("ここには武器屋がない。");
        }

        if (player.getEquipment().getInventory().hasWeapon(weapon)) {
            return GameActionResult.message(weapon.getDisplayName() + "はすでに持っています。");
        }

        if (!player.payGold(COPPER_SWORD_PRICE)) {
            return GameActionResult.message("ゴールドが足りません。必要なゴールド: " + COPPER_SWORD_PRICE);
        }

        player.getEquipment().addWeapon(weapon);
        return GameActionResult.message(weapon.getDisplayName() + "を買った。" + COPPER_SWORD_PRICE + " ゴールドを支払った。");
    }
}
