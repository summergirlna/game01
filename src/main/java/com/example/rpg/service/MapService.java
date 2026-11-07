package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.command.MapCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

/// マップ移動処理を担当するクラス
@RequiredArgsConstructor
@Service
public class MapService {

    private static final int CHEST_GOLD = 20;

    private static final int CHEST_HERB_AMOUNT = 2;

    private static final int ENCOUNTER_RATE_PERCENT = 30;

    private final InnService innService;

    private final ShopService shopService;

    private final WeaponShopService weaponShopService;

    private final SaveService saveService;

    private final Random random = new Random();

    /// 移動する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @param command マップ操作コマンド
    /// @return 操作結果
    public GameActionResult move(Player player, GameMap gameMap, MapCommand command) {
        if (!gameMap.move(command)) {
            return GameActionResult.message("これ以上そちらには進めません。");
        }

        MapEvent event = gameMap.getPlayerEvent(player.getCurrentArea());

        if (event != null) {
            return handleMapEvent(player, gameMap, event);
        }

        if (canEncounter(player) && encounter()) {
            return GameActionResult.redirect("モンスターが現れた！", "/battle");
        }

        return GameActionResult.message(exploringMessage(player));
    }

    /// 探索メッセージを作成する
    ///
    /// @param player プレイヤー
    /// @return 探索メッセージ
    private String exploringMessage(Player player) {
        if (player.isInTown()) {
            return "町を歩いている。";
        }

        return player.getCurrentArea().getDisplayName() + "を探索している。";
    }

    /// 通常エンカウントできるかどうかを返す
    ///
    /// @param player プレイヤー
    /// @return 通常エンカウントできる場合はtrue
    public boolean canEncounter(Player player) {
        return !player.isInTown();
    }

    /// 通常エンカウントを判定する
    ///
    /// @return エンカウントした場合はtrue
    private boolean encounter() {
        return random.nextInt(100) < ENCOUNTER_RATE_PERCENT;
    }

    /// マップイベントを処理する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @param event   マップイベント
    /// @return 操作結果
    private GameActionResult handleMapEvent(Player player, GameMap gameMap, MapEvent event) {
        return switch (event) {
            case TOWN -> returnToTown(player, gameMap);
            case EXIT -> leaveTown(player, gameMap);
            case INN -> innService.stay(player);
            case ITEM_SHOP -> shopService.buyHerb(player);
            case WEAPON_SHOP -> weaponShopService.buyCopperSword(player);
            case SAVE_POINT -> saveService.save(player);
            case NEXT_AREA -> moveToNextArea(player, gameMap);
            case PREVIOUS_AREA -> moveToPreviousArea(player, gameMap);
            case CHEST -> openChest(player);
            case BOSS -> GameActionResult.redirect("強大な敵が立ちはだかった！", "/battle/boss");
        };
    }

    /// 町を出て草原へ移動する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @return 操作結果
    private GameActionResult leaveTown(Player player, GameMap gameMap) {
        player.moveTo(FieldArea.GRASSLAND);
        gameMap.resetPlayerPosition();
        return GameActionResult.message("町を出て草原へ向かった。");
    }

    /// 町へ戻る
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @return 操作結果
    private GameActionResult returnToTown(Player player, GameMap gameMap) {
        player.moveTo(FieldArea.TOWN);
        gameMap.resetPlayerPosition();
        return GameActionResult.message("町へ戻った。");
    }

    /// 次のエリアへ移動する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @return 操作結果
    private GameActionResult moveToNextArea(Player player, GameMap gameMap) {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE, TOWN -> player.getCurrentArea();
        };

        player.moveTo(nextArea);
        gameMap.resetPlayerPosition();

        return GameActionResult.message(nextArea.getDisplayName() + "へ移動した。");
    }

    /// 前のエリアへ移動する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @return 操作結果
    private GameActionResult moveToPreviousArea(Player player, GameMap gameMap) {
        FieldArea previousArea = switch (player.getCurrentArea()) {
            case FOREST -> FieldArea.GRASSLAND;
            case CAVE -> FieldArea.FOREST;
            case GRASSLAND, TOWN -> player.getCurrentArea();
        };

        player.moveTo(previousArea);
        gameMap.resetPlayerPosition();

        return GameActionResult.message(previousArea.getDisplayName() + "へ移動した。");
    }

    /// 宝箱を開ける
    ///
    /// @param player プレイヤー
    /// @return 操作結果
    private GameActionResult openChest(Player player) {
        if (player.hasOpenedTreasureChest()) {
            return GameActionResult.message("宝箱は空だった。");
        }

        player.openTreasureChest();
        player.addGold(CHEST_GOLD);
        player.getInventory().add(ItemType.HERB, CHEST_HERB_AMOUNT);

        return GameActionResult.message("宝箱を開けた！" + CHEST_GOLD + " ゴールドと薬草 " + CHEST_HERB_AMOUNT + " 個を手に入れた。");
    }
}
