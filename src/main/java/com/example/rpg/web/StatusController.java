package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// ステータス画面コントローラ
@Controller
@RequiredArgsConstructor
public class StatusController {

    private static final String VIEW_STATUS = "status";
    private static final String ATTRIBUTE_STATUS = "status";

    private final WebGameSession webGameSession;

    /// ステータス画面を表示する
    ///
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/status")
    public String status(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute(ATTRIBUTE_STATUS, StatusView.from(player));

        return VIEW_STATUS;
    }
}
