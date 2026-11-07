package com.example.rpg.web;

import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;
import com.example.rpg.service.BattleService;
import com.example.rpg.service.GameActionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.BiFunction;

/// Web版戦闘画面コントローラ
@Controller
@RequiredArgsConstructor
public class BattleController {

    private final WebGameSession webGameSession;

    private final BattleService battleService;

    /// 戦闘画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/battle")
    public String battle(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        Player player = webGameSession.getPlayer();
        Monster monster = webGameSession.getCurrentMonster();
        String message = webGameSession.consumeLastMessage(monster.getName() + "が現れた！");

        addBattleAttributes(model, player, monster, message);

        return "battle";
    }

    /// 攻撃する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/battle/attack")
    public String attack(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        String winRedirectPath = webGameSession.isBossBattle() ? "/clear" : "/map";

        return handleBattleAction(model, (player, monster) -> battleService.attack(player, monster, winRedirectPath));

    }

    /// 薬草を使う
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/battle/use-herb")
    public String useHerb(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        return handleBattleAction(model, battleService::useHerb);
    }

    /// 逃げる
    ///
    /// @return リダイレクト先
    @PostMapping("/battle/escape")
    public String escape() {
        Player player = webGameSession.getPlayer();

        GameActionResult result = battleService.escape(player);
        webGameSession.clearBattle();
        webGameSession.setLastMessage(result.message());

        return "redirect:" + result.redirectPath();
    }

    /// 戦闘操作を実行する
    /// @param model 画面モデル
    /// @param action 戦闘操作
    /// @return テンプレート名またはリダイレクト先
    private String handleBattleAction(Model model, BiFunction<Player, Monster, GameActionResult> action) {
        Player player = webGameSession.getPlayer();
        Monster monster = webGameSession.getCurrentMonster();

        GameActionResult result = action.apply(player, monster);

        if (result.hasRedirect()) {
            webGameSession.clearBattle();
            webGameSession.setLastMessage(result.message());
            return "redirect:" + result.redirectPath();
        }

        addBattleAttributes(model, player, monster, result.message());

        return "battle";
    }

    /// 戦闘画面用属性を追加する
    /// @param model 画面モデル
    /// @param player プレイヤー
    /// @param monster モンスター
    /// @param message メッセージ
    private void addBattleAttributes(Model model, Player player, Monster monster, String message) {
        model.addAttribute("player", player);
        model.addAttribute("monster", monster);
        model.addAttribute("message", message);
    }
}
