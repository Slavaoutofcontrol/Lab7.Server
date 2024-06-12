package threads;

import connection.Response;
import utils.ServerLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

public class Sender extends Thread{
    private ExecutorService executor;
    private DatagramSocket socket;
    private TaskSender task;

    private class TaskSender implements Runnable {
        private Response response;
        private InetAddress address;
        private int port;
        private DatagramSocket socket;
        private boolean hasNextResponse = false;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public TaskSender(DatagramSocket socket) {
            this.socket = socket;
        }

        public void putResponse(Response response, DatagramPacket packet) {
            this.response = response;
            this.address = packet.getAddress();
            this.port = packet.getPort();
            this.hasNextResponse = true;

        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (hasNextResponse) {
                    send(response, address, port);
                }
            }
        }

        public void send(Response response, InetAddress address, int port) {
            byte[] output;
            try {
                hasNextResponse = false;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(response);
                output = bos.toByteArray();
                ServerLogger.getLogger().log(Level.INFO, "Отправляется результат на %s".formatted(address));
                oos.close();
            } catch (IOException e) {
                output = "Не получилось десериализовать ответ".getBytes();
            }
            try {
                DatagramPacket packet = new DatagramPacket(output, output.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Ошибка при отправке ответа");
            }
        }

    }
    public Sender(DatagramSocket socket){
        executor = Executors.newCachedThreadPool();
        this.socket = socket;
    }
    @Override
    public void run() {
        task = new TaskSender(socket);
        executor.execute(task);
    }

    public void putResponse(Response response, DatagramPacket packet) {
        task.putResponse(response, packet);
    }
}
