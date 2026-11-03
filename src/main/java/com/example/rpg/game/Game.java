package com.example.rpg.game;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;

import java.util.Random;
import java.util.Scanner;

/// ゲーム全体の進行を管理するクラス
public class Game {
    private final Player player;

    private final MonsterFactory monsterFactory;

    private final Random random;

    private final Scanner scanner;

    private boolean running;

    public Game() {
        this.player = new Player("勇者", 30, 8);
        this.monsterFactory = new MonsterFactory();
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /// ゲームを開始する
    public void start() {
        System.out.println("=== ゲーム開始 ===");

        while (running && player.isAlive()) {
            showMenu();

            String input = scanner.nextLine();
            GameCommand command = GameCommand.fromInput(input);

            switch (command) {
                case EXPLORE -> explore();
                case STATUS -> showStatus();
                case QUIT -> quit();
                case INVALID -> System.out.println("正しいコマンドを入力してください。");
            }
        }

        System.out.println("ゲームを終了します。");
    }

    /// メニューを表示する
    private void showMenu() {
        System.out.println();
        System.out.println("何をしますか？");
        System.out.println("1. 探索する");
        System.out.println("2. ステータスを見る");
        System.out.println("3. 終了する");
        System.out.print("> ");
    }

    /// 探索する
    private void explore() {
        System.out.println("周囲を探索した。");

        if (!encounterMonster()) {
            System.out.println("特に何も起きなかった。");
            return;
        }

        Monster monster = monsterFactory.createRandomMonster();

        Battle battle = new Battle(player, monster);
        BattleResult result = battle.start();

        System.out.println("戦闘結果: " + result);

        if (result == BattleResult.LOSE) {
            running = false;
        }
    }

    /// モンスターに遭遇するかどうかを判定する
    /// @return モンスターと遭遇した場合はtrue、そうでない場合はfalse
    private boolean encounterMonster() {
        int encounterRate = 60;
        return random.nextInt(100) < encounterRate;
    }

    /// ステータスを表示する
    private void showStatus() {
        System.out.println("=== ステータス ===");
        System.out.println("名前: " + player.getName());
        System.out.println("レベル: " + player.getLevel().getValue());
        System.out.println("HP: " + player.getHp() + " / " + player.getMaxHp());
        System.out.println("攻撃力: " + player.getAttack());
        System.out.println("経験値: " + player.getExp());
        System.out.println("ゴールド: " + player.getGold());
    }

    /// ゲームを終了する
    private void quit() {
        running = false;
    }
}
