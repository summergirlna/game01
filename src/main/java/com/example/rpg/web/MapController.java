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

    private final WebGameSession webGameSession;

    private final MapService mapService;

    private final MonsterFactory monsterFactory;

    /// マップ画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/map")
    public String map(Model model) {
        String message = webGameSession.consumeLastMessage("探索を開始した。");
        addMapAttributes(model, message);
        return "map";
    }

    /// マップ上を移動する
    /// @param command マップ操作コマンド
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/map/move")
    public String move(@RequestParam MapCommand command, Model model) {
        Player player = webGameSession.getPlayer();
        GameMap gameMap = webGameSession.getGameMap();

        GameActionResult result = mapService.move(player, gameMap, command);

        if (result.hasRedirect()) {
            if ("/battle".equals(result.redirectPath())) {
                webGameSession.setCurrentMonster(monsterFactory.createRandomMonster(player.getCurrentArea()));
            }

            return "redirect:" + result.redirectPath();
        }

        addMapAttributes(model, result.message());

        return "map";
    }

    /// マップ画面用属性を追加する
    /// @param model 画面モデル
    /// @param message メッセージ
    private void addMapAttributes(Model model, String message) {
        Player player = webGameSession.getPlayer();
        GameMap gameMap = webGameSession.getGameMap();

        model.addAttribute("player", player);
        model.addAttribute("rows", buildRows(player, gameMap));
        model.addAttribute("message", message);
    }

    /// マップ表示行を作成する
    /// @param player プレイヤー
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
    /// @param player プレイヤー
    /// @param gameMap マップ
    /// @param position 座標
    /// @return マップ表示セル
    private MapCell buildCell(Player player, GameMap gameMap, Position position) {
        if (gameMap.isPlayerPosition(position)) {
            return new MapCell(position.getX(), position.getY(), "P", "player");
        }

        MapEvent event = gameMap.getEventAt(player.getCurrentArea(), position);

        if (event != null) {
            return new MapCell(
                    position.getX(),
                    position.getY(),
                    String.valueOf(event.getSymbol()),
                    "event " + event.name().toLowerCase());
        }

        return new MapCell(position.getX(), position.getY(), ".", "empty");
    }
}
