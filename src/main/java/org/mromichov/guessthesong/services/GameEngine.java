package org.mromichov.guessthesong.services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.mromichov.guessthesong.util.FileWorker;
import org.mromichov.guessthesong.model.MusicLibrary;
import org.mromichov.guessthesong.model.PieceOfMusic;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class GameEngine {
    private final YoutubeService youtubeService = new YoutubeService();
    private final AudioManager audioManager = new AudioManager(youtubeService);
    private final FileWorker fileWorker = new FileWorker();

    private final MusicLibrary musicLibrary = new MusicLibrary(); // TODO не пересоздавать его при запуске новой игры без изменений
    private List<PieceOfMusic> currentPlaylist;

    private int numberOfRounds = 5; // TODO считывать из файла настроек
    private int currentRound = 0;   // 0 - первый раунд

    private MediaPlayer mediaPlayer;

    public CompletableFuture<Void> startRound(Consumer<Double> onProgress) {
        if ((currentRound %= numberOfRounds) == 0)
             return prepareGame(onProgress)
                     .thenAccept(v -> {
                         audioManager.setPrepareNextRound(audioManager.prepareRound(String.valueOf(currentRound+1), currentPlaylist.get(currentRound+1)));
                     });
        return audioManager.getPrepareNextRound()
                .thenRun(() -> {
                    if (currentRound < numberOfRounds-1)
                        audioManager.setPrepareNextRound(audioManager.prepareRound(String.valueOf(currentRound+1), currentPlaylist.get(currentRound+1)));
                });
    }

    private CompletableFuture<Void> prepareGame(Consumer<Double> onProgress) {
        return createMusicLibraryAsync(onProgress)
                .thenApply(v -> {
                    fileWorker.deleteFiles(new File(AudioManager.MUSIC_DIR));
                    currentPlaylist = musicLibrary.generateAndGetRandomPieces(numberOfRounds);
                    return currentPlaylist;
                })
                .thenCompose(currentPlaylist -> {
                    onProgress.accept(-1.);
                    return audioManager.prepareRound(String.valueOf(currentRound), currentPlaylist.get(currentRound));
                });
    }

    public void playCurrentRoundMusic() {
        Media media = new Media(currentPlaylist.get(currentRound).getSnippetFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public String checkAnswer(String answer) {
        mediaPlayer.stop();
        // TODO currentPlaylist.get(currentRound) надо как-то заменить на поле с текущей pieceOfMusic
        int answeredRound = currentRound++;
        String rightAnswer = currentPlaylist.get(answeredRound).getTitle();
        String yourAnswer = "Your answer: " + answer +
                ((answer.equalsIgnoreCase(currentPlaylist.get(answeredRound).getTitle())
                        || currentPlaylist.get(answeredRound).getTitle().toLowerCase().contains(answer.toLowerCase())) ? "✅" : "❌");
        return rightAnswer + " " + yourAnswer;
    }

    private CompletableFuture<Void> createMusicLibraryAsync(Consumer<Double> onProgress) {
                                                            // TODO убрать этот хард код
        List<String> playlistUrls = fileWorker.getPlaylists(new File("playlists.txt"));
        AtomicInteger completed = new AtomicInteger(0);
        int playlistUrlsLen = playlistUrls.size();
        List<CompletableFuture<MusicLibrary>> futures = playlistUrls.stream()
                .map(url -> youtubeService.createMusicLibraryFromPlaylist(url)
                        .thenApply(musicLib -> {
                            onProgress.accept((double) completed.incrementAndGet() / playlistUrlsLen);
                            return musicLib;
                        })).toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenAccept(v -> {
                    futures.forEach(f -> {
                        musicLibrary.add(f.join()); // join тут безопасен, т.к. allOf уже дождался всех
                    });
                })
                .exceptionally(ex -> {
                    System.err.println("Error occurred while creating music library: " + ex.getMessage());
                    return null;
                });
    }

}

