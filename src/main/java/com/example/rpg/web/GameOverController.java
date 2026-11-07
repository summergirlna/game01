package com.example.rpg.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// ゲームオーバー画面コントローラ
@Controller
@RequiredArgsConstructor
public class GameOverController {

    private final WebGameSession webGameSession;

    /// ゲームオーバー画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/game-over")
    public String gameOver(Model model) {
        model.addAttribute("player", webGameSession.getPlayer());
        model.addAttribute("message", webGameSession.consumeLastMessage("勇者は倒れた……"));

        return "game-over";
    }
}
