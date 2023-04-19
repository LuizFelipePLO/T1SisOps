import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {

        String[] fileNames = { "T1SisOps\\ex_pgms_tp1\\prog1.txt", "T1SisOps\\ex_pgms_tp1\\prog2.txt",
                "T1SisOps\\ex_pgms_tp1\\prog3.txt" };

        System.out.println("Escolha o modelo do escalonador:");
        System.out.println("1. Shortest Job First");
        System.out.println("2. Round Robin");

        Scanner menuScanner = new Scanner(System.in);
        int option = menuScanner.nextInt();

        switch (option) {
            case 1:
                try {
                    for (int i = 0; i < fileNames.length; i++) {
                        readData(readFile(fileNames[i]));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EscalonadorSJF sjf = new EscalonadorSJF();
                break;

            case 2:
                try {
                    for (int i = 0; i < fileNames.length; i++) {
                        readData(readFile(fileNames[i]));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Valor inválido. Renicie o programa.");
                break;
        }
        menuScanner.close();
    }

    public static List<String> readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        List<String> lines = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                // se a linha é um comentário, ignorar
                continue;
            } else {
                String[] tokens = line.split("\\s+");
                if (tokens.length > 0) {
                    lines.add(line);
                }
            }
        }

        reader.close();
        return lines;
    }

    public static HashMap<String, Integer> readData(List<String> lines) {
        HashMap<String, Integer> variables = new HashMap<>();
        // variável para indicar se estamos dentro da seção .data
        boolean inDataSection = false;

        // percorre as linhas do arquivo
        for (String line : lines) {
            if (line.contains(":")) {
                variables.put(line.replaceAll(":", ""), lines.indexOf(line));
            }
            // se encontrarmos a linha que inicia a seção .data, setamos a flag
            if (line.trim().startsWith(".data")) {
                inDataSection = true;
                continue;
            }
            // se encontrarmos a linha que finaliza a seção .enddata, setamos a flag
            if (line.trim().startsWith(".enddata")) {
                inDataSection = false;
                break;
            }
            // se estivermos dentro da seção .data, buscamos o nome e o valor da variável
            if (inDataSection) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 2 && tokens[1].matches("\\d+")) {
                    variables.put(tokens[0], Integer.parseInt(tokens[1]));
                }
            }
        }
        return variables;
    }
}
