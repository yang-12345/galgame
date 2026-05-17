module io.github.rikkakawaii0612.galgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens io.github.rikkakawaii0612.galgame to javafx.fxml;
    exports io.github.rikkakawaii0612.galgame;
}