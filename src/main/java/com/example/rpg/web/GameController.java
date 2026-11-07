package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.service.GameActionResult;
import com.example.rpg.service.GameFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.Function;

/// Web版ゲーム画面コントローラ
@Controller
@RequiredArgsConstructor
public class GameController {

    private static final String VIEW_GAME = "game";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String PATH_MAP = "/map";
    private static final String ATTRIBUTE_PLAYER = "player";
    private static final String ATTRIBUTE_IS_TOWN = "isTown";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String DEFAULT_MESSAGE = "コマンドを選んでください。";

    private final WebGameSession webGameSession;

    private final GameFacade gameFacade;

    /// ニューゲームを開始する
    /// @return リダイレクト先
    @PostMapping("/game/new")
    public String newGame() {
        webGameSession.startNewGame();

        return REDIRECT_PREFIX + PATH_MAP;
    }

    /// ゲームメニュー画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/game")
    public String game(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute(ATTRIBUTE_PLAYER, player);
        model.addAttribute(ATTRIBUTE_IS_TOWN, player.isInTown());

        if (!model.containsAttribute(ATTRIBUTE_MESSAGE)) {
            model.addAttribute(ATTRIBUTE_MESSAGE, webGameSession.consumeLastMessage(DEFAULT_MESSAGE));
        }

        return VIEW_GAME;
    }

    /// 薬草を使う
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/use-herb")
    public String useHerb(Model model) {
        return handleGameAction(model, gameFacade::useHerb);
    }

    /// 次のエリアへ移動する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/move")
    public String move(Model model) {
        return handleGameAction(model, gameFacade::moveToNextArea);
    }

    /// 銅の剣を装備する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/equip-copper-sword")
    public String equipCopperSword(Model model) {
        return handleGameAction(model, gameFacade::equipCopperSword);
    }

    /// セーブする
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/save")
    public String save(Model model) {
        return handleGameAction(model, gameFacade::save);
    }

    /// ロードする
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/load")
    public String load(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.load(player);

        webGameSession.setLastMessage(result.message());

        return REDIRECT_PREFIX + PATH_MAP;
    }

    /// ゲーム操作を実行してゲームメニュー画面を再表示する
    /// @param model 画面モデル
    /// @param action プレイヤーに対するゲーム操作
    /// @return テンプレート名
    private String handleGameAction(Model model, Function<Player, GameActionResult> action) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = action.apply(player);

        model.addAttribute(ATTRIBUTE_MESSAGE, result.message());

        return game(model);
    }
}
