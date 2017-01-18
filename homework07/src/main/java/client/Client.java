package client;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String args[]) {
        try {
            System.out.println("Start");
            // открываем сокет и коннектимся к localhost:3128
            // получаем сокет сервера
            Socket s = new Socket("localhost", 5050);

            // берём поток вывода и выводим туда первый аргумент
            // заданный при вызове
            //PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
            //pw.println(args[0]);
            s.getOutputStream().write(args[0].getBytes());

            // читаем ответ
            byte buf[] = new byte[64 * 1024];
            int r = s.getInputStream().read(buf);
            String data = new String(buf, 0, r);

            // выводим ответ в консоль
            System.out.println(data);
            System.out.println("Finish");
        } catch (Exception e) {
            System.out.println("init error: " + e);
        } // вывод исключений
    }


}
