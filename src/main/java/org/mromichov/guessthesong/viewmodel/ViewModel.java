package org.mromichov.guessthesong.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import org.mromichov.guessthesong.services.GameEngine;

public class ViewModel {
    private final GameEngine engine = new GameEngine();

    private final ObjectProperty<GameState> state = new SimpleObjectProperty<>(GameState.MAIN_MENU);
    private final BooleanProperty isLoading = new SimpleBooleanProperty(false);
    private final DoubleProperty progress = new SimpleDoubleProperty();

    public void startRound() {
        isLoading.set(true);
        engine.startRound(val -> Platform.runLater(() -> progress.set(val)))
                .thenRun(() -> {
                    Platform.runLater(() -> {
                        isLoading.set(false);
                        state.set(GameState.GUESSING); // TODO доделать это сегодня
                    });
                    engine.playCurrentRoundMusic();
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> isLoading.set(false));
                    System.out.println(ex.getMessage());
                    return null;
                });
    }

    public String getRightAnswer(CharSequence characters) {
        state.set(GameState.ANSWERED);
        return engine.checkAnswer(characters.toString());
    }

    public BooleanProperty isLoadingProperty() {
        return isLoading;
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public ObjectProperty<GameState> stateProperty() {
        return state;
    }

}
