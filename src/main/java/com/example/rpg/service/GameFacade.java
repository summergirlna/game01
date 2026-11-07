package com.example.rpg.service;

import com.example.rpg.character.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/// ファサードクラス
@Service
@RequiredArgsConstructor
public class GameFacade {

    private final ItemService itemService;

    private final ShopService shopService;

    private final EquipmentService equipmentService;

    private final WeaponShopService weaponShopService;

    private final SaveService saveService;

    private final LoadService loadService;

    private final FieldService fieldService;

    public GameActionResult useHerb(Player player) {
        return itemService.useHerb(player);
    }

    public GameActionResult buyHerb(Player player) {
        return shopService.buyHerb(player);
    }

    public GameActionResult equipCopperSword(Player player) {
        return equipmentService.equipCopperSword(player);
    }

    public GameActionResult buyCopperSword(Player player) {
        return weaponShopService.buyCopperSword(player);
    }

    public GameActionResult save(Player player) {
        return saveService.save(player);
    }

    public GameActionResult load(Player player) {
        return loadService.load(player);
    }

    public GameActionResult moveToNextArea(Player player) {
        return fieldService.moveToNextArea(player);
    }
}
