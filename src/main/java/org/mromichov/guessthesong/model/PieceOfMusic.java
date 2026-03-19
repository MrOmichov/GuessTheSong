package org.mromichov.guessthesong.model;

import java.io.File;

public class PieceOfMusic {
    private final String title;
    private final String url;
    private DownloadStatus downloadStatus; // TODO Пока не использую, а надо
    private File fullAudioFile;
    private File snippetFile;

    public PieceOfMusic(String title, String url) {
        this.title = title;
        this.url = url;
        this.downloadStatus = DownloadStatus.NOT_DOWNLOADED;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(DownloadStatus downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public File getFullAudioFile() {
        return fullAudioFile;
    }

    public void setFullAudioFile(File audioFile) {
        this.fullAudioFile = audioFile;
    }

    public File getSnippetFile() {
        return snippetFile;
    }

    public void setSnippetFile(File snippetFile) {
        this.snippetFile = snippetFile;
    }
}
