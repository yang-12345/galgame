module io.github.rikkakawaii0612.galgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.rikkakawaii0612.galgame to javafx.fxml;
    exports io.github.rikkakawaii0612.galgame;
}