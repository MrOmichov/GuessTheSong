package org.mromichov.guessthesong.services;

import javafx.util.Pair;
import org.mromichov.guessthesong.infrastructure.FfmpegClient;
import org.mromichov.guessthesong.model.PieceOfMusic;
import org.mromichov.guessthesong.util.BackgroundExecutor;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class AudioManager {
    // TODO вынести в файл настроек
    public static final String MUSIC_DIR = "music";
    private static final String SNIPPET_PREFIX = "snippet";
    private static final int SNIPPET_LEN = 3;

    private final YoutubeService youtubeService;

    private CompletableFuture<Void> prepareNextRound;
    public AudioManager(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    private final FfmpegClient ffmpegClient = new FfmpegClient();

    public CompletableFuture<Void> prepareRound(String fileName, PieceOfMusic pieceOfMusic) {
        CompletableFuture<Integer> durationFuture = youtubeService.getDuration(pieceOfMusic.getUrl());
        CompletableFuture<File> downloadAudio = youtubeService.downloadAudio(pieceOfMusic.getUrl(), MUSIC_DIR, fileName);
        return downloadAudio
                .thenCombine(durationFuture, (fullAudioFile, duration) -> {
                    pieceOfMusic.setFullAudioFile(fullAudioFile);
                    int startSeconds = ThreadLocalRandom.current().nextInt(0, duration-SNIPPET_LEN);
                    return new Pair<>(fullAudioFile, startSeconds);
                })
                .thenCompose(snippetData -> createSnippet(snippetData.getKey(), SNIPPET_PREFIX, snippetData.getValue(), SNIPPET_LEN))
                .thenAccept(pieceOfMusic::setSnippetFile);
    }

    private CompletableFuture<File> createSnippet(File fullAudioFile, String snippetPrefix, int startSeconds, int snippetLen) {
        return CompletableFuture.supplyAsync(() -> {
            File snippetFile = ffmpegClient.createSnippet(fullAudioFile, snippetPrefix, startSeconds, snippetLen);

            if (!snippetFile.getAbsolutePath().isEmpty())
                System.out.println("Snippet has been created successfully");
            else
                System.out.println("Error occurred while creating snippet");
            return snippetFile;
        }, BackgroundExecutor.getExecutor());
    }

    public CompletableFuture<Void> getPrepareNextRound() {
        return prepareNextRound;
    }

    public void setPrepareNextRound(CompletableFuture<Void> f) {
        prepareNextRound = f;
    }
}
