package org.mromichov.guessthesong.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileWorker {
    public List<String> getPlaylists(File file) {
        List<String> playlists = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String playlistUrl;
            while ((playlistUrl = reader.readLine()) != null){
                playlists.add(playlistUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return playlists;
    }

    public void deleteFiles(File dir) {
        if (!dir.exists()) return;
        Arrays.stream(dir.listFiles()).forEach(file -> file.delete());
    }

    // TODO Сделать файл с настройками
}
