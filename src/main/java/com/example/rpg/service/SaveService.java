package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.save.SaveMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/// セーブ処理を担当するクラス
@RequiredArgsConstructor
@Service
public class SaveService {
    private static final String SAVE_FILE_NAME = "save.txt";

    /// セーブする
    /// @param player プレイヤー
    public GameActionResult save(Player player) {
        List<String> lines = List.of(
                "name=" + player.getName(),
                "currentArea=" + player.getCurrentArea().name(),
                "level=" + player.getLevel().getValue(),
                "hp=" + player.getHp(),
                "maxHp=" + player.getMaxHp(),
                "baseAttack=" + player.getBaseAttack(),
                "exp=" + player.getExp(),
                "gold=" + player.getGold(),
                "herb=" + player.getInventory().getAmount(ItemType.HERB),
                "weapon=" + player.getEquipment().getWeapon().name(),
                "hasWoodenSword=" + player.getEquipment().getInventory().hasWeapon(WeaponType.WOODEN_SWORD),
                "hasCopperSword=" + player.getEquipment().getInventory().hasWeapon(WeaponType.COPPER_SWORD)
        );

        try {
            Files.write(Path.of(SAVE_FILE_NAME), lines);
            return new GameActionResult(SAVE_FILE_NAME + " にセーブしました。");
        } catch (IOException e) {
            return new GameActionResult("セーブに失敗しました。");
        }
    }
}
