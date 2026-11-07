package com.example.rpg.value;

import lombok.Getter;

/// ゴールドを表す値オブジェクト
@Getter
public class Gold {
    private final int value;

    public Gold(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("ゴールドは0以上である必要があります。");
        }

        this.value = value;
    }

    /// 初期ゴールドを作成する
    ///
    /// @return 初期ゴールド
    public static Gold zero() {
        return new Gold(0);
    }

    /// ゴールドを加算する
    ///
    /// @param amount 加算する量
    /// @return 加算後のゴールド
    public Gold add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("加算するゴールドは0以上である必要があります。");
        }

        return new Gold(value + amount);
    }

    /// ゴールドを支払う
    ///
    /// @param amount 支払う量
    /// @return 支払い後のゴールド
    public Gold subtract(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("支払うゴールドは0以上である必要があります。");
        }

        if (value < amount) {
            throw new IllegalArgumentException("ゴールドが足りません。");
        }

        return new Gold(value - amount);
    }

    /// 指定した金額以上持っているか判定する
    ///
    /// @param amount 金額
    /// @return 足りている場合はtrue、そうでない場合はfalse
    public boolean hasEnough(int amount) {
        return value >= amount;
    }
}
