package com.example.rpg.web;

import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// Web版戦闘画面コントローラ
@Controller
@RequiredArgsConstructor
public class BattleController {

    private final WebGameSession webGameSession;

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
        model.addAttribute("message", monster.getName() + "が現れた！");

        return "battle";
    }
}
