package server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UDPServer {
    private static final String GROUP_ADDRESS = "233.0.0.1";
    private static final int PORT = 1502;
    private static final String MESSAGE_FILE = "weather.txt";
    private static int messageIndex = 0;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<String> messages = readMessagesFromFile();
                    if (messageIndex >= messages.size()) {
                        messageIndex = 0; // Перезапуск с первого сообщения
                    }
                    String message = messages.get(messageIndex);
                    messageIndex++;

                    if (message != null) {
                        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                        socket.send(packet);
                        System.out.println("Message sent: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10000);

    }

    private static List<String> readMessagesFromFile() throws IOException {
        List<String> messages = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            messages.add(line);
        }
        return messages;
    }
}