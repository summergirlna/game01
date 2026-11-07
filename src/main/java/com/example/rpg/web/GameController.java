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

    private final WebGameSession webGameSession;

    private final GameFacade gameFacade;

    /// ゲームメニュー画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/game")
    public String game(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute("player", player);
        model.addAttribute("isTown", player.getCurrentArea().name().equals("TOWN"));

        if (!model.containsAttribute("message")) {
            model.addAttribute("message", webGameSession.consumeLastMessage("コマンドを選んでください。"));
        }

        return "game";
    }

    /// 薬草を使う
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/use-herb")
    public String useHerb(Model model) {
        return handleGameAction(model, gameFacade::useHerb);
    }

    /// 宿屋に泊まる
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/inn")
    public String stayInn(Model model) {
        return handleGameAction(model, gameFacade::stayInn);
    }

    /// 次のエリアへ移動する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/move")
    public String move(Model model) {
        return handleGameAction(model, gameFacade::moveToNextArea);
    }

    /// 薬草を購入する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/buy-herb")
    public String buyHerb(Model model) {
        return handleGameAction(model, gameFacade::buyHerb);
    }

    /// 銅の剣を装備する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/equip-copper-sword")
    public String equipCopperSword(Model model) {
        return handleGameAction(model, gameFacade::equipCopperSword);
    }

    /// 銅の剣を購入する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/buy-copper-sword")
    public String buyCopperSword(Model model) {
        return handleGameAction(model, gameFacade::buyCopperSword);
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
        return handleGameAction(model, gameFacade::load);
    }

    /// ゲーム操作を実行してゲームメニュー画面を再表示する
    /// @param model 画面モデル
    /// @param action プレイヤーに対するゲーム操作
    /// @return テンプレート名
    private String handleGameAction(Model model, Function<Player, GameActionResult> action) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = action.apply(player);

        model.addAttribute("message", result.message());

        return game(model);
    }
}
