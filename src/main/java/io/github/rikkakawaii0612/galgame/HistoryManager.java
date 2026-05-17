package io.github.rikkakawaii0612.galgame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class HistoryManager {
    private LinkedList<String> history;
    private static final int MAX_SIZE = 50;

    public HistoryManager() {
        history = new LinkedList<>();
    }

    public void add(String entry) {
        if (history.size() >= MAX_SIZE) {
            history.removeFirst();
        }
        history.addLast(entry);
    }

    public Button createButton() {
        Button btn = new Button("历史");
        btn.setOnAction(e -> showHistoryWindow());
        btn.setStyle("-fx-font-size: 14px;");
        return btn;
    }

    public Text createText() {
        Text text = new Text("历史");
        text.setFill(Color.LIGHTGRAY);
        text.setStyle("-fx-font-size: 14px; -fx-underline: true; -fx-cursor: hand;");
        text.setOnMouseClicked(e -> showHistoryWindow());
        return text;
    }

    private void showHistoryWindow() {
        Stage stage = new Stage();
        stage.setTitle("对话历史");
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(history);
        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}