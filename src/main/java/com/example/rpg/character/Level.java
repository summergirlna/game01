package com.example.rpg.character;

import lombok.Getter;

/// レベルを表す値オブジェクト
@Getter
public class Level {
    private final int value;

    public Level(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("レベルは1以上である必要があります。");
        }

        this.value = value;
    }

    /// 初期レベルを作成する
    /// @return 初期レベル
    public static Level initial() {
        return new Level(1);
    }

    /// 次のレベルを作成する
    /// @return 次のレベル
    public Level next() {
        return new Level(value + 1);
    }

    /// 次のレベルに必要な経験値を取得する
    /// @return 次のレベルに必要な経験値
    public int getRequiredExpForNextLevel() {
        return value * 10;
    }
}
