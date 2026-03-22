package org.mromichov.guessthesong.infrastructure;

import java.io.File;

public class FfmpegClient {
    private final ProcessExecutor executor = new ProcessExecutor();

    /**
     * Creates snippet from given audio file
     * @param fullAudioFile Full audio file
     * @param snippetPrefix Prefix for snippet file
     * @param startSeconds Beginning time of snippet
     * @param snippetLen Length of snippet
     * @return File instance initialized with snippet file path or with empty path if error occurs
     */
    public File createSnippet(File fullAudioFile, String snippetPrefix, int startSeconds, int snippetLen) {
        String snippetFileName = fullAudioFile.getParent() + File.separator + snippetPrefix + fullAudioFile.getName();
        int exitCode = executor.execute(
                "ffmpeg",
                "-y",
                "-ss", Integer.toString(startSeconds),
                "-t", Integer.toString(snippetLen),
                "-i", fullAudioFile.getAbsolutePath(),
                snippetFileName
        );

        if (exitCode == 0) {
            return new File(snippetFileName);
        } else {
            return new File("");
        }
    }
}
