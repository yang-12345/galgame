package io.github.rikkakawaii0612.galgame;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class App extends Application {
    public static String fangzheng;

    @Override
    public void start(Stage primaryStage) {
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/fangzheng.otf")) {
            // 加载字体，并指定大小为12点 (可根据需要调整)
            fangzheng = Font.loadFont(fontStream, 12).getFamily();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameEngine engine = new GameEngine(primaryStage);
        engine.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}