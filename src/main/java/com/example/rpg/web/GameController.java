package com.example.rpg.web;

import com.example.rpg.character.Player;
import com.example.rpg.equipment.WeaponType;
import com.example.rpg.item.ItemType;
import com.example.rpg.service.GameActionResult;
import com.example.rpg.service.GameFacade;
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

    private final GameFacade gameFacade;

    /// トップ画面を表示する
    /// @return リダイレクト先
    @GetMapping("/")
    public String index() {
        return "title";
    }

    /// ゲームメニュー画面を表示する
    /// @param model 画面モデル
    /// @return テンプレート名
    @GetMapping("/game")
    public String game(Model model) {
        Player player = webGameSession.getPlayer();

        model.addAttribute("player", player);
        model.addAttribute("isTown", player.getCurrentArea().name().equals("TOWN"));

        if (!model.containsAttribute("message")) {
            model.addAttribute("message", webGameSession.consumeLastMessage("コマンドを選んでください。"));
        }

        return "game";
    }

    /// 薬草を使う
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/use-herb")
    public String useHerb(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.useHerb(player);
        model.addAttribute("message", result.message());

        return game(model);
    }

    /// 宿屋に泊まる
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/inn")
    public String stayInn(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.stayInn(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// 次のエリアへ移動する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/move")
    public String move(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.moveToNextArea(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// 薬草を購入する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/buy-herb")
    public String buyHerb(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.buyHerb(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// 銅の剣を装備する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/equip-copper-sword")
    public String equipCopperSword(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.equipCopperSword(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// 銅の剣を購入する
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/buy-copper-sword")
    public String buyCopperSword(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.buyCopperSword(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// セーブする
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/save")
    public String save(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.save(player);

        model.addAttribute("message", result.message());
        return game(model);
    }

    /// ロードする
    /// @param model 画面モデル
    /// @return テンプレート名
    @PostMapping("/game/load")
    public String load(Model model) {
        Player player = webGameSession.getPlayer();
        GameActionResult result = gameFacade.load(player);

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
