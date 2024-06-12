package connection;

import collectionManager.CollectionManager;
import dataBase.DbParser;
import threads.Interpreter;
import threads.Receiver;
import threads.Sender;

import utils.ServerLogger;

import java.net.*;
import java.util.Scanner;


public class Server {
    public static final int bufferSize = 1024 * 1024;
    private final int port;
    private DatagramSocket socket;
    private static CollectionManager collectionManager;
    private DbParser dbParser;

    public Server(int port, DbParser parser) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.port = port;
        this.dbParser = parser;
    }

    public void run() throws InterruptedException {
        try {
            ServerLogger.getLogger().info("Сервер запущен на порте " + port);
            try {
                Scanner scanner = new Scanner(System.in);
                Sender sender = new Sender(socket);
                sender.start();
                Interpreter interpreter = new Interpreter(sender, socket);
                interpreter.start();
                Receiver receiver = new Receiver(socket, interpreter);
                receiver.setDaemon(true);
                receiver.start();
                while (true) {
                    interpreter.askCommand(scanner, dbParser);
                }
            } catch (Exception e) {
                ServerLogger.getLogger().warning("Ошибка: " + e.getMessage());
            }
        } finally {
            socket.close();
        }
    }
}