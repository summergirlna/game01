package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import lombok.Getter;
import org.springframework.stereotype.Component;

/// Web版ゲーム状態
@Component
@Getter
public class WebGameSession {

    private final Player player = new Player("勇者", 30 ,8);

    private final GameMap gameMap = GameMap.initial();

}
