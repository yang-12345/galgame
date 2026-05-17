package io.github.rikkakawaii0612.galgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class DialogManager {
    private AnchorPane box;               // 改为 AnchorPane
    private Text speakerText;
    private TextFlow textFlow;
    private Text contentText;
    private Timeline timeline;
    private String fullText;
    private int charIndex;
    private Runnable onFinished;

    public DialogManager() {
        box = new AnchorPane();
        box.setStyle(
                "-fx-background-color: rgba(0,0,0,0.5);" +
                        "-fx-padding: 15;" +
                        "-fx-background-radius: 5;"
        );
        box.setPrefHeight(200);
        box.setMaxWidth(1200);

        // 说话人：固定在左上角
        speakerText = new Text();
        speakerText.setFill(Color.CYAN);
        speakerText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(speakerText, 0.0D);
        AnchorPane.setLeftAnchor(speakerText, 15.0);

        // 正文文本区域
        contentText = new Text();
        contentText.setFill(Color.WHITE);
        contentText.setStyle("-fx-font-size: 22px;");
        textFlow = new TextFlow(contentText);
        textFlow.setMaxWidth(1100);
        AnchorPane.setTopAnchor(textFlow, 35.0D);
        AnchorPane.setLeftAnchor(textFlow, 15.0);
        AnchorPane.setRightAnchor(textFlow, 15.0);
        AnchorPane.setBottomAnchor(textFlow, 40.0); // 留出底部给历史文本

        // 历史文本占位，稍后由 GameEngine 注入
        // （我们在构造函数中不添加历史文本，而是提供一个方法让外部设置）

        box.getChildren().addAll(speakerText, textFlow);
    }

    public AnchorPane getView() {
        return box;
    }

    // 添加历史文本节点（在右下角）
    public void setHistoryNode(Text historyText) {
        AnchorPane.setBottomAnchor(historyText, 10.0);
        AnchorPane.setRightAnchor(historyText, 15.0);
        box.getChildren().add(historyText);
    }

    public void showText(String speaker, String text, Runnable onFinished) {
        this.onFinished = onFinished;
        speakerText.setText(speaker != null ? speaker : "");
        fullText = text;
        charIndex = 0;
        contentText.setText("");

        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            if (charIndex < fullText.length()) {
                contentText.setText(fullText.substring(0, ++charIndex));
            } else {
                timeline.stop();
                if (onFinished != null) onFinished.run();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * 立即显示全部文本（用于选项节点的文本）
     */
    public void showTextInstant(String speaker, String text) {
        if (timeline != null) timeline.stop();
        speakerText.setText(speaker != null ? speaker : "");
        contentText.setText(text);
    }

    /**
     * 直接完成当前渐出文本
     */
    public void finishText() {
        if (timeline != null) {
            timeline.stop();
            contentText.setText(fullText);
            if (onFinished != null) onFinished.run();
        }
    }
}