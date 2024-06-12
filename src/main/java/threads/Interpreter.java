package threads;

import commands.Command;
import connection.Request;
import connection.Response;
import dataBase.DbParser;
import utils.ServerLogger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Interpreter extends Thread {
    private ExecutorService executor;
    private Sender sender;
    private DatagramSocket socket;
    private TaskInterpreter task;
    private class TaskInterpreter extends Thread {
        private Request request;
        private DatagramSocket socket;
        private DatagramPacket packet;
        private Sender sender;
        private boolean hasNextPacket = false;

        public TaskInterpreter(Sender sender, DatagramSocket socket) {
            this.sender = sender;
            this.socket = socket;
        }

        public void putRequest(Request request, DatagramPacket packet) {
            this.request = request;
            this.packet = packet;
            hasNextPacket = true;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (hasNextPacket) {
                    interpret(request, packet);
                }
            }
        }

        public void interpret(Request request, DatagramPacket packet) {
            hasNextPacket = false;
            Command command = request.getCommand();
            ServerLogger.getLogger().log(Level.INFO, "Получена команда %s от %s".formatted(command.getCommandName().toUpperCase(), packet.getAddress()));
            Response response = command.run();
            sender.putResponse(response, packet);
        }
    }
    public Interpreter(Sender sender, DatagramSocket socket) {
        executor = Executors.newFixedThreadPool(5);
        this.sender = sender;
        this.socket = socket;
    }

    @Override
    public void run() {
        task = new TaskInterpreter(sender, socket);
        executor.execute(task);
    }

    public void putRequest(Request request, DatagramPacket packet) {
        task.putRequest(request, packet);
    }

    public void askCommand(Scanner scanner, DbParser dbParser) {
        try {
            String line = scanner.nextLine();
            if (line.equals("save") || line.equals("s")) {
                dbParser.writeCollection();
            }
            if (line.equals("exit")) {
                dbParser.writeCollection();
                ServerLogger.getLogger().info("Завершаем работу сервера.");
                System.exit(0);
            }
        } catch (NoSuchElementException e) {
            dbParser.writeCollection();
            ServerLogger.getLogger().info("Экстренно завершаем работу сервера.");
            System.exit(0);
        }
    }
}
