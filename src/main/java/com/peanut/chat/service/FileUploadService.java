package com.peanut.chat.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class FileUploadService {
    private static final int port = 8089;
    private static int flag = 0;
    @PostConstruct
    synchronized void createTcpThread() throws IOException {
        if(flag != 0){
            return;
        }
        flag = 1;
        ServerSocket server = new ServerSocket(port);
        InetAddress ia = InetAddress.getByName(null);

        System.out.println("Server@"+ia+" start!");
        new Thread(() -> {
            try{
                while (true) {
                    Socket socket = server.accept();
                    System.out.println("有新的tcp连接");
                    new FileUploadThread(socket);
                }
            }catch (IOException e){
                try {
                    server.close();
                } catch (IOException e1) {
                    System.out.println("文件上传socket关闭失败");
                }
                System.out.println("文件上传socket建立失败");
            } }).start();
    }
}
