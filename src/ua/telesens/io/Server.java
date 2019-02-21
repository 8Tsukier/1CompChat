package ua.telesens.io;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {
        int port = 7777; // случайный порт (может быть любое число от 1025 до 65535)
        // Создаем сокет сервера и привязываем его к вышеуказанному порту:
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Ожидаем клиента...");
            // Сервер ждет подключений:
            Socket socket = ss.accept();
            System.out.println("Первый Клиент нашелся!");
            System.out.println();

            Socket socket2 = ss.accept();
            System.out.println("Второй Клиент нашелся!");
            System.out.println();

            // Получаем входной и выходной потоки сокета
            // Теперь можем получать и отсылать данные клиенту:
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            DataInputStream in2 = new DataInputStream(socket2.getInputStream());
            DataOutputStream out2 = new DataOutputStream(socket2.getOutputStream());

            String line = null;
            while (true) {
                line = in.readUTF(); // ожидаем пока первый клиент пришлет строку текста
                System.out.println("Первый клиент отправил мне строку: " + line);
                System.out.println("Я отправляю ее второму...");
                out2.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста
                out2.flush(); // освобождаем буфер потока
                System.out.println("Ожидаю следующую строку...");
                System.out.println();

                line = in2.readUTF(); //ожидаем пока второй клиент пришлёт строку текста
                System.out.println("Второй клиент отправил мне строку: " + line);
                System.out.println("Я отправляю ее первому...");
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста
                out.flush(); // освобождаем буфер потока
                System.out.println("Ожидаю следующую строку...");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}