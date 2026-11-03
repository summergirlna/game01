package com.example.rpg.app;

import com.example.rpg.battle.BattleResult;
import com.example.rpg.character.MonsterFactory;
import com.example.rpg.character.Player;
import com.example.rpg.field.GameMap;
import com.example.rpg.field.MapResult;
import com.example.rpg.service.*;
import com.example.rpg.ui.command.GameCommand;
import com.example.rpg.ui.command.TitleCommand;
import com.example.rpg.ui.equipment.EquipmentMessage;
import com.example.rpg.ui.field.FieldMessage;
import com.example.rpg.ui.field.MapMessage;
import com.example.rpg.ui.game.GameMessage;
import com.example.rpg.ui.game.StatusMessage;
import com.example.rpg.ui.inn.InnMessage;
import com.example.rpg.ui.item.ItemMessage;
import com.example.rpg.ui.save.LoadMessage;
import com.example.rpg.ui.save.SaveMessage;
import com.example.rpg.ui.shop.ShopMessage;
import com.example.rpg.ui.shop.WeaponShopMessage;
import com.example.rpg.ui.title.TitleMessage;

import java.util.Scanner;

/// ゲーム全体の進行を管理するクラス
public class Game {
    private final Player player;

    private final GameMessage message;

    private final TitleMessage titleMessage;

    private final StatusMessage statusMessage;

    private final MapService mapService;

    private final InnService innService;

    private final ItemService itemService;

    private final EquipmentService equipmentService;

    private final FieldService fieldService;

    private final ShopService shopService;

    private final WeaponShopService weaponShopService;

    private final SaveService saveService;

    private final LoadService loadService;

    private final Scanner scanner;

    private boolean running;

    public Game() {
        this.player = new Player("勇者", 30, 8);
        MonsterFactory monsterFactory = new MonsterFactory();
        this.message = new GameMessage();
        this.titleMessage = new TitleMessage();
        this.statusMessage = new StatusMessage();
        this.mapService = new MapService(
                player,
                GameMap.initial(),
                new EncounterService(player, monsterFactory),
                new MapMessage()
        );
        this.innService = new InnService(new InnMessage());
        this.itemService = new ItemService(new ItemMessage());
        this.equipmentService = new EquipmentService(new EquipmentMessage());
        this.fieldService = new FieldService(new FieldMessage());
        this.shopService = new ShopService(new ShopMessage());
        this.weaponShopService = new WeaponShopService(new WeaponShopMessage());
        this.saveService = new SaveService(new SaveMessage());
        this.loadService = new LoadService(new LoadMessage());
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /// ゲームを開始する
    public void start() {
        if (!showTitle()) {
            message.showEnd();
            return;
        }

        message.showStart();

        while (running && player.isAlive()) {
            message.showMenu(player.getCurrentArea());

            String input = scanner.nextLine();
            GameCommand command = GameCommand.fromInput(input, player.getCurrentArea());

            switch (command) {
                case EXPLORE -> exploreMap();
                case STATUS -> statusMessage.showStatus(player);
                case INN -> innService.stay(player);
                case ITEM -> itemService.useHerb(player);
                case EQUIPMENT -> equipmentService.equipCopperSword(player);
                case MOVE -> fieldService.moveToNextArea(player);
                case SHOP -> shopService.buyHerb(player);
                case WEAPON_SHOP -> weaponShopService.buyCopperSword(player);
                case SAVE -> saveService.save(player);
                case LOAD -> loadService.load(player);
                case QUIT -> quit();
                case INVALID -> message.showInvalidCommand();
            }
        }

        message.showEnd();
    }

    /// マップを探索する
    private void exploreMap() {
        MapResult result = mapService.start();

        if (result == MapResult.LOSE) {
            running = false;
        }
    }

    /// ゲームを終了する
    private void quit() {
        running = false;
    }

    /// タイトル画面を表示する
    /// @return ゲームを開始する場合はtrue、終了する場合はfalse
    private boolean showTitle() {
        while (true) {
            titleMessage.showTitle();

            String input = scanner.nextLine();
            TitleCommand command = TitleCommand.fromInput(input);

            switch (command) {
                case NEW_GAME -> {
                    titleMessage.showNewGame();
                    return true;
                }
                case LOAD_GAME -> {
                    if (loadService.load(player)) {
                        titleMessage.showLoadGame();
                        return true;
                    }
                }
                case QUIT -> {
                    return false;
                }
                case INVALID -> titleMessage.showInvalidCommand();
            }
        }


    }
}
