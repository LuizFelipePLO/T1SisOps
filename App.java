import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        
    }

    public static List<String> lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        List<String> linhas = new ArrayList<>();
    
        while ((linha = reader.readLine()) != null) {
            if (linha.startsWith(".")) {
                // linha é uma diretiva, ignorar
                continue;
            }else if (linha.startsWith("#")) {
                //se a linha é um comentário, ignorar
                continue;
            } else {
                String[] tokens = linha.split("\\s+");
                if (tokens.length > 0) {
                    linhas.add(linha);
                }
            }
        }
    
        reader.close();
        return linhas;
    }
}
