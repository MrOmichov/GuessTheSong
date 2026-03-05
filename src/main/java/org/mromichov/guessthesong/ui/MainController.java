package org.mromichov.guessthesong.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.mromichov.guessthesong.core.AudioClipper;
import org.mromichov.guessthesong.core.DownloadManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class MainController {
    @FXML
    private Label welcomeText;

    private MediaPlayer mediaPlayer;

    private final DownloadManager downloadManager = new DownloadManager();
    private final AudioClipper audioClipper = new AudioClipper();
    // TODO брать ссылки через плейлисты
    List<String> videoUrls = Arrays.asList(
            "https://youtu.be/cHqi2hoAXYQ?si=IGFauPEDQ47Tgdue",
            "https://youtu.be/OfM7_BHzpKQ?si=uI5x3aljXct3XJqz",
            "https://youtu.be/YM-4ye422Jo?si=hL1ZPbwS33LhGc6K",
            "https://youtu.be/fULE6VNAxiU?si=SXNIKAvi0SsKqdEB",
            "https://youtu.be/IqqRx77T4Vo?si=CbDPVEP8oXxHH2n9",
            "https://youtu.be/WqSXhynkz7g?si=7JfXn4qnIFPGvwfZ",
            "https://youtu.be/YaNxroBFWBU?si=1iTPljkbrbf1Bxri",
            "https://youtu.be/hA_MqPRvsJc?si=gbcjZ9oBsPojWvFw"
    );



    @FXML
    public void onPlayButtonClick(ActionEvent actionEvent) {
        Random random = new Random();
        int randomIndex = random.nextInt(0, videoUrls.size());

        String fileName = downloadManager.downloadAudio(videoUrls.get(randomIndex)) + ".mp3";
        int fullDuration = downloadManager.getDuration(videoUrls.get(randomIndex));
        int snippetDuration = 5;

        String snippetFileName = audioClipper.createSnippet(fileName, random.nextInt(0, fullDuration - snippetDuration), snippetDuration);
        Media snippet = new Media(snippetFileName);
        mediaPlayer = new MediaPlayer(snippet);
        mediaPlayer.play();
    }

    @FXML
    public void onGuessButtonClick(ActionEvent actionEvent) {
    }
}