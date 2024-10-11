package intermediate;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

public class IntermediateServer {
    private static final String GROUP_ADDRESS = "233.0.0.1";
    private static final int UDP_PORT = 1502;
    private static final int TCP_PORT = 6000;
    private static String lastMessage = "";
    private static Queue<String> lastFiveMessages = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Thread udpThread = new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(UDP_PORT)) {
                InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
                socket.joinGroup(group);
                byte[] buffer = new byte[256];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String receivedMessage = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                    if (!receivedMessage.equals(lastMessage)) {
                        System.out.println("New message: " + receivedMessage);
                        lastMessage = receivedMessage;
                        addMessageToQueue(receivedMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        udpThread.start();

        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            System.out.println("Intermediate server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        }
    }

    private static void addMessageToQueue(String message) {
        if (lastFiveMessages.size() >= 5) {
            lastFiveMessages.poll();
        }
        lastFiveMessages.add(message);
    }

    public static String getLastMessage() {
        return lastMessage;
    }

    private static void handleClient(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String lastMessage = getLastMessage();
            out.println(lastMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}