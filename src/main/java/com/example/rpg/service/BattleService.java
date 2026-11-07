package com.example.rpg.service;

import com.example.rpg.character.GameCharacter;
import com.example.rpg.character.Monster;
import com.example.rpg.character.Player;
import com.example.rpg.item.ItemType;
import org.springframework.stereotype.Service;

/// Web版戦闘処理を担当するサービス
@Service
public class BattleService {

    public GameActionResult attack(Player player, Monster monster, String winRedirectPath) {
        StringBuilder message = new StringBuilder();

        int playerDamage = applyAttack(player, monster);
        message.append(player.getName())
                .append("の攻撃！")
                .append(monster.getName())
                .append("に")
                .append(playerDamage)
                .append("ダメージ！");

        if (!monster.isAlive()) {
            player.addExp(monster.getExp());
            player.addGold(monster.getGold());

            message.append("\n")
                    .append(monster.getName())
                    .append("を倒した！")
                    .append("\n")
                    .append(monster.getExp())
                    .append(" 経験値と ")
                    .append(monster.getGold())
                    .append(" ゴールドを手に入れた。");

            if (player.hasLeveledUp()) {
                message.append("\n")
                        .append("レベルが ")
                        .append(player.getLevel().getValue())
                        .append(" に上がった！");

                player.resetLeveledUp();
            }

            return GameActionResult.redirect(message.toString(), winRedirectPath);
        }

        appendMonsterAttack(player, monster, message);

        if (!player.isAlive()) {
            message.append("\n")
                    .append(player.getName())
                    .append("は倒れた……");

            return GameActionResult.redirect(message.toString(), "/game-over");
        }

        return GameActionResult.message(message.toString());
    }

    /// 薬草を使う
    /// @param player プレイヤー
    /// @param monster モンスター
    /// @return 操作結果
    public GameActionResult useHerb(Player player, Monster monster) {
        StringBuilder message = new StringBuilder();
        ItemType itemType = ItemType.HERB;

        if (!player.getInventory().use(itemType)) {
            return GameActionResult.message(itemType.getDisplayName() + "を持っていません。");
        }

        player.heal(itemType.getHealAmount());
        message.append(itemType.getDisplayName())
                .append("を使った。HPが ")
                .append(itemType.getHealAmount())
                .append(" 回復した。");

        appendMonsterAttack(player, monster, message);

        if (!player.isAlive()) {
            message.append("\n")
                    .append(player.getName())
                    .append("は倒れた……");

            return GameActionResult.redirect(message.toString(), "/game-over");
        }

        return GameActionResult.message(message.toString());
    }

    /// 逃げる
    /// @param player プレイヤー
    /// @return 操作結果
    public GameActionResult escape(Player player) {
        return GameActionResult.redirect(player.getName() + "は逃げ出した！", "/map");
    }

    /// モンスターの攻撃メッセージを追加する
    /// @param player プレイヤー
    /// @param monster モンスター
    /// @param message メッセージ
    private void appendMonsterAttack(Player player, Monster monster, StringBuilder message) {
        int monsterDamage = applyAttack(monster, player);
        message.append("\n")
                .append(monster.getName())
                .append("の攻撃！")
                .append(player.getName())
                .append("に")
                .append(monsterDamage)
                .append("ダメージ！");
    }

    /// 攻撃する
    /// @param attacker 攻撃者
    /// @param target 攻撃対象
    /// @return ダメージ
    private int applyAttack(GameCharacter attacker, GameCharacter target) {
        int damage = attacker.getAttack();
        target.takeDamage(damage);
        return damage;
    }
}
