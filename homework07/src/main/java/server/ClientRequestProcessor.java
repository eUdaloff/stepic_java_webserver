package server;

import java.io.*;
import java.net.Socket;

public class ClientRequestProcessor extends Thread {

    private static final int BUFFER_SIZE = 4 * 1024;
    private static final String CHARSET_NAME = "utf-8";
    private Socket socket;
    private int num;

    public ClientRequestProcessor(Socket socket, int num) {
        this.socket = socket;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            System.out.println("Request #" + num + " is processing...");
            process();
            System.out.println("Request #" + num + " is processed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process() throws IOException {
        String fromClient = getClientRequest();
        if("Bue".equals(fromClient)) {
            socket.close();
            return;
        }
        sendResponse(fromClient);
        socket.close();
    }

    private void sendResponse(String response) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), CHARSET_NAME)) {
            osw.write(response);
        }
    }

    private String getClientRequest() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream is = socket.getInputStream();
        int r = is.read(buffer);
        String line = new String(buffer, 0, r);
        return line;
    }

    void process2() throws IOException {
        // из сокета клиента берём поток входящих данных
        InputStream is = socket.getInputStream();
        // и оттуда же - поток данных от сервера к клиенту
        OutputStream os = socket.getOutputStream();

        // буффер данных в 64 килобайта
        byte buf[] = new byte[64 * 1024];
        // читаем 64кб от клиента, результат - кол-во реально принятых данных
        int r = is.read(buf);

        // создаём строку, содержащую полученную от клиента информацию
        String data = new String(buf, 0, r);

        // добавляем данные об адресе сокета:
        data = "" + num + ": " + "\n" + data;

        // выводим данные:
        os.write(data.getBytes());

        // завершаем соединение
        socket.close();
    }
}
