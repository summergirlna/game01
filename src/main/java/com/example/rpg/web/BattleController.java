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

/// Web版戦闘画面コントローラ
@Controller
@RequiredArgsConstructor
public class BattleController {

    private final WebGameSession webGameSession;

    private final BattleService battleService;

    /// 戦闘画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/battle")
    public String battle(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        Player player = webGameSession.getPlayer();
        Monster monster = webGameSession.getCurrentMonster();

        model.addAttribute("player", player);
        model.addAttribute("monster", monster);
        model.addAttribute("message", webGameSession.consumeLastMessage(monster.getName()) + "が現れた！");

        return "battle";
    }

    /// 攻撃する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/battle/attack")
    public String attack(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        Player player = webGameSession.getPlayer();
        Monster monster = webGameSession.getCurrentMonster();
        String winRedirectPath = webGameSession.isBossBattle() ? "/clear" : "/map";

        GameActionResult result = battleService.attack(player, monster, winRedirectPath);

        if (result.hasRedirect()) {
            webGameSession.clearBattle();
            webGameSession.setLastMessage(result.message());
            return "redirect:" + result.redirectPath();
        }

        model.addAttribute("player", player);
        model.addAttribute("monster", monster);
        model.addAttribute("message", result.message());

        return "battle";
    }

    /// 薬草を使う
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/battle/use-herb")
    public String useHerb(Model model) {
        if (!webGameSession.isInBattle()) {
            return "redirect:/map";
        }

        Player player = webGameSession.getPlayer();
        Monster monster = webGameSession.getCurrentMonster();

        GameActionResult result = battleService.useHerb(player, monster);

        if (result.hasRedirect()) {
            webGameSession.clearBattle();
            webGameSession.setLastMessage(result.message());
            return "redirect:" + result.redirectPath();
        }

        model.addAttribute("player", player);
        model.addAttribute("monster", monster);
        model.addAttribute("message", result.message());

        return "battle";
    }

    /// 逃げる
    /// @return リダイレクト先
    @PostMapping("/battle/escape")
    public String escape() {
        Player player = webGameSession.getPlayer();

        GameActionResult result = battleService.escape(player);
        webGameSession.clearBattle();
        webGameSession.setLastMessage(result.message());

        return "redirect:" + result.redirectPath();
    }
}
