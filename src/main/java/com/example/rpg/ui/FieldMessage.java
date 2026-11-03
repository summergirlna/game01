package com.example.rpg.ui;

import com.example.rpg.field.FieldArea;

/// フィールド移動メッセージ表示を担当するクラス
public class FieldMessage {

    /// エリア移動メッセージを表示する
    /// @param area 移動先エリア
    public void showMoveArea(FieldArea area) {
        System.out.println(area.getDisplayName() + "へ移動した。");
    }
}
