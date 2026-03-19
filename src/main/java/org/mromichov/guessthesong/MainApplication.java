package org.mromichov.guessthesong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("view/main-view.fxml")
        );

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Guess the Song");
        stage.show();
    }


}