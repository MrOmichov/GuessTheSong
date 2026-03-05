package org.mromichov.guessthesong.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DownloadManager {
    // TODO разобраться с путями, а то это очень плохо выглядит
    public static final String MUSIC_CACHE = "music_cache";

    public DownloadManager() {
        File dir = new File(MUSIC_CACHE);
        if (!dir.exists()) dir.mkdirs();
    }

    public String downloadAudio(String videoUrl) {
        String outputTemplate = MUSIC_CACHE + File.separator + "%(id)s.%(ext)s";

        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "-x",
                "--audio-format", "mp3",
                "--no-keep-video",
                "-o", outputTemplate,
                videoUrl
        );

        pb.inheritIO();
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Загрузка завершена успешно!");
                // TODO Именование файлов и вытаскивание их имён стоит пересмотреть
                pb = new ProcessBuilder(
                        "yt-dlp",
                        "--get-id",
                        videoUrl
                );

                process = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                process.waitFor();
                return reader.readLine();
            } else {
                System.err.println("Ошибка при загрузке. Код: " + exitCode);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getDuration(String videoUrl) {
        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "--print", "duration",
                videoUrl
        );
//        pb.inheritIO();

        try {
            Process process = pb.start();
            pb.inheritIO();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return Integer.parseInt(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
