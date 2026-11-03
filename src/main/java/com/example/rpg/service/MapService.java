package com.example.rpg.service;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import com.example.rpg.ui.MapCommand;
import com.example.rpg.ui.MapMessage;
import lombok.RequiredArgsConstructor;

import java.util.Random;
import java.util.Scanner;

/// マップ移動処理を担当するクラス
@RequiredArgsConstructor
public class MapService {
    private static final int ENCOUNTER_RATE = 30;

    private final Player player;

    private final GameMap gameMap;

    private final MonsterFactory monsterFactory;

    private final MapMessage message;

    private final Scanner scanner = new Scanner(System.in);

    private final Random random = new Random();

    /// マップ移動を開始する
    public BattleResult start() {
        while (true) {
            message.showMap(player.getCurrentArea(), gameMap);
            message.showCommandMenu();

            String input = scanner.nextLine();
            MapCommand command = MapCommand.fromInput(input);

            switch (command) {
                case UP, DOWN, LEFT, RIGHT -> {
                    BattleResult result = move(command);

                    if (result == BattleResult.LOSE) {
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
    /// @return 移動後に発生した戦闘結果。戦闘が発生しなかった場合はnull
    private BattleResult move(MapCommand command) {
        if (!gameMap.move(command)) {
            message.showCannotMove();
            return null;
        }

        if (!encounterMonster()) {
            return null;
        }

        Monster monster = monsterFactory.createRandomMonster(player.getCurrentArea());
        Battle battle = new Battle(player, monster);
        return battle.start();
    }

    /// モンスターに遭遇するかどうかを判定する
    /// @return モンスターと遭遇した場合はtrue、そうでない場合はfalse
    private boolean encounterMonster() {
        return random.nextInt(100) < ENCOUNTER_RATE;
    }
}
