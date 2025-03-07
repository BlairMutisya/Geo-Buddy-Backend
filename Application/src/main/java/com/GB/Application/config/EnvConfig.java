package com.GB.Application.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .filename(".env")
            .load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
