package org.mromichov.guessthesong.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicLibrary {
    private final List<PieceOfMusic> allPieces;

    public MusicLibrary() {
        this.allPieces = new ArrayList<>();
    }


    public void add(PieceOfMusic pieceOfMusic) {
        allPieces.add(pieceOfMusic);
    }

    public synchronized void add(MusicLibrary anotherMusicLibrary) {
        allPieces.addAll(anotherMusicLibrary.getAllPieces());
    }

    public List<PieceOfMusic> generateAndGetRandomPieces(int numberOfPieces) {
        List<PieceOfMusic> shuffled = new ArrayList<>(allPieces);
        Collections.shuffle(shuffled);
        return shuffled.stream().limit(numberOfPieces).toList();
    }

    public List<PieceOfMusic> getAllPieces() {
        return allPieces;
    }
}
