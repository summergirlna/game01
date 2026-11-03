package com.example.rpg.ui;

import com.example.rpg.battle.BattleResult;
import com.example.rpg.field.FieldArea;

/// 探索メッセージ表示を担当するクラス
public class ExploreMessage {

    /// エリアを探索した時のメッセージを表示する
    /// @param area 探索するエリア
    public void showExploreArea(FieldArea area) {
        System.out.println(area.getDisplayName() + "を探索した。");
    }

    /// 町が安全であることを表示する
    public void showSafeTown() {
        System.out.println("町は安全だ。モンスターは出ない。");
    }

    /// 何も起きなかった時のメッセージを表示する
    public void showNoEvent() {
        System.out.println("特に何も起きなかった。");
    }

    /// 戦闘結果を表示する
    /// @param result 戦闘結果
    public void showBattleResult(BattleResult result) {
        System.out.println("戦闘結果: " + result);
    }
}
