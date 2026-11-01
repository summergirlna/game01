package com.example.rpg;

public class Main {
    public static void main() {
        Player player = new Player("勇者", 30, 8);
        Monster monster = new Monster("スライム", 20, 5);

        System.out.println("=== RPGを開始します! ===");
        System.out.println(monster.getName() + "が現れた!");

        while (player.isAlive() && monster.isAlive()) {
            // プレイヤーは先制攻撃できる
            // プレイヤーの攻撃力分ダメージを受ける
            monster.takeDamage(player.getAttack());
            System.out.println(player.getName() + "の攻撃!");
            System.out.println(monster.getName() + "に" + player.getAttack() + "ダメージ!");

            if (!monster.isAlive()) {
                break;
            }

            player.takeDamage(monster.getAttack());
            System.out.println(monster.getName() + "の攻撃!");
            System.out.println(player.getName() + "に" + monster.getAttack() + "ダメージ!");

            System.out.println(player.getName() + " HP: " + player.getHp());
            System.out.println(monster.getName() + " HP: " + monster.getHp());
            System.out.println();
        }

        if (player.isAlive()) {
            System.out.println(monster.getName() + "を倒した!");
            System.out.println("勝利!");
        } else {
            System.out.println(player.getName() + "は倒れてしまった...");
            System.out.println("ゲームオーバー");
        }

    }
}
