package threads;

import connection.Request;
import connection.Server;
import utils.ServerLogger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Receiver extends Thread{
    private ExecutorService executor;
    private DatagramSocket socket;
    private Interpreter interpreter;
    private class TaskReceiver implements Runnable {
        private DatagramSocket socket;
        private Interpreter interpreter;

        public TaskReceiver(DatagramSocket socket, Interpreter interpreter) {
            this.socket = socket;
            this.interpreter = interpreter;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    byte[] bytes = new byte[Server.bufferSize];
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                    socket.receive(packet);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Request request = (Request) objectInputStream.readObject();
                    ServerLogger.getLogger().info("Получено сообщение от " + packet.getAddress() + " : " + packet.getPort() + " - " + request.getCommand().getCommandName());
                    interpreter.putRequest(request, packet);
                    byteArrayInputStream.close();
                    objectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    ServerLogger.getLogger().warning("Невозможно принять сообщение");
                }
            }
        }

    }

    public Receiver(DatagramSocket socket, Interpreter interpreter){
        executor = Executors.newCachedThreadPool();
        this.socket = socket;
        this.interpreter = interpreter;
    }
    @Override
    public void run(){
        TaskReceiver task = new TaskReceiver(this.socket, this.interpreter);
        executor.execute(task);
    }
}
