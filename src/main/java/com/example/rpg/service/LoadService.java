package com.example.rpg.service;

import com.example.rpg.character.Level;
import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.field.FieldArea;
import com.example.rpg.value.Gold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// ロード処理を担当するクラス
@RequiredArgsConstructor
@Service
public class LoadService {
    private static final String SAVE_FILE_NAME = "save.txt";

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
