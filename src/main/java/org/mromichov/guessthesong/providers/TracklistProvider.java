package org.mromichov.guessthesong.providers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TracklistProvider {
    private final HttpClient client = HttpClient.newHttpClient();

    public String provide(String artist) throws URISyntaxException, IOException, InterruptedException {
        // TODO try catch

        HttpRequest request = HttpRequest.newBuilder() // id=3296287 Queen
                .uri(new URI("https://itunes.apple.com/lookup?id=3296287&entity=song&limit=50")) // TODO форматный ввод
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
