package org.mromichov.guessthesong.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    // TODO Сделать файл с настройками
}
