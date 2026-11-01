package com.example.rpg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/// モンスタークラス
@Getter
@AllArgsConstructor
public class Monster {
    private final String name;

    private int hp;

    private final int attack;

    /// ダメージを受けた際の処理
    /// 0より小さくなった場合は0にする
    /// @param damage ダメージを受けた数(HPを減らす数)
    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0)
            hp = 0;
    }

    /// 生存しているか判定する
    /// @return 生存している場合はtrue, そうでない場合はfalse
    public boolean isAlive() {
        return hp > 0;
    }
}
