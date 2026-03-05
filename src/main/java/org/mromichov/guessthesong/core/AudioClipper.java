package org.mromichov.guessthesong.core;

import java.io.File;

import static org.mromichov.guessthesong.core.DownloadManager.MUSIC_CACHE;

public class AudioClipper {
    public String createSnippet(String inputFileName, int startSeconds, int duration) {
        File inputFile = new File(MUSIC_CACHE, inputFileName);
        File outputFile = new File(MUSIC_CACHE, "snippet_" + inputFileName);

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg",
                    "-y",                // Перезаписывать файл, если существует
                    "-ss", String.valueOf(startSeconds), // Откуда резать (напр. 30 сек)
                    "-t", String.valueOf(duration),     // Сколько секунд (напр. 10 сек)
                    "-i", inputFile.getAbsolutePath(),
                    "-acodec", "copy",   // Быстрая обрезка без перекодирования
                    outputFile.getAbsolutePath()
            );

            pb.inheritIO();
            pb.start().waitFor();
            // TODO это фигня, надо как-то иначе прописывать пути и называть файлы. Нейроонка тут мне не помощник
            return outputFile.toURI().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
