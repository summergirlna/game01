package com.example.rpg.character;

import lombok.Getter;

/// ゲーム内キャラクターの共通クラス
@Getter
public class GameCharacter {
    private final String name;

    private int maxHp;

    private int hp;

    private int attack;

    public GameCharacter(String name, int maxHp, int attack) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
    }

    /// ダメージを受けた際の処理
    /// 0より小さくなった場合は0にする
    /// @param damage ダメージを受けた数(HPを減らす数)
    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0)
            hp = 0;
    }

    /// HPを回復する
    /// 最大HPより大きくなった場合は最大HPにする
    /// @param amount 回復量
    public void heal(int amount) {
        hp += amount;

        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /// HPを最大値まで回復する
    public void healFull() {
        hp = maxHp;
    }

    /// 最大HPを増やす
    /// @param amount 増加量
    protected void increaseMaxHp(int amount) {
        maxHp += amount;
        hp += amount;
    }

    /// 攻撃力を増やす
    /// @param amount 増加量
    protected void increaseAttack(int amount) {
        attack += amount;
    }

    /// 生存しているか判定する
    /// @return 生存している場合はtrue, そうでない場合はfalse
    public boolean isAlive() {
        return hp > 0;
    }

}
