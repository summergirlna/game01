package com.example.rpg;

import java.util.Scanner;

/// 戦闘処理を担当するクラス
public class Battle {
    private final Player player;

    private final Monster monster;

    private final BattleMessage message;

    private final Scanner scanner;

    private boolean escaped;

    public Battle(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.message = new BattleMessage();
        this.scanner = new Scanner(System.in);
        this.escaped = false;
    }

    /// 戦闘を開始する
    public BattleResult start() {
        message.showStart();
        message.showAppear(monster);
        message.showHp(player, monster);


        while (player.isAlive() && monster.isAlive() && !escaped) {
            executePlayerTurn();

            if (!monster.isAlive()) {
                break;
            }

            executeMonsterTurn();

            message.showHp(player, monster);
        }

        return showResult();
    }

    /// プレイヤーのターンを実行する
    private void executePlayerTurn() {
        while (true) {
            message.showCommandMenu();

            String input = scanner.nextLine();
            BattleCommand command = BattleCommand.fromInput(input);

            switch (command) {
                case ATTACK -> {
                    attack(player, monster);
                    return;
                }
                case HEAL -> {
                    heal(player);
                    return;
                }
                case ESCAPE -> {
                    escape();
                    return;
                }
                case INVALID -> message.showInvalidCommand();
            }
        }
    }

    /// モンスターのターンを実行する
    private void executeMonsterTurn() {
        attack(monster, player);
    }

    /// 攻撃処理
    /// @param attacker 攻撃者
    /// @param target 攻撃対象
    private void attack(GameCharacter attacker, GameCharacter target) {
        int damage = attacker.getAttack();

        target.takeDamage(damage);
        message.showAttack(attacker, target, damage);
    }

    /// 回復処理
    /// @param character 回復するキャラクター
    private void heal(GameCharacter character) {
        int healAmount = 10;

        character.heal(healAmount);
        message.showHeal(character, healAmount);
    }

    /// 逃走処理
    private void escape() {
        escaped = true;
        message.showEscape(player);
    }

    /// 戦闘結果表示
    private BattleResult showResult() {
        if (escaped) {
            return BattleResult.ESCAPE;
        }

        if (player.isAlive()) {
            message.showWin(monster);
            return BattleResult.WIN;
        } else {
            message.showLose(player);
            return BattleResult.LOSE;
        }
    }
}
