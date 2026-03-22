package org.mromichov.guessthesong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.mromichov.guessthesong.viewmodel.ViewModel;

public class FileView {
    private ViewModel viewModel = new ViewModel();

    @FXML public Button closeButton;
    @FXML public TextArea fileTextArea;
    @FXML public Button saveButton;
    @FXML public VBox fileViewContainer;

    @FXML public void initialize() {

    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;

        fileViewContainer.visibleProperty().bind(viewModel.isChangingFileProperty());
        fileViewContainer.managedProperty().bind(viewModel.isChangingFileProperty());
    }

    public void onCloseButtonClicked() {
        viewModel.showChangingFile(false);
    }

    public void onSaveButtonClicked() {

    }
}
