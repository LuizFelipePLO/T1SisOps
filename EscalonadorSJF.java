import java.util.*;

public class EscalonadorSJF {

    private List<PCB> readyQueue;
    private List<PCB> waitQueue;

    public void SJFScheduler() {
        readyQueue = new ArrayList<>();
        waitQueue = new ArrayList<>();
    }

    public void addProcess(PCB process) {
        if (process.getArrivalTime() <= process.getCurrentTime()) {
            readyQueue.add(process);
        } else {
            waitQueue.add(process);
        }
    }

    public void run() {
        int currentTime = 0;
        PCB currentProcess = null;

        while (!readyQueue.isEmpty() || !waitQueue.isEmpty()) {

            // Verifica se há processos na lista de espera que já chegaram ao tempo atual
            List<PCB> arrivedProcesses = new ArrayList<>();
            for (PCB process : waitQueue) {
                if (process.getArrivalTime() <= currentTime) {
                    arrivedProcesses.add(process);
                }
            }
            waitQueue.removeAll(arrivedProcesses);
            readyQueue.addAll(arrivedProcesses);

            // Se a lista de processos prontos estiver vazia, avança o tempo atual
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Escolhe o processo com o menor tempo de execução restante
            currentProcess = readyQueue.stream().min(Comparator.comparingInt(PCB::getRemainingTime)).get();
            readyQueue.remove(currentProcess);

            // Executa o processo até que seja interrompido por um processo com tempo de
            // execução menor
            int quantum = 1;
            while (quantum > 0 && currentProcess.getRemainingTime() > 0) {
                currentProcess.executeInstructions(currentProcess.getInstructions().get(currentProcess.getPc()));
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                quantum--;

            }
        }
    }
}
