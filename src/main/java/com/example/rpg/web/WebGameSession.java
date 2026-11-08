package com.example.rpg.web;

import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.WorldMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/// Web版ゲーム状態
@Component
@Getter
@Setter
public class WebGameSession {

    private Player player = new Player("勇者", 30, 8);

    private WorldMap worldMap = WorldMap.initial();

    private GameMap gameMap = GameMap.initial();

    private Monster currentMonster;

    private boolean bossBattle;

    private String lastMessage;

    /// 初期プレイヤーを作成する
    ///
    /// @return 初期プレイヤー
    private static Player createInitialPlayer() {
        return new Player("勇者", 30, 8);
    }

    /// ニューゲームを開始する
    public void startNewGame() {
        player = createInitialPlayer();
        worldMap = WorldMap.initial();
        gameMap = GameMap.initial();
        currentMonster = null;
        bossBattle = false;
        lastMessage = "冒険を開始した。";
    }

    /// 戦闘を開始する
    ///
    /// @param monster    モンスター
    /// @param bossBattle ボス戦の場合はtrue
    public void startBattle(Monster monster, boolean bossBattle) {
        this.currentMonster = monster;
        this.bossBattle = bossBattle;
    }

    /// 戦闘中か判定する
    ///
    /// @return 戦闘中の場合はtrue
    public boolean isInBattle() {
        return currentMonster != null && currentMonster.isAlive();
    }

    /// 戦闘状態をクリアする
    public void clearBattle() {
        currentMonster = null;
    }

    /// 最後のメッセージを取得してクリアする
    ///
    /// @param defaultMessage デフォルトメッセージ
    /// @return 最後のメッセージ
    public String consumeLastMessage(String defaultMessage) {
        if (lastMessage == null || lastMessage.isBlank()) {
            return defaultMessage;
        }

        String message = lastMessage;
        lastMessage = null;
        return message;
    }
}
