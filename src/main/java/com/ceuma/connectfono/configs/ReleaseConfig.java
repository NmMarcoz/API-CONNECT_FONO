package com.ceuma.connectfono.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@Profile("release")
public class ReleaseConfig {
    @Bean
    public DataSource dataSource() {
        String dbPath = determineDatabasePath();
        createDatabaseDirectory(dbPath);

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + dbPath + "/fono.db");
        return dataSource;
    }

    private String determineDatabasePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String home = System.getProperty("user.home");

        if (os.contains("win")) {
            return System.getenv("LOCALAPPDATA") + "\\fono-care\\database";
        } else if (os.contains("mac")) {
            return home + "/Library/Application Support/fono-care/database";
        } else {
            // Linux e outros Unix-like
            return home + "/.local/share/fono-care/database";
        }
    }

    private void createDatabaseDirectory(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create database directory", e);
        }
    }
}
