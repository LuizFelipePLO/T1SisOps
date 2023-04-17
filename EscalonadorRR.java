import java.util.*;

//MUDAR: Para funcionar está sendo preciso fornecer manualmente o tempo de chegada e execução
public class EscalonadorRR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o valor do quantum: ");
        int quantum = sc.nextInt();

        ArrayList<PCB> processes = new ArrayList<>();
        System.out.print("Informe a quantidade de processos: ");
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.print("Informe o tempo de chegada, o tempo de burst e a prioridade do processo " + i + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            int priority = sc.nextInt();
            processes.add(new PCB(i, priority, arrivalTime, burstTime, 0, 0, 0, 0, 0, null));
        }

        sc.close();

        // escalonamento começa no tempo 0
        int currentTime = 0;
        // usada para contar quantos processos já foram concluídos pelo escalonador
        int completedProcesses = 0;
        // tempo máximo que um processo pode executar antes de ser interrompido e voltar
        // para a fila de espera
        int timeQuantum = quantum;
        // armazena o tempo que cada processo teve que esperar na fila antes de ser
        // executado pelo escalonador.
        int[] waitingTime = new int[n];
        // armazena o tempo dtotal que um processo leva desde a chegada até a conclusão
        // de cada processo
        int[] turnaroundTime = new int[n];
        // armazena o tempo restante de cada processo que ainda não foi concluído
        int[] remainingTime = new int[n];
        // marca os processos que já foram executados pelo escalonador
        boolean[] executed = new boolean[n];
        // Boa Pratica: preenche o array "executed" com o valor false, apesar de ser
        // redundante
        Arrays.fill(executed, false);

        while (completedProcesses < n) {
            // armazena a maior prioridade encontrada até o momento, inicializada como
            // "baixa"
            int highestPriority = 2;
            // armazena o índice do processo que tem a maior prioridade encontrada até o
            // momento
            int highestPriorityProcess = -1;
            // procura pelo processo com maior prioridade que ainda não foi executado
            for (int i = 0; i < n; i++) {
                // percorrendo a lista de processos e verificando se o processo ainda não foi
                // executado
                // também checa se o processo já chegou ao tempo de chegada e se a prioridade do
                // processo é menor que a maior prioridade encontrada até o momento
                if (!executed[i] && processes.get(i).getPriority() < highestPriority
                        && processes.get(i).getArrivalTime() <= currentTime) {
                    highestPriority = processes.get(i).getPriority();
                    highestPriorityProcess = i;
                }
            }

            if (highestPriorityProcess != -1) {
                PCB process = processes.get(highestPriorityProcess);
                // escalonador verifica se o tempo restante do processo é menor ou igual ao
                // tempo máximo definido. Se for, ela é atualizada com o tempo restante do
                // processo.
                if (process.getRemainingTime() <= timeQuantum) {
                    timeQuantum = process.getRemainingTime();
                }
                process.setRemainingTime(process.getRemainingTime() - timeQuantum);
                currentTime += timeQuantum;
                timeQuantum = quantum;
                // Se o tempo restante do processo process for igual a zero, significa que ele
                // foi concluído.
                if (process.getRemainingTime() == 0) {
                    completedProcesses++;
                    int waiting = currentTime - process.getArrivalTime() - process.getBurstTime();
                    waitingTime[process.getId() - 1] = waiting;
                    turnaroundTime[process.getId() - 1] = waiting + process.getBurstTime();
                    executed[highestPriorityProcess] = true;
                    // o processo não foi concluído
                } else {
                    executed[highestPriorityProcess] = false;
                }

                // escalonador percorre todos os processos na fila de espera
                for (int i = 0; i < n; i++) {
                    // verifica se eles não foram executados, se eles chegaram até o tempo atual e
                    // se não são o processo com a maior prioridade
                    if (!executed[i] && processes.get(i).getArrivalTime() <= currentTime
                            && i != highestPriorityProcess) {
                        // tempo de espera do processo é atualizado
                        waitingTime[processes.get(i).getId() - 1] += timeQuantum;
                    }
                }
            } else {
                currentTime++;
            }
        }

        double averageWaitingTime = 0;
        double averageTurnaroundTime = 0;
        // para cada processo, é adicionado ao total de tempo de espera na fila e o
        // total de tempo "turnaround"
        for (int i = 0; i < n; i++) {
            averageWaitingTime += waitingTime[i];
            averageTurnaroundTime += turnaroundTime[i];
        }
        // Obtem a média
        averageWaitingTime /= n;
        averageTurnaroundTime /= n;
        System.out.println("Process\tArrival Time\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            PCB process = processes.get(i);
            System.out.println(
                    process.getId() + "\t\t" + process.getArrivalTime() + "\t\t" + process.getBurstTime() + "\t\t"
                            + process.getPriority() + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }

        System.out.println("\nAverage Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
