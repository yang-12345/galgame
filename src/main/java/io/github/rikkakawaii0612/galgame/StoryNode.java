package io.github.rikkakawaii0612.galgame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StoryNode {
    private String id;
    private String speaker;              // 说话人名字
    private String text;                 // 完整文本
    private List<Choice> choices;        // 选项列表
    private Runnable onEnter;            // 进入节点时执行（可改变背景、立绘等）
    private Runnable onExit;             // 离开节点时执行
    private String nextNodeId;           // 无选项时的下一节点ID
    private Consumer<String> onTextShown;// 文本显示完毕后的回调，参数为本节点ID
    private int autoAdvanceMs = 0;       // 自动跳转延迟（毫秒），0 表示不自动跳转

    public StoryNode(String id, String speaker, String text) {
        this.id = id;
        this.speaker = speaker;
        this.text = text;
        this.choices = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getSpeaker() { return speaker; }
    public String getText() { return text; }
    public List<Choice> getChoices() { return choices; }
    public String getNextNodeId() { return nextNodeId; }
    public void setAutoAdvanceMs(int ms) { this.autoAdvanceMs = ms; }

    public void setOnEnter(Runnable onEnter) { this.onEnter = onEnter; }
    public void setOnExit(Runnable onExit) { this.onExit = onExit; }
    public void setOnTextShown(Consumer<String> callback) { this.onTextShown = callback; }
    public void setNextNodeId(String nextNodeId) { this.nextNodeId = nextNodeId; }
    public int getAutoAdvanceMs() { return autoAdvanceMs; }

    public void enter() {
        if (onEnter != null) onEnter.run();
    }
    public void exit() {
        if (onExit != null) onExit.run();
    }
    public void onTextShown() {
        if (onTextShown != null) onTextShown.accept(id);
    }

    public void addChoice(String text, String targetNodeId, Runnable action) {
        choices.add(new Choice(text, targetNodeId, action));
    }

    public static class Choice {
        private String text;
        private String targetNodeId;
        private Runnable action;   // 选择后额外执行的代码

        public Choice(String text, String targetNodeId, Runnable action) {
            this.text = text;
            this.targetNodeId = targetNodeId;
            this.action = action;
        }
        public String getText() { return text; }
        public String getTargetNodeId() { return targetNodeId; }
        public Runnable getAction() { return action; }
    }
}