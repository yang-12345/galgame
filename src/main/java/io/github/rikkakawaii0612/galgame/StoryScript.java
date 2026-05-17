package io.github.rikkakawaii0612.galgame;

import javafx.animation.Interpolator;

import java.util.List;
import java.util.Map;

public class StoryScript {
    public static void build(Map<String, StoryNode> nodes, GameEngine engine) {
        BackgroundManager bg = engine.getBackgroundManager();
        SpriteManager sprites = engine.getSpriteManager();

        // 节点定义
        StoryNode start1 = add(nodes, "start", "", "眼前是一片混沌。");
        start1.setOnEnter(() -> {
            bg.setImage("/backgrounds/black.png");
            engine.setBgm(Resources.START_BGM);
        });
        start1.setNextNodeId("start2");

        add(nodes, "start2", "", "你不知道你为什么会出现在这里，只模糊地记得刚才还在教室里睡觉。")
                .setNextNodeId("start3");

        add(nodes, "start3", "", "……也许，这是一场梦？或者只是睡迷糊了？")
                .setNextNodeId("start4");

        add(nodes, "start4", "", "你这么想着，隐隐约约听到了一些神秘的声音……")
                .setNextNodeId("start5");

        add(nodes, "start5", "", "那是……")
                .setNextNodeId("start6");

        StoryNode start6 = add(nodes, "start6", "橘雪莉", "嗨嗨！");
        start6.setOnEnter(() -> {
            bg.setImage("/backgrounds/school.jpeg");
            engine.setBgm(null);
            engine.playAudio(Resources.SHERRY_HAIHAI);
            sprites.setSprite("sherry", "/sprites/sherry_normal.png", 600, 1200, 1.2D, false);
        });
        start6.setNextNodeId("op");

        StoryNode op = add(nodes, "op", "", "");
        op.setOnEnter(() -> engine.playOp("intro1"));

        StoryNode intro1 = add(nodes, "intro1", "橘雪莉", "嘿嘿，终于开始行政报告了！");
        intro1.setOnEnter(() -> {
            engine.setBgm(Resources.NORMAL_BGM);
            sprites.setSprite("sherry", "/sprites/sherry_smile.png", 600, 600, 0.5D, true);
        });
        intro1.setNextNodeId("intro2");

        StoryNode intro2 = add(nodes, "intro2", "橘雪莉", "虽然这个行政报告有点赶工的说~");
        intro2.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        intro2.setNextNodeId("intro3");

        add(nodes, "intro3", "橘雪莉", "所以你们还想看吗？")
                .setNextNodeId("introChoice1");

        StoryNode introChoice1 = add(nodes, "introChoice1", "橘雪莉", "所以你们还想看吗？");
        introChoice1.addChoice("想看", "intro4A1", () -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        introChoice1.addChoice("不想", "intro4B1", () -> {
            sprites.setSprite("sherry", "/sprites/sherry_surprised.png");
        });

        add(nodes, "intro4A1", "橘雪莉", "好嘞！接下来就有请名侦探雪莉给大家梳理上周的情况！")
                .setNextNodeId("intro5");

        StoryNode intro4B = add(nodes, "intro4B1", "橘雪莉", "不……不看也得看！");
        intro4B.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(0.0D, -20.0D, 100, Interpolator.LINEAR),
                    SpriteManager.AnimationStep.translate(0.0D, 0.0D, 100, Interpolator.LINEAR)
            ));
        });
        intro4B.setNextNodeId("intro4B2");

        add(nodes, "intro4B2", "橘雪莉", "我只是展示一下选项功能而已~！")
                .setNextNodeId("intro5");

        StoryNode intro5 = add(nodes, "intro5", "", "");
        intro5.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(350.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        intro5.setAutoAdvanceMs(800);
        intro5.setNextNodeId("report1");

        StoryNode report1 = add(nodes, "report1", "橘雪莉", "嗯对，其实我没干什么的说（");
        report1.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        report1.setNextNodeId("report2");

        add(nodes, "report2", "橘雪莉", "我们组员还是太强大了。")
                .setNextNodeId("report3");

        add(nodes, "report2", "橘雪莉", "我们组员还是太强大了。")
                .setNextNodeId("report3");
    }

    private static StoryNode add(Map<String, StoryNode> nodes, String id, String speaker, String text) {
        StoryNode node = new StoryNode(id, speaker, text);
        nodes.put(id, node);
        return node;
    }
}