package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/// Web版ゲーム画面コントローラ
@Controller
@RequiredArgsConstructor
public class GameController {

    private final WebGameSession webGameSession;

    private final WebGameService webGameService;

    /// トップ画面を表示する
    /// @return リダイレクト先
    @GetMapping("/")
    public String index() {
        return "redirect:/game";
    }

    /// ゲームメニュー画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/game")
    public String game(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute("player", player);
        model.addAttribute("isTown", player.getCurrentArea().name().equals("TOWN"));

        return "game";
    }

    /// 薬草を使う
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/use-herb")
    public String useHerb(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = webGameService.useHerb(player);
        model.addAttribute("message", result.message());

        return game(model);
    }

    /// 宿屋に泊まる
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/inn")
    public String stayInn(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = webGameService.stayInn(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// 次のエリアへ移動する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/move")
    public String move(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = webGameService.moveToNextArea(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

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
