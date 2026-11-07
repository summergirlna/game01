package com.example.rpg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/// タイトル画面コントローラ
@Controller
public class TitleController {

    private static final String VIEW_TITLE = "title";
    private static final String VIEW_HELP = "help";

    /// タイトル画面を表示する
    ///
    /// @return テンプレート名
    @GetMapping("/")
    public String title() {
        return VIEW_TITLE;
    }

    /// ヘルプ画面を表示する
    ///
    /// @return テンプレート名
    @GetMapping("/help")
    public String help() {
        return VIEW_HELP;
    }
}
