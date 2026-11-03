package com.example.rpg.game;

import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;

import java.util.Random;
import java.util.Scanner;

/// ゲーム全体の進行を管理するクラス
public class Game {
    private final Player player;

    private final GameMessage message;

    private final ExploreService exploreService;

    private final MonsterFactory monsterFactory;

    private final Random random;

    private final Scanner scanner;

    private boolean running;

    public Game() {
        this.player = new Player("勇者", 30, 8);
        this.monsterFactory = new MonsterFactory();
        this.message = new GameMessage();
        this.exploreService = new ExploreService(player, monsterFactory);
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /// ゲームを開始する
    public void start() {
        message.showStart();

        while (running && player.isAlive()) {
            message.showMenu();

            String input = scanner.nextLine();
            GameCommand command = GameCommand.fromInput(input);

            switch (command) {
                case EXPLORE -> explore();
                case STATUS -> message.showStatus(player);
                case QUIT -> quit();
                case INVALID -> message.showInvalidCommand();
            }
        }

        message.showEnd();
    }

    /// 探索する
    private void explore() {
        BattleResult result = exploreService.explore();

        if (result == BattleResult.LOSE) {
            running = false;
        }
    }

    /// ゲームを終了する
    private void quit() {
        running = false;
    }
}
