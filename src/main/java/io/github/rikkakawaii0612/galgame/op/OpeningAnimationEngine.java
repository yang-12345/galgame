package io.github.rikkakawaii0612.galgame.op;

import io.github.rikkakawaii0612.galgame.App;
import io.github.rikkakawaii0612.galgame.Resources;
import io.github.rikkakawaii0612.galgame.Util;
import io.github.rikkakawaii0612.galgame.op.node.CircleNode;
import io.github.rikkakawaii0612.galgame.op.node.ImageNode;
import io.github.rikkakawaii0612.galgame.op.node.RectNode;
import io.github.rikkakawaii0612.galgame.op.node.TextNode;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * OP 风格实时渲染核心框架
 */
public class OpeningAnimationEngine {
    private final MediaPlayer music;
    private final AnimationCanvas canvas;

    public OpeningAnimationEngine(Runnable onFinishedCallback) {
        this.music = new MediaPlayer(new Media(Resources.OP));
        this.music.play();
        this.canvas = new AnimationCanvas(1280, 720, 23.0D, () -> {
            this.music.stop();
            onFinishedCallback.run();
        });
    }

    public AnimationCanvas getView() {
        return canvas;
    }

    public void start() {
        buildScene(canvas);
        canvas.start();
    }


    private void buildScene(AnimationCanvas canvas) {
        ImageNode fireInTheSky = new ImageNode(-700, -1500, "/op/fire_in_the_sky.jpg");
        canvas.addNode(fireInTheSky);
        fireInTheSky.alpha.addKeyframe(0, 0.0D);
        fireInTheSky.alpha.addKeyframe(time(0), 1.0D);
        fireInTheSky.x.addKeyframe(time(0), time(8), -800, Easing.linear);

        TextNode author1 = new TextNode(-100, 50, "Author", Font.font(App.fangzheng, 24), Color.WHITE);
        canvas.addNode(author1);
        author1.alpha.addKeyframe(0, 0.0D);
        author1.x.addKeyframe(time(0.5), time(2.5), 0, Easing.easeOutCubic);
        author1.alpha.addKeyframe(time(0.5), time(2.5), 0.0D, 1.0D, Easing.easeOutCubic);
        author1.alpha.addKeyframe(time(8), 0.0D);

        TextNode author2 = new TextNode(-100, 95, "RikkaKawaii0612", Font.font(App.fangzheng, 54), Color.WHITE);
        canvas.addNode(author2);
        author2.alpha.addKeyframe(0, 0.0D);
        author2.x.addKeyframe(time(0.5), time(2.5), 0, Easing.easeOutCubic);
        author2.alpha.addKeyframe(time(0.5), time(2.5), 0.0D, 1.0D, Easing.easeOutCubic);
        author2.alpha.addKeyframe(time(8), 0.0D);


        fireInTheSky.x.addKeyframe(time(8), time(12), -1000, -900, Easing.linear);
        fireInTheSky.y.addKeyframe(time(8), -400);

        TextNode origin1 = new TextNode(-400, -100, "Original Track", Font.font(App.fangzheng, 24), Color.WHITE);
        canvas.addNode(origin1);
        origin1.alpha.addKeyframe(0, 0.0D);
        origin1.x.addKeyframe(time(8), time(10), -500, Easing.easeOutCubic);
        origin1.alpha.addKeyframe(time(8), time(10), 0.0D, 1.0D, Easing.easeOutCubic);
        origin1.alpha.addKeyframe(time(12), 0.0D);

        TextNode origin2 = new TextNode(-400, -55, "影色舞", Font.font(App.fangzheng, 54), Color.WHITE);
        canvas.addNode(origin2);
        origin2.alpha.addKeyframe(0, 0.0D);
        origin2.x.addKeyframe(time(8), time(10), -500, Easing.easeOutCubic);
        origin2.alpha.addKeyframe(time(8), time(10), 0.0D, 1.0D, Easing.easeOutCubic);
        origin2.alpha.addKeyframe(time(12), 0.0D);

        TextNode title = new TextNode(0, 50, "第一小组行政报告", Font.font(App.fangzheng, 192), Color.WHITE);
        title.setAlignment(TextAlignment.CENTER);
        title.alpha.addKeyframe(0, 0.0D);
        title.alpha.addKeyframe(time(12), 1.0D);
        canvas.addNode(title);

        fireInTheSky.x.addKeyframe(time(12), -1832);
        fireInTheSky.y.addKeyframe(time(12), -1030);

        canvas.camera.zoom.addKeyframe(time(12), 0.9D);
        canvas.camera.zoom.addKeyframe(time(12.75), 0.65D);
        canvas.camera.zoom.addKeyframe(time(13), 0.55D);
        canvas.camera.zoom.addKeyframe(time(13.5), 0.4D);

        fireInTheSky.alpha.addKeyframe(time(14.5), 0.0D);
        title.alpha.addKeyframe(time(14.5), 0.0D);

        canvas.camera.zoom.addKeyframe(time(16), 1.0D);
        title.y.addKeyframe(time(16), 20);
        title.alpha.addKeyframe(time(16), 1.0D);
        title.scaleX.addKeyframe(time(16), time(48), 0.5D, 0.8D, Easing.linear);
        title.scaleY.addKeyframe(time(16), time(48), 0.5D, 0.8D, Easing.linear);

        for (int i = 1; i <= 16; i++) {
            ImageNode imageNode = new ImageNode(0, 0, "/op/flashback/" + i + ".jpg");
            imageNode.x.addKeyframe(0, -imageNode.width / 2);
            imageNode.y.addKeyframe(0, -imageNode.height / 2);
            canvas.addNode(imageNode);
            imageNode.alpha.addKeyframe(0, 0.0D);
            for (int j = 0; j < (i > 12 ? 1 : 2); j++) {
                imageNode.alpha.addKeyframe(time(15 + i + 16 * j), 1.0D);
                imageNode.alpha.addKeyframe(time(16 + i + 16 * j), 0.0D);
                imageNode.scaleX.addKeyframe(time(15 + i + 16 * j), time(16 + i + 16 * j), 1.6D, 1.7D, Easing.linear);
                imageNode.scaleY.addKeyframe(time(15 + i + 16 * j), time(16 + i + 16 * j), 1.6D, 1.7D, Easing.linear);
            }
        }

        RectNode flashbackMask = new RectNode(-640.0D, -360.0D, 1280.0D, 720.0D, Color.gray(0.0D, 0.4D));
        canvas.addNode(flashbackMask);
        flashbackMask.alpha.addKeyframe(0, 0.0D);
        flashbackMask.alpha.addKeyframe(time(16), 1.0D);
        flashbackMask.alpha.addKeyframe(time(44), 0.0D);

        canvas.toTop(title);


        TextNode title2 = new TextNode(0, 30, "下次再做完整版XD", Font.font(App.fangzheng, 192), Color.WHITE);
        title2.setAlignment(TextAlignment.CENTER);
        title2.alpha.addKeyframe(0, 0.0D);
        canvas.addNode(title2);

        title.alpha.addKeyframe(time(44), 0.0D);
        fireInTheSky.alpha.addKeyframe(time(44), 1.0D);
        title2.alpha.addKeyframe(time(44), 1.0D);

        canvas.camera.zoom.addKeyframe(time(44), 0.9D);
        canvas.camera.zoom.addKeyframe(time(44.5), 0.65D);
        canvas.camera.zoom.addKeyframe(time(45), 0.55D);
        canvas.camera.zoom.addKeyframe(time(45.5), 0.4D);

        title2.alpha.addKeyframe(18.8D, 20.134D, 0.0D, Easing.easeInCubic);
        fireInTheSky.alpha.addKeyframe(18.8D, 20.134D, 0.0D, Easing.easeInCubic);
    }

    // Offset: 1692
    // BPM: 166
    private static double time(double beats) {
        return 1.692D + beats * 60.0D / 166.0D;
    }

    private void buildExampleScene(AnimationCanvas canvas) {
        // 1. 添加一些图形节点
        RectNode rect = new RectNode(100, 100, 60, 60, Color.DODGERBLUE);
        rect.x.addKeyframe(0.0D, 2.0D, 400, Easing.easeInOutQuad);      // 平移动画
        rect.rotation.addKeyframe(0.0D, 3.0D, 360, Easing.easeInOutQuad); // 旋转动画
        canvas.addNode(rect);

        CircleNode circle = new CircleNode(300, 200, 40, Color.ORANGERED);
        circle.scaleX.addKeyframe(0.0D, 1.5D, 2.0D, Easing.easeOutBounce);
        circle.scaleY.addKeyframe(0.0D, 1.5D, 2.0D, Easing.easeOutBounce);
        canvas.addNode(circle);

        // 2. 图像节点（请替换为本地图片路径或使用资源文件）
        Image image = new Image(Util.resource("/op/fire_in_the_sky.jpg")); // 若没有图片，请用其他图形代替
        ImageNode imageNode = new ImageNode(200, 100, image);
        imageNode.width = 100;
        imageNode.height = 100;
        imageNode.alpha.addKeyframe(0.0D, 2.0D, 0.5D, Easing.easeInOutCubic);
        imageNode.alpha.addKeyframe(2.0D, 4.0D, 1.0D, Easing.easeInOutCubic);
        canvas.addNode(imageNode);

        // 3. 摄像机动画
        canvas.camera.x.addKeyframe(0.0D, 4.0D, 200, Easing.easeInOutQuad);
        canvas.camera.y.addKeyframe(0.0D, 4.0D, 100, Easing.easeInOutQuad);
        canvas.camera.zoom.addKeyframe(0.0D, 4.0D, 1.5, Easing.easeInOutQuad);
        canvas.camera.rotation.addKeyframe(0.0D, 4.0D, 15, Easing.easeInOutQuad);
    }
}