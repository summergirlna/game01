package com.example.rpg;

import com.example.rpg.battle.Battle;
import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("勇者", 30, 8);
        Monster monster = new Monster("スライム", 20, 5, 100, 5);

        Battle battle = new Battle(player, monster);
        BattleResult result = battle.start();

        System.out.println("戦闘結果: " + result);
        System.out.println("現在の経験値: " + player.getExp());
        System.out.println("現在のゴールド: " + player.getGold());
    }
}
