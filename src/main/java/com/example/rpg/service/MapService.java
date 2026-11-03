package com.example.rpg.service;

import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Player;
import com.example.rpg.field.FieldArea;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapResult;
import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.ui.field.MapMessage;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/// マップ移動処理を担当するクラス
@RequiredArgsConstructor
public class MapService {

    private final Player player;

    private final GameMap gameMap;

    private final EncounterService encounterService;

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

        if (gameMap.isPlayerOnTownPosition()) {
            return returnToTown();
        }

        BattleResult battleResult = encounterService.encounter();

        if (battleResult == BattleResult.LOSE) {
            return MapResult.LOSE;
        }

        return null;
    }

    /// 町へ戻る
    /// @return 町へ戻った結果
    private MapResult returnToTown() {
        player.moveTo(FieldArea.TOWN);
        gameMap.resetPlayerPosition();
        message.showReturnToTown();
        return MapResult.RETURN_TO_TOWN;
    }
}
