package org.mromichov.guessthesong;

import org.mromichov.guessthesong.infrastructure.FfmpegClient;
import org.mromichov.guessthesong.infrastructure.YtDlpClient;

import java.io.File;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        YtDlpClient ytDlpClient = new YtDlpClient();
        FfmpegClient ffmpegClient = new FfmpegClient();

        Random random = new Random();
        File file = ytDlpClient.downloadAudio("https://youtu.be/JLSLO4QXGkc?si=5DmaKnU3nezlYppk", "music_cache", "song");
        int fullLength = ytDlpClient.getDuration("https://youtu.be/JLSLO4QXGkc?si=5DmaKnU3nezlYppk");
        ffmpegClient.createSnippet(file, "snippet", random.nextInt(0, fullLength), 5);
    }
}
