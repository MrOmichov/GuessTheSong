module org.mromichov.guessthesong {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.google.gson;
    requires javafx.media;

    opens org.mromichov.guessthesong to javafx.fxml, com.google.gson;
    exports org.mromichov.guessthesong;
    exports org.mromichov.guessthesong.view;
    opens org.mromichov.guessthesong.view to javafx.fxml;
}