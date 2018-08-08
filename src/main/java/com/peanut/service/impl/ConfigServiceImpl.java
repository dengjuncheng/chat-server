package com.peanut.service.impl;

import com.peanut.service.ConfigService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{
        @Override
        public String readFile(String path) throws IOException{
            StringBuilder builder = new StringBuilder();
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
                BufferedReader bfReader = new BufferedReader(reader);

                String tmpContent = null;
                while ((tmpContent = bfReader.readLine()) != null) {
                    builder.append(tmpContent);
                }
                bfReader.close();
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
            }
            return filter(builder.toString());
        }

        private String filter(String input) {
            return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
        }
}
