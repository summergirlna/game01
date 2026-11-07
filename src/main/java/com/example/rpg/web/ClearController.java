package com.example.rpg.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// ゲームクリア画面コントローラ
@Controller
@RequiredArgsConstructor
public class ClearController {

    private final WebGameSession webGameSession;

    /// ゲームクリア画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/clear")
    public String clear(Model model) {
        model.addAttribute("player", webGameSession.getPlayer());
        model.addAttribute("message", webGameSession.consumeLastMessage("ボスを倒した！"));

        return "clear";
    }
}
