package com.peanut.service;

import java.io.IOException;

public interface ConfigService {
    String readFile(String path) throws IOException;
}
