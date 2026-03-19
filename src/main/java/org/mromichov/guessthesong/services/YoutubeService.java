package org.mromichov.guessthesong.services;

import javafx.concurrent.Task;
import javafx.util.Pair;
import org.mromichov.guessthesong.infrastructure.YtDlpClient;
import org.mromichov.guessthesong.model.PieceOfMusic;
import org.mromichov.guessthesong.model.MusicLibrary;
import org.mromichov.guessthesong.util.BackgroundExecutor;


import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class YoutubeService {
    private final YtDlpClient ytDlpClient = new YtDlpClient();

    public CompletableFuture<File> downloadAudio(String url, String dirName, String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Downloading audio");
            File audioFile;
            audioFile = ytDlpClient.downloadAudio(url, dirName, fileName);
            if (audioFile != null && audioFile.exists()) {
                System.out.println("Audio has been downloaded successfully");
            } else {
                System.err.println("Error occurred while downloading audio");
            }
            return audioFile;
        }, BackgroundExecutor.getExecutor());
    }

    public CompletableFuture<Integer> getDuration(String url) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Receiving duration of the audio");
            int duration = ytDlpClient.getDuration(url);
            if (duration == -1) {
                System.err.println("Error occurred while receiving duration");
                throw new RuntimeException();
            }

            System.out.println("Duration has been received successfully");
            return duration;
        }, BackgroundExecutor.getExecutor());
    }

    public CompletableFuture<MusicLibrary> createMusicLibraryFromPlaylist(String playlistUrl) {
        return CompletableFuture.supplyAsync(() -> {
                System.out.println("Creating music library from: " + playlistUrl);
                MusicLibrary musicLibrary = new MusicLibrary();
                List<Pair<String, String>> titlesAndUrls = ytDlpClient.getTitlesAndUrlsFromPlaylist(playlistUrl);
                for (Pair<String, String> titleAndUrl : titlesAndUrls) {
                    musicLibrary.add(new PieceOfMusic(titleAndUrl.getKey(), titleAndUrl.getValue()));
                }
                System.out.println("Music library has been created from: " + playlistUrl);
                return musicLibrary;
        }, BackgroundExecutor.getExecutor());
    }
}
