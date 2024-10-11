import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите, что запустить:");
        System.out.println("1. UDP Server");
        System.out.println("2. UDP Intermediate Client and TCP Intermediate Server");
        System.out.println("3. TCP Final Client (GUI)");

        int choice = scanner.nextInt();
        try {
            switch (choice) {
                case 1:
                    server.UDPServer.main(null);
                    break;
                case 2:
                    intermediate.IntermediateServer.main(null);
                    break;
                case 3:
                    client.TCPFinalClientGUI.main(null);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}