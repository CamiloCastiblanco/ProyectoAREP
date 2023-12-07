package edu.escuelaing.arep;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GitHubRepositoryCreator {

    private static final String GITHUB_API_URL = "https://api.github.com/user/repos";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al asistente inteligente con GitHub.");
        
        String sentence = scanner.nextLine();
        
        String[] x = NLP(sentence);
    
        if (x[2].equals("Create")){
            //Hi, Create 5000 projects for me, my token is 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 and their names will start with proof
            createAll(Integer.parseInt(x[3]),x[11],x[18]);
        }else if (x[2].equals( "Delete")) {
            //Hi, Delete 1 project for me, my token is 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 and its name start with proof
            deleteAll(Integer.parseInt(x[3]),x[11],x[17]);            
        } else {
            System.out.println("Error");
            
        }        
    }
    public static void createAll(int n, String token, String name)
    {
        for (int i = 0; i < n; i++){
            GitHubRepositoryCreator.createGitHubRepository(token, name + i);
        }
    }    
    public static void deleteAll(int n, String token, String name)
    {
        for (int i = 0; i < n; i++){
            GitHubRepositoryCreator.deleteGitHubRepository(token, name + i);
        }
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
    private static void deleteGitHubRepository(String token, String repoName) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete("https://api.github.com/repos/" + repoName);
    
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpDelete.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    
        try {
            HttpResponse response = httpClient.execute(httpDelete);
    
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Código de estado: " + statusCode);
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
    
            if (statusCode == 204) {
                System.out.println("Repositorio eliminado con éxito.");
            } else {
                System.out.println("Error al eliminar el repositorio.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] NLP(String sentence) throws IOException {
        InputStream modelIn = new FileInputStream("src/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
        TokenizerModel model = new TokenizerModel(modelIn);
        TokenizerME tokenizer = new TokenizerME(model);  
        String[] tokens = tokenizer.tokenize(sentence);
        return tokens;

        
    }
}