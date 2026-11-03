package com.example.rpg;

/// 戦闘処理を担当するクラス
public class Battle {
    private final Player player;

    private final Monster monster;

    private final BattleMessage message;

    public Battle(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.message = new BattleMessage();
    }

    public void start() {
        message.showStart();
        message.showAppear(monster);

        while (player.isAlive() && monster.isAlive()) {
            attack(player, monster);

            if (!monster.isAlive()) {
                break;
            }

            attack(monster, player);

            message.showHp(player, monster);
        }

        showResult();
    }

    /// 攻撃処理
    /// @param attacker 攻撃者
    /// @param target 攻撃対象
    private void attack(GameCharacter attacker, GameCharacter target) {
        int damage = attacker.getAttack();

        target.takeDamage(damage);
        message.showAttack(attacker, target, damage);
    }

    /// 戦闘結果表示
    private void showResult() {
        if (player.isAlive()) {
            message.showWin(monster);
        } else {
            message.showLose(player);
        }
    }
}
