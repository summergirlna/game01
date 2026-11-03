package com.example.rpg;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("勇者", 30, 8);
        Monster monster = new Monster("スライム", 20, 5);

        Battle battle = new Battle(player, monster);
        battle.start();
    }
}
