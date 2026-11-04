package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/// Web版ゲーム画面コントローラ
@Controller
@RequiredArgsConstructor
public class GameController {

    private final WebGameSession webGameSession;

    /// ステータス画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/status")
    public String status(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute("name", player.getName());
        model.addAttribute("area", player.getCurrentArea().getDisplayName());
        model.addAttribute("level", player.getLevel().getValue());
        model.addAttribute("hp", player.getHp());
        model.addAttribute("maxHp", player.getMaxHp());
        model.addAttribute("attack", player.getAttack());
        model.addAttribute("weapon", player.getEquipment().getWeapon().getDisplayName());
        model.addAttribute("hasCopperSword", player.getEquipment().getInventory().hasWeapon(WeaponType.COPPER_SWORD));
        model.addAttribute("exp", player.getExp());
        model.addAttribute("gold", player.getGold());
        model.addAttribute("herbAmount", player.getInventory().getAmount(ItemType.HERB));

        return "status";
    }
}
