package com.example.rpg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/// タイトル画面コントローラ
@Controller
public class TitleController {

    /// タイトル画面を表示する
    ///
    /// @return テンプレート名
    @GetMapping("/")
    public String title() {
        return "title";
    }

    /// ヘルプ画面を表示する
    ///
    /// @return テンプレート名
    @GetMapping("/help")
    public String help() {
        return "help";
    }

}
