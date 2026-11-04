package com.example.rpg.service;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.field.MapResult;
import com.example.rpg.item.ItemType;
import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.ui.field.MapMessage;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/// マップ移動処理を担当するクラス
@RequiredArgsConstructor
public class MapService {

    private static final int CHEST_GOLD = 20;

    private static final int CHEST_HERB_AMOUNT = 2;

    private final Player player;

    private final GameMap gameMap;

    private final EncounterService encounterService;

    private final MonsterFactory monsterFactory;

    private final MapMessage message;

    private final Scanner scanner = new Scanner(System.in);

    /// マップ移動を開始する
    public MapResult start() {
        while (true) {
            message.showMap(player.getCurrentArea(), gameMap);
            message.showCommandMenu();

            String input = scanner.nextLine();
            MapCommand command = MapCommand.fromInput(input);

            switch (command) {
                case UP, DOWN, LEFT, RIGHT -> {
                    MapResult result = move(command);

                    if (result != null) {
                        return result;
                    }
                }
                case MENU -> {
                    message.showBackToMenu();
                    return null;
                }
                case INVALID -> message.showInvalidCommand();
            }

        }
    }

    /// 移動する
    /// @param command マップ操作コマンド
    /// @return マップ探索結果。探索を継続する場合はnull
    private MapResult move(MapCommand command) {
        if (!gameMap.move(command)) {
            message.showCannotMove();
            return null;
        }

        MapResult eventResult = handleMapEvent();

        if (eventResult != null) {
            return eventResult;
        }

        BattleResult battleResult = encounterService.encounter();

        if (battleResult == BattleResult.LOSE) {
            return MapResult.LOSE;
        }

        return null;
    }

    /// マップイベントを処理する
    /// @return マップ探索結果。探索を継続する場合はnull
    private MapResult handleMapEvent() {
        MapEvent event = gameMap.getPlayerEvent(player.getCurrentArea());

        if (event == null) {
            return null;
        }

        return switch (event) {
            case TOWN -> returnToTown();
            case NEXT_AREA -> {
                moveToNextArea();
                yield null;
            }
            case PREVIOUS_AREA -> {
                moveToPreviousArea();
                yield null;
            }
            case CHEST -> {
                openChest();
                yield null;
            }
            case BOSS -> fightBoss();
        };
    }

    /// 町へ戻る
    /// @return 町へ戻った結果
    private MapResult returnToTown() {
        player.moveTo(FieldArea.TOWN);
        gameMap.resetPlayerPosition();
        message.showReturnToTown();
        return MapResult.RETURN_TO_TOWN;
    }

    /// 宝箱を開ける
    private void openChest() {
        if (player.hasOpenedTreasureChest()) {
            message.showOpenedChest();
            return;
        }

        player.openTreasureChest();
        player.addGold(CHEST_GOLD);
        player.getInventory().add(ItemType.HERB, CHEST_HERB_AMOUNT);
        message.showOpenChest(CHEST_GOLD, CHEST_HERB_AMOUNT);
    }

    /// 次のエリアへ移動する
    private void moveToNextArea() {
        FieldArea nextArea = switch (player.getCurrentArea()) {
            case GRASSLAND -> FieldArea.FOREST;
            case FOREST -> FieldArea.CAVE;
            case CAVE, TOWN -> player.getCurrentArea();
        };

        player.moveTo(nextArea);
        gameMap.resetPlayerPosition();
        message.showMoveArea(nextArea);
    }

    /// 前のエリアへ移動する
    private void moveToPreviousArea() {
        FieldArea previousArea = switch (player.getCurrentArea()) {
            case FOREST -> FieldArea.GRASSLAND;
            case CAVE -> FieldArea.FOREST;
            case GRASSLAND, TOWN -> player.getCurrentArea();
        };

        player.moveTo(previousArea);
        gameMap.resetPlayerPosition();
        message.showMoveArea(previousArea);
    }

    /// ボス戦を行う
    /// @return ボス戦結果
    private MapResult fightBoss() {
        message.showBossEncounter();

        Monster boss = monsterFactory.createBoss();
        Battle battle = new Battle(player, boss);
        BattleResult result = battle.start();

        return switch (result) {
            case WIN -> {
                message.showGameClear();
                yield MapResult.CLEAR;
            }
            case LOSE -> MapResult.LOSE;
            case ESCAPE -> null;
        };
    }
}
