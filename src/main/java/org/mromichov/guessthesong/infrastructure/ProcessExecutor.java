package org.mromichov.guessthesong.infrastructure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProcessExecutor {
    private List<String> output = new ArrayList<>();

    public int execute(String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            process.waitFor();

            output = reader.readAllLines();
//            for (String s : output) System.out.println(s);
            return process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public List<String> getOutput() {
        return output;
    }
}
