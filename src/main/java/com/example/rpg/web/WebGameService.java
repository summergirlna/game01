package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;
import org.springframework.stereotype.Service;

/// Web版ゲーム操作サービス
@Service
public class WebGameService {

    private static final int INN_COST = 10;

    private static final int HERB_PRICE = 5;

    private static final int COPPER_SWORD_PRICE = 30;

    /// 薬草を使う
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult useHerb(Player player) {
        ItemType itemType = ItemType.HERB;

        if (!player.getInventory().use(itemType)) {
            return new GameActionResult(itemType.getDisplayName() + "を持っていません。");
        }

        player.heal(itemType.getHealAmount());
        return new GameActionResult(itemType.getDisplayName() + "を使った。HPが " + itemType.getHealAmount() + " 回復した。");
    }

    /// 宿屋に泊まる
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult stayInn(Player player) {
        if (player.getCurrentArea() != FieldArea.TOWN) {
            return new GameActionResult("ここには宿屋がない。");
        }

        if (!player.payGold(INN_COST)) {
            return new GameActionResult("ゴールドが足りません。必要なゴールド: " + INN_COST);
        }

        player.healFull();
        return new GameActionResult("宿屋に泊まった。HPが全回復した。");
    }

    /// 次のエリアへ移動する
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult moveToNextArea(Player player) {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case TOWN -> FieldArea.GRASSLAND;
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE -> FieldArea.TOWN;
        };

        player.moveTo(nextArea);
        return new GameActionResult(nextArea.getDisplayName() + "へ移動した。");

    }

    /// 薬草を購入する
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult buyHerb(Player player) {
        ItemType itemType = ItemType.HERB;

        if (player.getCurrentArea() != FieldArea.TOWN) {
            return new GameActionResult("ここには道具屋がない。");
        }

        if (!player.payGold(HERB_PRICE)) {
            return new GameActionResult("ゴールドが足りません。必要なゴールド: " + HERB_PRICE);
        }

        player.getInventory().add(itemType, 1);
        return new GameActionResult(itemType.getDisplayName() + "を買った。" + HERB_PRICE + " ゴールドを支払った。");
    }

    /// 銅の剣を装備する
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult equipCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (!player.getEquipment().equipWeapon(weapon)) {
            return new GameActionResult(weapon.getDisplayName() + "を持っていません。");
        }

        return new GameActionResult(weapon.getDisplayName() + "を装備した。攻撃力が " + weapon.getAttackBonus() + " 上がる。");
    }

    /// 銅の剣を購入する
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult buyCopperSword(Player player) {
        WeaponType weapon = WeaponType.COPPER_SWORD;

        if (player.getCurrentArea() != FieldArea.TOWN) {
            return new GameActionResult("ここには武器屋がない。");
        }

        if (player.getEquipment().getInventory().hasWeapon(weapon)) {
            return new GameActionResult(weapon.getDisplayName() + "はすでに持っています。");
        }

        if (!player.payGold(COPPER_SWORD_PRICE)) {
            return new GameActionResult("ゴールドが足りません。必要なゴールド: " + COPPER_SWORD_PRICE);
        }

        player.getEquipment().addWeapon(weapon);
        return new GameActionResult(weapon.getDisplayName() + "を買った。" + COPPER_SWORD_PRICE + " ゴールドを支払った。");
    }
}
