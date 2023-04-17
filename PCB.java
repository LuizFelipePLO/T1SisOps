import java.util.ArrayList;

public class PCB {
    private int id;
    private int priority;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int waitingTime;
    private int turnaroundTime;
    private ArrayList<String> instructions;
    private int pc;
    private int acc;
    private int[] PMEM;

    public PCB(int id, int priority, int arrivalTime, int burstTime, int remainingTime, int waitingTime,
            int turnaroundTime, int pc, int acc, int[] pMEM) {
        this.id = id;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = remainingTime;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
        this.pc = pc;
        this.acc = acc;
        PMEM = pMEM;
    }

    private int getIndex(String variable) {
        return Integer.parseInt(variable.substring(1));
    }

    public void executeInstructions(String instruction) {

        String[] tokens = instruction.split("\\s+");
        String opcode = tokens[0];

        switch (opcode) {
            case "load":
                acc = PMEM[getIndex(tokens[1])];
                break;

            case "store":
                PMEM[getIndex(tokens[1])] = acc;
                break;

            case "add":
                acc += Integer.parseInt(tokens[1].substring(1));
                break;

            case "sub":
                acc -= Integer.parseInt(tokens[1].substring(1));
                break;

            case "mult":
                acc *= PMEM[getIndex(tokens[1])];
                break;

            case "div":
                acc /= PMEM[getIndex(tokens[1])];
                break;

            case "BRZERO":
                if (acc == 0) {
                    pc = Integer.parseInt(tokens[1].substring(1)) - 1;
                }
                break;

            case "BRANY":
                pc = Integer.parseInt(tokens[1].substring(1)) - 1;
                break;

            case "BRPOS":
                if (acc > 0) {
                    pc = Integer.parseInt(tokens[1].substring(1)) - 1;
                }
                break;

            case "BRNEG":
                if (acc < 0) {
                    pc = Integer.parseInt(tokens[1].substring(1)) - 1;
                }
                break;

            case "syscall":
                switch (Integer.parseInt(tokens[1])) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        System.out.println(acc);
                        break;
                    case 2:
                        acc = Integer.parseInt(System.console().readLine());
                        break;
                    default:
                        System.err.println("Unknown syscall: " + tokens[1]);
                        break;
                }
                break;

            default:
                System.err.println("Unknown instruction: " + instruction);
                break;
        }
        pc++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int[] getPMEM() {
        return PMEM;
    }

    public void setPMEM(int[] pMEM) {
        PMEM = pMEM;
    }
}
