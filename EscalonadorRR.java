import java.util.*;

public class EscalonadorRR implements Escalonador {

    private List<PCB> readyQueue;
    private List<PCB> waitQueue;
    private int timeSlice;

    public void EscalonadorRoundRobin(int timeSlice) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o valor do quantum: ");
        this.timeSlice = sc.nextInt();
        readyQueue = new ArrayList<>();
        waitQueue = new ArrayList<>();
        sc.close();
    }

    public boolean isDone(){
        return !readyQueue.isEmpty() && !waitQueue.isEmpty();
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

            // Executa o processo com um quantum definido
            currentProcess = readyQueue.remove(0);
            int quantum = timeSlice;
            while (quantum > 0 && currentProcess.getRemainingTime() > 0) {
                currentProcess.executeInstructions(currentProcess.getInstructions().get(currentProcess.getPc()));
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                quantum--;

                // Se o quantum acabar, coloca o processo no final da lista de processos prontos
                if (quantum == 0 && currentProcess.getRemainingTime() > 0) {
                    readyQueue.add(currentProcess);
                }
            }

            // Adiciona processos que chegaram durante a execução do processo atual à lista
            // de processos prontos
            List<PCB> arrivedProcessesDuringExecution = new ArrayList<>();
            for (PCB process : waitQueue) {
                if (process.getArrivalTime() <= currentTime) {
                    arrivedProcessesDuringExecution.add(process);
                }
            }
            waitQueue.removeAll(arrivedProcessesDuringExecution);
            readyQueue.addAll(arrivedProcessesDuringExecution);
        }
    }
    public void imprimir() {

    }

}
