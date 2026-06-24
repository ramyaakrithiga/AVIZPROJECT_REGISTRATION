package com.aviz.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton utility that loads and exposes values from config.properties.
 * Thread-safe via class-loading guarantees on the inner enum holder.
 */
public class ConfigReader {

    private final Properties properties = new Properties();

    private ConfigReader() {
        loadProperties();
    }

    /* ── Singleton holder ─────────────────────────────────── */
    private enum Holder {
        INSTANCE;
        private final ConfigReader configReader = new ConfigReader();
    }

    public static ConfigReader getInstance() {
        return Holder.INSTANCE.configReader;
    }

    /* ── Loader ──────────────────────────────────────────────*/
    private void loadProperties() {
        // Try loading from classpath first (works when packaged), then file-system fallback
        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("config.properties");
        if (stream == null) {
            try {
                stream = new FileInputStream("src/test/resources/config.properties");
            } catch (IOException e) {
                throw new RuntimeException("config.properties not found on classpath or filesystem.", e);
            }
        }
        try (InputStream s = stream) {
            properties.load(s);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /* ── Accessors ───────────────────────────────────────────*/
    public String get(String key) {
        String value = System.getProperty(key);          // JVM -D flag overrides file
        if (value == null) {
            value = properties.getProperty(key);
        }
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public String getBrowser() {
        return get("browser", "chrome");
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public int getImplicitWait() {
        return getInt("implicit.wait");
    }

    public int getExplicitWait() {
        return getInt("explicit.wait");
    }

    public int getPageLoadTimeout() {
        return getInt("page.load.timeout");
    }

    public boolean isHeadless() {
        return getBoolean("headless");
    }

    public boolean isScreenshotOnFailure() {
        return getBoolean("screenshot.on.failure");
    }

    public String getReportsOutputDir() {
        return get("reports.output.dir", "target/extent-reports");
    }
}
