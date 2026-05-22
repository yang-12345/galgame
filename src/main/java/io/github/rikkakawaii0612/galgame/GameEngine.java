package io.github.rikkakawaii0612.galgame;

import io.github.rikkakawaii0612.galgame.op.AnimationCanvas;
import io.github.rikkakawaii0612.galgame.op.OpeningAnimationEngine;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class GameEngine {
    private Stage stage;
    private Scene scene;
    private StackPane root;
    private Pane spriteLayer;
    private DialogManager dialogManager;
    private ChoiceManager choiceManager;
    private BackgroundManager backgroundManager;
    private SpriteManager spriteManager;
    private HistoryManager historyManager;
    private Map<String, StoryNode> nodeMap;
    private StoryNode currentNode;
    private boolean textFinished = false;
    private boolean waitingForChoice = false;
    private boolean optionJustSelected = false;
    private Timeline autoAdvanceTimer;   // 自动跳转定时器
    private MediaPlayer bgm;

    private boolean playingOp = false;

    public GameEngine(Stage stage) {
        this.stage = stage;
        this.nodeMap = new HashMap<>();
        initUI();
    }

    private void initUI() {
        backgroundManager = new BackgroundManager();
        Pane bgLayer = backgroundManager.getView();

        spriteManager = new SpriteManager();
        spriteLayer = spriteManager.getLayer();
        // 固定立绘层大小
        spriteLayer.setPrefSize(1280, 720);

        dialogManager = new DialogManager();
        historyManager = new HistoryManager();
        choiceManager = new ChoiceManager();

        // 对话框内部添加历史文本
        AnchorPane dialogBox = dialogManager.getView();
        Text historyText = historyManager.createText();
        dialogManager.setHistoryNode(historyText);   // 历史文本被固定在右下角

        // UI 底层：只包含对话框
        VBox uiBox = new VBox(dialogBox);
        uiBox.setAlignment(Pos.BOTTOM_CENTER);
        uiBox.setPickOnBounds(false);
        uiBox.setPadding(new Insets(20));

        // 选项容器独立，居中悬浮
        VBox choiceBox = choiceManager.getView();

        root = new StackPane(bgLayer, spriteLayer, uiBox, choiceBox);
        StackPane.setAlignment(choiceBox, Pos.CENTER);

        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Visual Novel Framework");
        stage.show();

        // 点击推进文本（穿透 uiBox）
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> {
            if (playingOp) {
                return;
            }
            if (optionJustSelected) {
                optionJustSelected = false;   // 重置标志
                return;                       // 忽略此次点击，不推进文本
            }
            if (!waitingForChoice) {
                advanceText();
            }
        });
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (playingOp) {
                    return;
                }
                if (optionJustSelected) {
                    optionJustSelected = false;   // 重置标志
                    return;                       // 忽略此次点击，不推进文本
                }
                if (!waitingForChoice) {
                    advanceText();
                }
            }
        });
    }

    public void start() {
        // 注册故事节点
        StoryScript.build(nodeMap, this);
        if (nodeMap.containsKey("start")) {
            goToNode("start");
        }
    }

    public void goToNode(String nodeId) {
        if (currentNode != null) currentNode.exit();
        StoryNode next = nodeMap.get(nodeId);
        if (next == null) return;
        currentNode = next;
        currentNode.enter();
        textFinished = false;
        waitingForChoice = false;
        choiceManager.hide();

        if (currentNode.getChoices().isEmpty()) {
            dialogManager.showText(currentNode.getSpeaker(), currentNode.getText(), () -> {
                textFinished = true;
                currentNode.onTextShown();
            });
        } else {
            // 有选项：显示完整文本后出现选项
            dialogManager.showTextInstant(currentNode.getSpeaker(), currentNode.getText());
            textFinished = true;
            waitingForChoice = true;
            List<StoryNode.Choice> choices = currentNode.getChoices();
            choiceManager.show(choices, choice -> {
                optionJustSelected = true;   // 关键：标记本次点击来自选项
                if (choice.getAction() != null) choice.getAction().run();
                goToNode(choice.getTargetNodeId());
            });
        }

        // 记录到历史
        if (currentNode.getSpeaker() != null && !currentNode.getSpeaker().isEmpty()) {
            historyManager.add(currentNode.getSpeaker() + ": " + currentNode.getText());
        } else {
            historyManager.add(currentNode.getText());
        }

        // 自动跳转处理（仅无选项节点）
        if (currentNode.getAutoAdvanceMs() > 0 && currentNode.getChoices().isEmpty()) {
            startAutoAdvance(currentNode.getAutoAdvanceMs());
        }
    }

    private void startAutoAdvance(int delayMs) {
        stopAutoAdvance();
        autoAdvanceTimer = new Timeline(new KeyFrame(Duration.millis(delayMs), _ -> {
            String nextId = currentNode.getNextNodeId();
            if (nextId != null && !nextId.isEmpty()) {
                goToNode(nextId);
            }
        }));
        autoAdvanceTimer.setCycleCount(1);
        autoAdvanceTimer.play();
    }

    private void stopAutoAdvance() {
        if (autoAdvanceTimer != null) {
            autoAdvanceTimer.stop();
            autoAdvanceTimer = null;
        }
    }

    private void advanceText() {
        stopAutoAdvance();
        if (waitingForChoice) return;  // 等待选项，不推进
        if (!textFinished) {
            // 直接完成文本显示
            dialogManager.finishText();
            textFinished = true;
            currentNode.onTextShown();
        } else {
            // 跳转到下一节点
            String nextId = currentNode.getNextNodeId();
            if (nextId != null && !nextId.isEmpty()) {
                goToNode(nextId);
            } else {
                // 没有下一节点，可能结束
                System.out.println("故事结束或未定义下一节点。");
            }
        }
    }

    public void playAudio(String path) {
        new AudioClip(path).play();
    }

    public void setBgm(String path) {
        if (this.bgm != null) {
            if (path != null) {
                this.bgm.stop();
            } else {
                Timeline fadeOut = new Timeline(
                        new KeyFrame(Duration.seconds(0.5D), new KeyValue(this.bgm.volumeProperty(), 0))
                );
                fadeOut.setOnFinished(_ -> {
                    this.bgm.stop();
                    this.bgm = null;
                });
                fadeOut.play();
            }
        }
        if (path != null) {
            this.bgm = new MediaPlayer(new Media(path));
            this.bgm.setCycleCount(AudioClip.INDEFINITE);
            this.bgm.setVolume(0.2D);
            this.bgm.play();
        }
    }

    public void playOp(String nextNode) {
        this.playingOp = true;
        OpeningAnimationEngine op = new OpeningAnimationEngine(() -> {
            this.stage.setScene(this.scene);
            this.playingOp = false;
            this.goToNode(nextNode);
        });
        AnimationCanvas view = op.getView();
        StackPane stackPane = new StackPane(view);
        stackPane.setBackground(Background.fill(Color.BLACK));
        Scene opScene = new Scene(stackPane);
        this.stage.setScene(opScene);
        op.start();
    }

    public BackgroundManager getBackgroundManager() { return backgroundManager; }
    public SpriteManager getSpriteManager() { return spriteManager; }
}