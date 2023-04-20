import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        String currDir = System.getProperty("user.dir");

        String[] fileNames = {
                currDir + File.separator + "ex_pgms_tp1" + File.separator + "prog1.txt",
                currDir + File.separator + "ex_pgms_tp1" + File.separator + "prog2.txt",
                currDir + File.separator + "ex_pgms_tp1" + File.separator + "prog3.txt"
        };

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

    public static void HandleExecution(Escalonador e) {
        int option;
        while(!e.isDone()){
            System.out.println("Escolha o que fazer:");
            System.out.println("1. rodar um ciclo");
            System.out.println("2. imprimir informações");
            Scanner menuScanner = new Scanner(System.in);
            option = menuScanner.nextInt();
            switch (option) {
                case 1:
                    e.run();
                    break;
                case 2:
                    e.imprimir();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
        System.out.println("Acabaram os programas");
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
