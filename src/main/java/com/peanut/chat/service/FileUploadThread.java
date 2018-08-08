package com.peanut.chat.service;

import com.peanut.chat.constants.ChatConstants;
import com.peanut.chat.pojo.vo.UploadVo;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;


public class FileUploadThread extends Thread{
    private Socket socket;
    private ObjectMapper objectMapper = new ObjectMapper();

    public FileUploadThread(Socket socket) throws IOException {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(30000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true){
                int length = 1024;
                byte[] buffer = new byte[length];
                int byteLength = in.read(buffer);
                System.out.println(byteLength);
                if(socket.isClosed() || byteLength == -1){
                    socket.close();
                    break;
                }
                String strBuffer = new String(buffer, 0, byteLength);
                System.out.println(strBuffer);
                UploadVo uploadVo = objectMapper.readValue(strBuffer, UploadVo.class);
                String fileName = uploadVo.getFileName();
                out.write("ok".getBytes());
                if(StringUtils.isEmpty(fileName)){
                    System.out.println("未读取到文件名，退出线程");
                }
                File file = new File(ChatConstants.tomPath + uploadVo.getUserId());
                File file2 = new File(ChatConstants.filePath + uploadVo.getUserId());
                System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());;
                if(!file.exists()){
                    file.mkdirs();
                    file2.mkdirs();
                    System.out.println("创建用户信息图片路径");
                }
                //file = new File(ChatConstants.filePath + uploadVo.getUserId() + "/" + fileName);
                System.out.println(file.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(file.getPath() + File.separator + fileName);
                FileOutputStream fos2 = new FileOutputStream(file2.getPath() + File.separator + fileName);
                byte[] fileBuffer = new byte[1024];
                int len;
                while((len = in.read(fileBuffer)) != 0){
                    byte[] fileTemp = new byte[1024];
                    System.arraycopy(fileBuffer,0,fileTemp,0,fileBuffer.length);
                    fos.write(fileBuffer, 0 ,len);
                    fos2.write(fileTemp, 0 ,len);
                    if(len != fileBuffer.length){
                        break;
                    }
                }

                fos.flush();
                fos2.flush();
                fos2.close();
                out.write(objectMapper.writeValueAsBytes(uploadVo));
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
