import java.util.List;

public interface Escalonador {
    List<PCB> readyQueue = null;
    List<PCB> waitQueue = null;

    void addProcess(PCB process);
    boolean isDone();
    void run();

    void imprimir();
}
