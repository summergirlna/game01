package com.example.rpg.web;

import com.example.rpg.character.Level;
import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import com.example.rpg.item.ItemType;
import com.example.rpg.value.Gold;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Web版ゲーム操作サービス
@Service
public class WebGameService {

    private static final String SAVE_FILE_NAME = "save.txt";

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

    /// セーブする
    /// @param player プレイヤー
    /// @return 操作結果
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

    /// ロードする
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult load(Player player) {
        Path path = Path.of(SAVE_FILE_NAME);

        if (!Files.exists(path)) {
            return new GameActionResult("セーブファイルが見つかりません。");
        }

        try {
            Map<String, String> saveData = readSaveData(path);

            FieldArea currentArea = FieldArea.valueOf(saveData.get("currentArea"));
            Level level = new Level(Integer.parseInt(saveData.get("level")));
            int hp = Integer.parseInt(saveData.get("hp"));
            int maxHp = Integer.parseInt(saveData.get("maxHp"));
            int baseAttack = Integer.parseInt(saveData.get("baseAttack"));
            int exp = Integer.parseInt(saveData.get("exp"));
            Gold gold = new Gold(Integer.parseInt(saveData.get("gold")));
            int herbAmount = Integer.parseInt(saveData.get("herb"));
            WeaponType weapon = WeaponType.valueOf(saveData.get("weapon"));
            boolean hasWoodenSword = Boolean.parseBoolean(saveData.get("hasWoodenSword"));
            boolean hasCopperSword = Boolean.parseBoolean(saveData.get("hasCopperSword"));

            player.restore(
                    currentArea,
                    level,
                    hp,
                    maxHp,
                    baseAttack,
                    exp,
                    gold,
                    herbAmount,
                    weapon,
                    hasWoodenSword,
                    hasCopperSword
            );

            return new GameActionResult(SAVE_FILE_NAME + " からロードしました。");
        } catch (IOException | RuntimeException e) {
            return new GameActionResult("ロードに失敗しました。");
        }
    }

    /// セーブファイルを読み込む
    /// @param path セーブファイルのパス
    /// @return セーブデータ
    /// @throws IOException ファイルが読み込めない場合は例外
    private Map<String, String> readSaveData(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Map<String, String> saveData = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split("=", 2);

            if (parts.length == 2) {
                saveData.put(parts[0], parts[1]);
            }
        }

        return saveData;
    }
}
