package com.upb.agripos.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk membaca environment variables dari file .env
 * Membaca .env file secara manual tanpa external dependency
 */
public class EnvConfig {
    private static final Map<String, String> envMap = new HashMap<>();

    static {
        loadEnvFile();
    }

    /**
     * Load environment variables dari .env file
     */
    private static void loadEnvFile() {
        File envFile = new File(".env");
        
        if (!envFile.exists()) {
            System.out.println("Info: .env file not found, using system environment variables");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(envFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines dan comments
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Parse key=value
                int equalsIndex = line.indexOf('=');
                if (equalsIndex > 0) {
                    String key = line.substring(0, equalsIndex).trim();
                    String value = line.substring(equalsIndex + 1).trim();
                    
                    // Remove quotes if present
                    if ((value.startsWith("\"") && value.endsWith("\"")) ||
                        (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    
                    envMap.put(key, value);
                }
            }
            System.out.println("Successfully loaded " + envMap.size() + " environment variables from .env");
        } catch (IOException e) {
            System.err.println("Warning: Could not read .env file: " + e.getMessage());
        }
    }

    /**
     * Get environment variable value
     * @param key Variable name
     * @return Variable value or null if not found
     */
    public static String get(String key) {
        // First try environment map (from .env file)
        String value = envMap.get(key);
        if (value != null) {
            return value;
        }
        // Then try system environment variables
        return System.getenv(key);
    }

    /**
     * Get environment variable value with default fallback
     * @param key Variable name
     * @param defaultValue Default value if not found
     * @return Variable value or defaultValue
     */
    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    // Database configuration getters
    public static String getDatabaseUrl() {
        return get("DB_URL", "jdbc:postgresql://localhost:5432/agripos_db");
    }

    public static String getDatabaseUsername() {
        return get("DB_USERNAME", "postgres");
    }

    public static String getDatabasePassword() {
        return get("DB_PASSWORD", "postgres");
    }

    public static String getDatabaseDriver() {
        return get("DB_DRIVER", "org.postgresql.Driver");
    }

    // Application configuration getters
    public static String getAppName() {
        return get("APP_NAME", "Agri-POS");
    }

    public static String getAppVersion() {
        return get("APP_VERSION", "1.0-SNAPSHOT");
    }

    public static String getAppEnvironment() {
        return get("APP_ENV", "development");
    }

    // JDBC configuration getters
    public static boolean getJdbcAutoReconnect() {
        return Boolean.parseBoolean(get("JDBC_AUTO_RECONNECT", "true"));
    }

    public static int getJdbcPoolSize() {
        try {
            return Integer.parseInt(get("JDBC_POOL_SIZE", "10"));
        } catch (NumberFormatException e) {
            return 10;
        }
    }
}
