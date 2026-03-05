package org.mromichov.guessthesong;

import org.mromichov.guessthesong.core.DownloadManager;

public class Test {
    public static void main(String[] args) {
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.downloadAudio("https://youtu.be/cHqi2hoAXYQ?si=icROAk4dxUmcniAE");
    }
}
