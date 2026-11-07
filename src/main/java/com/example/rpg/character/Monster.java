package com.example.rpg.character;

import lombok.Getter;

/// モンスタークラス
@Getter
public class Monster extends GameCharacter {
    private final int exp;

    private final int gold;

    public Monster(String name, int hp, int attack, int exp, int gold) {
        super(name, hp, attack);
        this.exp = exp;
        this.gold = gold;
    }

    /// モンスター表示用にCSSクラスを返す
    ///
    /// @return モンスター表示用CSSクラス
    public String getSpriteCssClass() {
        return switch (getName()) {
            case "スライム" -> "monster-slime";
            case "こうもり" -> "monster-bat";
            case "ゴブリン" -> "monster-goblin";
            case "オーク" -> "monster-orc";
            case "魔王" -> "monster-demon-lord";
            default -> "monster-unknown";
        };
    }
}
