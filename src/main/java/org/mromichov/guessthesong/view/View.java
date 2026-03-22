package org.mromichov.guessthesong.view;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.mromichov.guessthesong.viewmodel.GameState;
import org.mromichov.guessthesong.viewmodel.ViewModel;

public class View {
    // Main screen
    @FXML private VBox mainMenuContainer;
    @FXML private Button playButton;
    @FXML private Button sourceFileButton;
    @FXML private VBox fileView;

    @FXML private FileView fileViewController;

    // Loading
    @FXML private ProgressBar loading;

    // Guessing screen
    @FXML private VBox guessingContainer;
    @FXML private TextField guessField;
    @FXML private Button guessButton;

    // Answered screen
    @FXML private VBox answeredContainer;
    @FXML private Label rightAnswer;
    @FXML private Button nextButton;

    private final ViewModel viewModel = new ViewModel();

    @FXML private void initialize() {
        loading.visibleProperty().bind(viewModel.isLoadingProperty());
        loading.managedProperty().bind(viewModel.isLoadingProperty());
        loading.progressProperty().bind(viewModel.progressProperty());

        mainMenuContainer.visibleProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.MAIN_MENU));
        mainMenuContainer.managedProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.MAIN_MENU));
        mainMenuContainer.disableProperty().bind(viewModel.isLoadingProperty());

        guessingContainer.visibleProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.GUESSING));
        guessingContainer.managedProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.GUESSING));
        guessingContainer.disableProperty().bind(viewModel.isLoadingProperty());

        answeredContainer.visibleProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.ANSWERED));
        answeredContainer.managedProperty().bind(Bindings.equal(viewModel.stateProperty(), GameState.ANSWERED));
        answeredContainer.disableProperty().bind(viewModel.isLoadingProperty());

        fileViewController.setViewModel(viewModel);
    }

    public void onPlayButtonClick(ActionEvent actionEvent) {
        viewModel.startRound();
    }

    public void onGuessButtonClick(ActionEvent actionEvent) {
        rightAnswer.setText(viewModel.getRightAnswer(guessField.getCharacters()));
    }


    public void onNextButtonClick(ActionEvent actionEvent) {
        viewModel.startRound();
    }

    public void onSourceFileButtonClick(ActionEvent actionEvent) {
        viewModel.showChangingFile(true);
    }
}
