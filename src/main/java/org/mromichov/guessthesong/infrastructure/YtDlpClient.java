package org.mromichov.guessthesong.infrastructure;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class YtDlpClient {
    private final ProcessExecutor executor = new ProcessExecutor();

    /**
     * Downloads audio from the video
     * @param url Video URL
     * @param dirPath Directory to save audio file
     * @param fileName Audio file name
     * @return File instance initialized with download path or with empty path if error occurs
     */
    public File downloadAudio(String url, String dirPath, String fileName) {
        String outputTemplate = dirPath + File.separator + fileName + ".%(ext)s";
        int exitCode = executor.execute(
                "yt-dlp",
                "--quiet",
                "-x",
                "--audio-format", "mp3",
                "--no-keep-video",
                "-o", outputTemplate,
                "--print", "\"after_move:filepath\"", // --print "after_move:filepath" говорит утилите вывести путь до файла после всех загрузки и обработки
                url
        );
        if (exitCode == 0) {
            return new File(executor.getOutput().getFirst()); // вывод из консоли
        } else {
            return null;
        }
    }

    /**
     * Gets duration of the video
     * @param url Video URL
     * @return Duration or -1 if error occurs
     */
    public int getDuration(String url) {
        int exitCode = executor.execute(
                "yt-dlp",
                "--print", "duration",
                url
        );

        if (exitCode == 0) {
            int duration = Integer.parseInt(executor.getOutput().getFirst());
            return duration;
        }
        return -1;
    }


    public List<Pair<String, String>> getTitlesAndUrlsFromPlaylist(String playlistUrl) {
        List<Pair<String, String>> titlesAndUrls = new ArrayList<>();

        int exitCode = executor.execute(
                "yt-dlp",
                "--quiet",
                "--print", "%(title)s",
                "--print", "webpage_url",
                "--flat-playlist",
                playlistUrl
        );
        if (exitCode == 0) {
            List<String> fullOutput = executor.getOutput();
            for (int i = 0; i < fullOutput.size() - 1; i += 2) {
                Pair<String, String> titleAndUrl = new Pair<>(fullOutput.get(i), fullOutput.get(i + 1));
                titlesAndUrls.add(titleAndUrl);
            }
            return titlesAndUrls;
        } else {
            return titlesAndUrls;
        }
    }
}
