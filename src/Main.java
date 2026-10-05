
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String continuar = "sim";

        List<Address> addresses = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while (continuar.equals("sim")) {

            System.out.print("Digite um CEP: ");
            String CEP = reader.nextLine();

            if (CEP.length() != 8 || !CEP.matches("\\d+")) {
                System.out.println("CEP inválido. Por favor, digite um CEP válido.");
                continue;
            }


            String url = "https://viacep.com.br/ws/" + CEP + "/json/";


            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                Address address = gson.fromJson(json, Address.class);

                if ("true".equals(address.getErro())) {
                    System.out.println("CEP não encontrado. Por favor, digite um CEP válido.");
                    continue;
                }

                System.out.println(address);

                addresses.add(address);

                try (FileWriter file = new FileWriter("address.json")) {
                    file.write(gson.toJson(addresses));
                }

                System.out.println("\nDeseja consultar outro CEP? (sim/não)");
                continuar = reader.nextLine().toLowerCase();
            } catch (IOException | InterruptedException e) {
                System.out.println("Ocorreu um erro ao consultar o CEP: " + e.getMessage());
                System.out.println("Deseja tentar novamente? (sim/não)");
                continuar = reader.nextLine().toLowerCase();
            }
        }
        System.out.println("\nPrograma encerrado.");
    }
}