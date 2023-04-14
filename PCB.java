import java.util.ArrayList;

public class PCB {
    int id;
    int priority;
    int arrivalTime;
    ArrayList<String> instructions;
    int pc;
    int acc;
    int[] PMEM;

    public PCB(int id, int priority, int arrivalTime, ArrayList<String> instructions, int pc, int acc, int[] PMEM) {
        this.id = id;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.instructions = instructions;
        this.pc = 0;
        this.acc = 0;
        this.PMEM = PMEM;
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
                acc += Integer.parseInt(tokens[1].substring(1));
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
}
