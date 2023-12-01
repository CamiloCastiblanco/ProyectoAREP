package edu.escuelaing.arep;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GitHubRepositoryCreator {

    private static final String GITHUB_API_URL = "https://api.github.com/user/repos";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al creador de repositorios GitHub.");
        System.out.print("Por favor, ingresa tu token de acceso personal de GitHub: ");
        String token = scanner.nextLine();

        System.out.print("Ingresa el nombre del repositorio que deseas crear: ");
        String repoName = scanner.nextLine();

        createGitHubRepository(token, repoName);
    }

    private static void createGitHubRepository(String token, String repoName) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(GITHUB_API_URL);

        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String jsonBody = "{\"name\": \"" + repoName + "\"}";

        try {
            httpPost.setEntity(new StringEntity(jsonBody));
            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            if (statusCode == 201) {
                System.out.println("Repositorio creado con éxito.");
            } else {
                System.out.println("Error al crear el repositorio. Código de estado: " + statusCode);
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}