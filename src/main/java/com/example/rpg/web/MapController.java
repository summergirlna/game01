package com.example.rpg.web;

import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.GridMap;
import com.example.rpg.field.MapEvent;
import com.example.rpg.field.WorldMap;
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
import java.util.function.Function;

/// Web版マップ画面コントローラ
@Controller
@RequiredArgsConstructor
public class MapController {

    private static final String VIEW_MAP = "map";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String PATH_BATTLE = "/battle";
    private static final String PATH_BOSS_BATTLE = "/battle/boss";
    private static final String ATTRIBUTE_PLAYER = "player";
    private static final String ATTRIBUTE_WORLD_ROWS = "worldRows";
    private static final String ATTRIBUTE_ROWS = "rows";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String DEFAULT_MESSAGE = "探索を開始した。";
    private static final String PLAYER_SYMBOL = "P";
    private static final String EMPTY_SYMBOL = ".";
    private static final String PLAYER_CELL_CLASS = "player";
    private static final String EMPTY_CELL_CLASS = "empty";
    private static final String EVENT_CELL_CLASS_PREFIX = "event ";
    private static final String WORLD_AREA_CELL_CLASS = "event world-area";

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
    ///
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
        WorldMap worldMap = webGameSession.getWorldMap();
        GameMap gameMap = webGameSession.getGameMap();

        model.addAttribute(ATTRIBUTE_PLAYER, player);
        model.addAttribute(ATTRIBUTE_WORLD_ROWS, buildMapRows(worldMap, position -> buildWorldCell(player, worldMap, position)));
        model.addAttribute(ATTRIBUTE_ROWS, buildMapRows(gameMap, position -> buildCell(player, gameMap, position)));
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
    }

    /// マップ表示行を作成する
    /// @param gridMap グリッドマップ
    /// @param cellBuilder セル作成処理
    /// @return マップ表示行
    private List<List<MapCell>> buildMapRows(GridMap gridMap, Function<Position, MapCell> cellBuilder) {
        List<List<MapCell>> rows = new ArrayList<>();

        for (int y = 0; y < gridMap.getHeight(); y++) {
            List<MapCell> row = new ArrayList<>();

            for (int x = 0; x < gridMap.getWidth(); x++) {
                row.add(cellBuilder.apply(new Position(x, y)));
            }

            rows.add(row);
        }

        return rows;
    }

    /// プレイヤー表示セルを作成する
    ///
    /// @param position 座標
    /// @return プレイヤー表示セル
    private MapCell playerCell(Position position) {
        return new MapCell(position.getX(), position.getY(), PLAYER_SYMBOL, PLAYER_CELL_CLASS);
    }

    /// 空白セルを作成する
    ///
    /// @param position 座標
    /// @return 空白セル
    private MapCell emptyCell(Position position) {
        return new MapCell(position.getX(), position.getY(), EMPTY_SYMBOL, EMPTY_CELL_CLASS);
    }

    /// 世界マップ表示セルを作成する
    ///
    /// @param player    プレイヤー
    /// @param worldMap  世界マップ
    /// @param position  座標
    /// @return 世界マップ表示セル
    private MapCell buildWorldCell(Player player, WorldMap worldMap, Position position) {
        if (worldMap.isPlayerPosition(player.getCurrentArea(), position)) {
            return playerCell(position);
        }

        WorldMap.WorldMapArea area = worldMap.getAreaAt(position);

        if (area != null) {
            return new MapCell(
                    position.getX(),
                    position.getY(),
                    String.valueOf(area.symbol()),
                    WORLD_AREA_CELL_CLASS);
        }

        return emptyCell(position);
    }

    /// マップ表示セルを作成する
    ///
    /// @param player   プレイヤー
    /// @param gameMap  マップ
    /// @param position 座標
    /// @return マップ表示セル
    private MapCell buildCell(Player player, GameMap gameMap, Position position) {
        if (gameMap.isPlayerPosition(position)) {
            return playerCell(position);
        }

        MapEvent event = gameMap.getEventAt(player.getCurrentArea(), position);

        if (event != null) {
            return new MapCell(
                    position.getX(),
                    position.getY(),
                    String.valueOf(event.getSymbol()),
                    EVENT_CELL_CLASS_PREFIX + event.name().toLowerCase());
        }

        return emptyCell(position);
    }
}
