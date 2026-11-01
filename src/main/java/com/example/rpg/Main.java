package com.example.rpg;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("勇者", 30, 8);
        Monster monster = new Monster("スライム", 20, 5);
        BattleMessage message = new BattleMessage();

        message.showStart();
        message.showAppear(monster);

        while (player.isAlive() && monster.isAlive()) {
            // プレイヤーは先制攻撃できる
            // プレイヤーの攻撃力分ダメージを受ける
            int playerDamage = player.getAttack();

            monster.takeDamage(playerDamage);
            message.showAttack(player, monster, playerDamage);

            if (!monster.isAlive()) {
                break;
            }

            int monsterDamage = monster.getAttack();

            player.takeDamage(monsterDamage);
            message.showAttack(monster, player, monsterDamage);

            message.showHp(player, monster);
        }

        if (player.isAlive()) {
            message.showWin(monster);
        } else {
            message.showLose(player);
        }

    }
}
