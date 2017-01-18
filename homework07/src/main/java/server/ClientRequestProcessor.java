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
        // �� ������ ������� ���� ����� �������� ������
        InputStream is = socket.getInputStream();
        // � ������ �� - ����� ������ �� ������� � �������
        OutputStream os = socket.getOutputStream();

        // ������ ������ � 64 ���������
        byte buf[] = new byte[64 * 1024];
        // ������ 64�� �� �������, ��������� - ���-�� ������� �������� ������
        int r = is.read(buf);

        // ������ ������, ���������� ���������� �� ������� ����������
        String data = new String(buf, 0, r);

        // ��������� ������ �� ������ ������:
        data = "" + num + ": " + "\n" + data;

        // ������� ������:
        os.write(data.getBytes());

        // ��������� ����������
        socket.close();
    }
}
