package com.example.rpg.service;

import com.example.rpg.character.Player;
import com.example.rpg.field.*;
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
    /// @param player   プレイヤー
    /// @param mapMode  マップ操作モード
    /// @param worldMap 世界マップ
    /// @param gameMap  個別マップ
    /// @param command  マップ操作コマンド
    /// @return 操作結果
    public GameActionResult move(
            Player player,
            MapMode mapMode,
            WorldMap worldMap,
            GameMap gameMap,
            MapCommand command
    ) {
        return switch (mapMode) {
            case WORLD -> moveOnWorldMap(player, worldMap, gameMap, command);
            case FIELD -> moveOnFieldMap(player, gameMap, command);
        };
    }

    /// 世界マップ上を移動する
    ///
    /// @param player   プレイヤー
    /// @param worldMap 世界マップ
    /// @param gameMap  個別マップ
    /// @param command  マップ操作コマンド
    /// @return 操作結果
    private GameActionResult moveOnWorldMap(
            Player player,
            WorldMap worldMap,
            GameMap gameMap,
            MapCommand command
    ) {
        if (command == MapCommand.ACTION) {
            return enterCurrentWorldArea(player, worldMap, gameMap);
        }

        if (!worldMap.move(command)) {
            return GameActionResult.message("これ以上そちらには進めません。");
        }

        WorldMap.WorldMapArea area = worldMap.getPlayerArea();

        if (area != null) {
            return GameActionResult.message(area.fieldArea().getDisplayName() + "が見える。Enterで入る。");
        }

        return GameActionResult.message("世界を移動している。");
    }

    private GameActionResult enterCurrentWorldArea(Player player, WorldMap worldMap, GameMap gameMap) {
        WorldMap.WorldMapArea area = worldMap.getPlayerArea();

        if (area == null) {
            return GameActionResult.message("ここには入れる場所がない。");
        }

        player.moveTo(area.fieldArea());
        gameMap.resetPlayerPosition();

        return GameActionResult.message(area.fieldArea().getDisplayName() + "へ入った。");
    }

    /// 移動する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @param command マップ操作コマンド
    /// @return 操作結果
    public GameActionResult moveOnFieldMap(Player player, GameMap gameMap, MapCommand command) {
        if (command == MapCommand.ACTION) {
            return executeCurrentEvent(player, gameMap);
        }

        if (!gameMap.move(command)) {
            return GameActionResult.message("これ以上そちらには進めません。");
        }

        MapEvent event = gameMap.getPlayerEvent(player.getCurrentArea());

        if (event != null) {
            return GameActionResult.message(eventMessage(event));
        }

        if (canEncounter(player) && encounter()) {
            return GameActionResult.redirect("モンスターが現れた！", "/battle");
        }

        return GameActionResult.message(exploringMessage(player));
    }

    /// 現在地のマップイベントを実行する
    /// @param player プレイヤー
    /// @param gameMap マップ
    /// @return 操作結果
    private GameActionResult executeCurrentEvent(Player player, GameMap gameMap) {
        MapEvent event = gameMap.getPlayerEvent(player.getCurrentArea());

        if (event == null) {
            return GameActionResult.message("ここには何もない。");
        }

        return handleMapEvent(player, gameMap, event);
    }

    /// マップイベント案内メッセージを作成する
    ///
    /// @param event マップイベント
    /// @return 案内メッセージ
    private String eventMessage(MapEvent event) {
        return switch (event) {
            case TOWN -> "町が見える。Enterで町へ入る。";
            case EXIT -> "町の出口だ。Enterで外へ出る。";
            case INN -> "宿屋の前にいる。Enterで泊まる。";
            case ITEM_SHOP -> "道具屋の前にいる。Enterで薬草を買う。";
            case WEAPON_SHOP -> "武器屋の前にいる。Enterで銅の剣を買う。";
            case SAVE_POINT -> "セーブポイントだ。Enterで記録する。";
            case NEXT_AREA -> "次のエリアへ続く道だ。Enterで進む。";
            case PREVIOUS_AREA -> "前のエリアへ戻る道だ。Enterで戻る。";
            case CHEST -> "宝箱がある。Enterで開ける。";
            case BOSS -> "強大な敵の気配がする。Enterで挑む。";
        };
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
