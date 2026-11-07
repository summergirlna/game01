package com.example.rpg.web;

import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/// Web版ゲーム状態
@Component
@Getter
@Setter
public class WebGameSession {

    private final Player player = new Player("勇者", 30 ,8);

    private final GameMap gameMap = GameMap.initial();

    private Monster currentMonster;

    /// 戦闘中か判定する
    /// @return 戦闘中の場合はtrue
    public boolean isInBattle() {
        return currentMonster != null && currentMonster.isAlive();
    }
}
