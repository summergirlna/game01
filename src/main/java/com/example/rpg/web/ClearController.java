package com.example.rpg.web;

import com.example.rpg.service.GameActionResult;
import com.example.rpg.service.SaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// ゲームクリア画面コントローラ
@Controller
@RequiredArgsConstructor
public class ClearController {

    private static final String VIEW_CLEAR = "clear";
    private static final String ATTRIBUTE_PLAYER = "player";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_SAVE_MESSAGE = "saveMessage";
    private static final String DEFAULT_CLEAR_MESSAGE = "ボスを倒した！";

    private final WebGameSession webGameSession;

    private final SaveService saveService;

    /// ゲームクリア画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/clear")
    public String clear(Model model) {
        GameActionResult saveResult = saveService.save(webGameSession.getPlayer());

        model.addAttribute(ATTRIBUTE_PLAYER, webGameSession.getPlayer());
        model.addAttribute(ATTRIBUTE_MESSAGE, webGameSession.consumeLastMessage(DEFAULT_CLEAR_MESSAGE));
        model.addAttribute(ATTRIBUTE_SAVE_MESSAGE, saveResult.message());

        return VIEW_CLEAR;
    }
}
