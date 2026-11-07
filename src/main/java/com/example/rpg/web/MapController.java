package com.example.rpg.web;

import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.service.GameActionResult;
import com.example.rpg.service.MapService;
import com.example.rpg.ui.command.MapCommand;
import com.example.rpg.value.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/// Web版マップ画面コントローラ
@Controller
@RequiredArgsConstructor
public class MapController {

    private static final String VIEW_MAP = "map";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String PATH_BATTLE = "/battle";
    private static final String PATH_BOSS_BATTLE = "/battle/boss";
    private static final String ATTRIBUTE_PLAYER = "player";
    private static final String ATTRIBUTE_ROWS = "rows";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String DEFAULT_MESSAGE = "探索を開始した。";
    private static final String PLAYER_SYMBOL = "P";
    private static final String EMPTY_SYMBOL = ".";
    private static final String PLAYER_CELL_CLASS = "player";
    private static final String EMPTY_CELL_CLASS = "empty";
    private static final String EVENT_CELL_CLASS_PREFIX = "event ";

    private final WebGameSession webGameSession;

    private final MapService mapService;

    private final MonsterFactory monsterFactory;

    /// マップ画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/map")
    public String map(Model model) {
        String message = webGameSession.consumeLastMessage(DEFAULT_MESSAGE);
        addMapAttributes(model, message);
        return VIEW_MAP;
    }

    /// マップ上を移動する
    ///
    /// @param command マップ操作コマンド
    /// @param model   画面モデル
    /// @return テンプレート名
    @PostMapping("/map/move")
    public String move(@RequestParam MapCommand command, Model model) {
        Player player = webGameSession.getPlayer();
        GameMap gameMap = webGameSession.getGameMap();

        GameActionResult result = mapService.move(player, gameMap, command);

        if (result.hasRedirect()) {
            return handleRedirect(result, player);
        }

        addMapAttributes(model, result.message());

        return "map";
    }

    /// リダイレクトを伴うマップ操作結果を処理する
    /// @param result 操作結果
    /// @param player プレイヤー
    /// @return リダイレクト先
    private String handleRedirect(GameActionResult result, Player player) {
        if (PATH_BATTLE.equals(result.redirectPath())) {
            webGameSession.startBattle(monsterFactory.createRandomMonster(player.getCurrentArea()), false);
            webGameSession.setLastMessage(result.message());
            return REDIRECT_PREFIX + PATH_BATTLE;
        }

        if (PATH_BOSS_BATTLE.equals(result.redirectPath())) {
            webGameSession.startBattle(monsterFactory.createBoss(), true);
            webGameSession.setLastMessage(result.message());
            return REDIRECT_PREFIX + PATH_BATTLE;
        }

        webGameSession.setLastMessage(result.message());
        return REDIRECT_PREFIX + result.redirectPath();
    }

    /// マップ画面用属性を追加する
    ///
    /// @param model   画面モデル
    /// @param message メッセージ
    private void addMapAttributes(Model model, String message) {
        Player player = webGameSession.getPlayer();
        GameMap gameMap = webGameSession.getGameMap();

        model.addAttribute(ATTRIBUTE_PLAYER, player);
        model.addAttribute(ATTRIBUTE_ROWS, buildRows(player, gameMap));
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
    }

    /// マップ表示行を作成する
    ///
    /// @param player  プレイヤー
    /// @param gameMap マップ
    /// @return マップ表示行
    private List<List<MapCell>> buildRows(Player player, GameMap gameMap) {
        List<List<MapCell>> rows = new ArrayList<>();

        for (int y = 0; y < gameMap.getHeight(); y++) {
            List<MapCell> row = new ArrayList<>();

            for (int x = 0; x < gameMap.getWidth(); x++) {
                Position position = new Position(x, y);
                row.add(buildCell(player, gameMap, position));
            }

            rows.add(row);
        }

        return rows;
    }

    /// マップ表示セルを作成する
    ///
    /// @param player   プレイヤー
    /// @param gameMap  マップ
    /// @param position 座標
    /// @return マップ表示セル
    private MapCell buildCell(Player player, GameMap gameMap, Position position) {
        if (gameMap.isPlayerPosition(position)) {
            return new MapCell(position.getX(), position.getY(), PLAYER_SYMBOL, PLAYER_CELL_CLASS);
        }

        MapEvent event = gameMap.getEventAt(player.getCurrentArea(), position);

        if (event != null) {
            return new MapCell(
                    position.getX(),
                    position.getY(),
                    String.valueOf(event.getSymbol()),
                    EVENT_CELL_CLASS_PREFIX + event.name().toLowerCase());
        }

        return new MapCell(position.getX(), position.getY(), EMPTY_SYMBOL, EMPTY_CELL_CLASS);
    }
}
