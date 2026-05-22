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

        StoryNode report3 = add(nodes, "report3", "橘雪莉", "但是机智的雪莉已经获取到信息啦！");
        report3.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report3.setNextNodeId("report4");

        StoryNode report4 = add(nodes, "report4", "橘雪莉", "首先是纪律！");
        report4.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report4.setNextNodeId("report5");

        add(nodes, "report5", "橘雪莉", "你们觉得你们自己纪律怎样呢？")
                .setNextNodeId("reportChoice1");

        StoryNode reportChoice1 = add(nodes, "reportChoice1", "橘雪莉", "你们觉得你们自己纪律怎样呢？");
        reportChoice1.addChoice("挺好", "report6A1", () -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        reportChoice1.addChoice("依托，，", "report6B1", () -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });

        add(nodes, "report6A1", "橘雪莉", "确实！这周的的纪律比之前好上了些呢。")
                .setNextNodeId("report6A2");

        StoryNode report6A2 = add(nodes, "report6A2", "橘雪莉", "比如说晚自习纪律，个人感觉比之前要安静些了。");
        report6A2.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report6A2.setNextNodeId("report6A3");

        StoryNode report6A3 = add(nodes, "report6A3", "橘雪莉", "也请各位继续保持啊！");
        report6A3.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report6A3.setNextNodeId("report7C1");

        StoryNode report6B1 = add(nodes, "report6B1", "橘雪莉", "啊嘞，这么不自信的吗……");
        report6B1.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_surprised.png");
        });
        report6B1.setNextNodeId("report6B2");

        StoryNode report6B2 = add(nodes, "report6B2", "橘雪莉", "其实大家这周的晚自习纪律比之前要好的啊~！");
        report6B2.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        report6B2.setNextNodeId("report7C1");

        StoryNode report7C1 = add(nodes, "report7C1", "橘雪莉", "然后提到纪律，那就得提到我们的跑操和做操纪律了。");
        report7C1.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report7C1.setNextNodeId("report7C2");

        StoryNode report7C2 = add(nodes, "report7C2", "橘雪莉", "让我点一下跑操和做操请假最多的人……");
        report7C2.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report7C2.setNextNodeId("report7C3");

        StoryNode report7C3 = add(nodes, "report7C3", "橘雪莉", "嗯，罗天翔，陈皓，黄一元，这几位请假次数挺多的！");
        report7C3.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report7C3.setNextNodeId("report7C4");

        StoryNode report7C4 = add(nodes, "report7C4", "橘雪莉", "还有一位多少带点私人恩怨的……");
        report7C4.setNextNodeId("report7C5");

        StoryNode report7C5 = add(nodes, "report7C5", "橘雪莉", "HSP 你作何感想呀。");
        report7C5.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
            sprites.setSprite("ema", "/sprites/ema_fearful.png", 300, 1200, 0.5D, false);
            sprites.animateSpriteSequence("ema", List.of(
                    SpriteManager.AnimationStep.translate(0.0D, -600.0D, 600, Util.CUBE_IN)
            ));
        });
        report7C5.setNextNodeId("report7C6");

        StoryNode report7C6 = add(nodes, "report7C6", "樱羽艾玛", "诶？");
        report7C6.setNextNodeId("report7");

        StoryNode report7 = add(nodes, "report7", "橘雪莉", "哦对，还有一件事。");
        report7.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
            sprites.removeSprite("ema", true);
        });
        report7.setNextNodeId("report9");

        // no 8

        StoryNode report9 = add(nodes, "report9", "橘雪莉", "你们长时间不在教室能不能写假条啊！");
        report9.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_angry.png");
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(350.0D, -20.0D, 100, Interpolator.LINEAR),
                    SpriteManager.AnimationStep.translate(350.0D, 0.0D, 100, Interpolator.LINEAR)
            ));
        });
        report9.setNextNodeId("report10");

        StoryNode report10 = add(nodes, "report10", "橘雪莉", "比如化奥什么的，我们根本不知道几个人去。");
        report10.setNextNodeId("report11");

        StoryNode report11 = add(nodes, "report11", "橘雪莉", "另外说到纪律……");
        report11.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report11.setNextNodeId("report12");

        StoryNode report12 = add(nodes, "report12", "橘雪莉", "我们的宿舍纪律到底能不能搞好啊！！");
        report12.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_angry.png");
        });
        report12.setNextNodeId("report13");

        StoryNode report13 = add(nodes, "report13", "橘雪莉", "这里点名 B310，惯犯。");
        report13.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_angry.png");
        });
        report13.setNextNodeId("report14");

        StoryNode report14 = add(nodes, "report14", "", "");
        report14.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(-300.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        report14.setAutoAdvanceMs(800);
        report14.setNextNodeId("report2E1");

        StoryNode report2E1 = add(nodes, "report2E1", "橘雪莉", "然后是有关卫生组！");
        report2E1.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E1.setNextNodeId("report2E2");

        StoryNode report2E2 = add(nodes, "report2E2", "橘雪莉", "我宣布：卫生组，我认可你了！");
        report2E2.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report2E2.setNextNodeId("report2E3");

        StoryNode report2E3 = add(nodes, "report2E3", "橘雪莉", "然后是……有关各种活动！");
        report2E3.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E3.setNextNodeId("report2E4");

        StoryNode report2E4 = add(nodes, "report2E4", "橘雪莉", "这周也是有特别多的活动，本人也是不幸参加了其中的一大半。");
        report2E4.setNextNodeId("report2E5");

        StoryNode report2E5 = add(nodes, "report2E5", "橘雪莉", "所以这个行政报告才这么赶~");
        report2E5.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        report2E5.setNextNodeId("report2E6");

        StoryNode report2E6 = add(nodes, "report2E6", "橘雪莉", "首先是合唱！");
        report2E6.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(0.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        report2E6.setNextNodeId("report2E7");

        StoryNode report2E7 = add(nodes, "report2E7", "橘雪莉", "大家的合唱积极性也是十分强呢，同时也要注意安排好时间哦！");
        report2E7.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report2E7.setNextNodeId("report2E8");

        StoryNode report2E8 = add(nodes, "report2E8", "橘雪莉", "关于英语词汇大赛……");
        report2E8.setNextNodeId("report2E9");

        StoryNode report2E9 = add(nodes, "report2E9", "橘雪莉", "嘿嘿。知道就好。");
        report2E9.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        report2E9.setNextNodeId("report2E10");

        StoryNode report2E10 = add(nodes, "report2E10", "橘雪莉", "以及其它一些活动，比如奥赛、演讲比赛什么的……");
        report2E10.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E10.setNextNodeId("report2E11");

        StoryNode report2E11 = add(nodes, "report2E11", "橘雪莉", "大家的积极性也是非常高啊！");
        report2E11.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report2E11.setNextNodeId("report2E12");

        StoryNode report2E12 = add(nodes, "report2E12", "", "");
        report2E12.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(350.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        report2E12.setAutoAdvanceMs(800);
        report2E12.setNextNodeId("report2E13");

        StoryNode report2E13 = add(nodes, "report2E13", "橘雪莉", "然后是文化墙的布置！");
        report2E13.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E13.setNextNodeId("report2E14");

        StoryNode report2E14 = add(nodes, "report2E14", "橘雪莉", "煌兄完成了一面文化墙的布置，也是十分尽职尽责啊！");
        report2E14.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report2E14.setNextNodeId("report2E15");

        StoryNode report2E15 = add(nodes, "report2E15", "橘雪莉", "同时提醒各位，这周找好自己小组的照片哦。可以现在拍。");
        report2E15.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E15.setNextNodeId("report2E16");

        StoryNode report2E16 = add(nodes, "report2E16", "橘雪莉", "那当然，也有不足之处。");
        report2E16.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(0.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        report2E16.setNextNodeId("report2E17");

        StoryNode report2E17 = add(nodes, "report2E17", "橘雪莉", "首先我们行政组的，组长不知道干啥吃的。");
        report2E17.setNextNodeId("report2E18");

        StoryNode report2E18 = add(nodes, "report2E18", "橘雪莉", "其次，周六到校学习人数也相对少……");
        report2E18.setNextNodeId("report2E19");

        StoryNode report2E19 = add(nodes, "report2E19", "橘雪莉", "这还少吗？！虽然我自己都没来诶。");
        report2E19.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_surprised.png");
        });
        report2E19.setNextNodeId("report2E20");

        StoryNode report2E20 = add(nodes, "report2E20", "橘雪莉", "再然后是作业完成情况……");
        report2E20.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E20.setNextNodeId("report2E21");

        StoryNode report2E21 = add(nodes, "report2E21", "橘雪莉", "嗯对，懂的都懂。泰师已经十分记挂此事了。");
        report2E21.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
        report2E21.setNextNodeId("report2E22");

        StoryNode report2E22 = add(nodes, "report2E22", "", "");
        report2E22.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(-300.0D, 0.0D, 600, Util.CUBE_IN)
            ));
        });
        report2E22.setAutoAdvanceMs(800);
        report2E22.setNextNodeId("report2E23");

        StoryNode report2E23 = add(nodes, "report2E23", "橘雪莉", "那么，我对大家还是有一些期待的！");
        report2E23.setOnEnter(() -> {
            sprites.animateSpriteSequence("sherry", List.of(
                    SpriteManager.AnimationStep.translate(0.0D, 0.0D, 600, Util.CUBE_IN)
            ));
            sprites.setSprite("sherry", "/sprites/sherry_smile.png");
        });
        report2E23.setNextNodeId("report2E24");

        StoryNode report2E24 = add(nodes, "report2E24", "橘雪莉", "那就是继续努力，同时劳逸结合！");
        report2E24.setOnEnter(() -> {
            engine.setBgm(Resources.GUI);
        });
        report2E24.setNextNodeId("report2E25");


        StoryNode report2E25 = add(nodes, "report2E25", "橘雪莉", "哦对，感谢幺鸡哥喊安静。谢谢。");
        report2E25.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_normal.png");
        });
        report2E25.setNextNodeId("report2E26");

        StoryNode report2E26 = add(nodes, "report2E26", "橘雪莉", "没了。我说了是赶工。");
        report2E26.setOnEnter(() -> {
            sprites.setSprite("sherry", "/sprites/sherry_embarrassed.png");
        });
    }

    private static StoryNode add(Map<String, StoryNode> nodes, String id, String speaker, String text) {
        StoryNode node = new StoryNode(id, speaker, text);
        nodes.put(id, node);
        return node;
    }
}