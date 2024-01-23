package util;

import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString() {
        Properties properties = new Properties();
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/cars?useSSL=false&user=root&password=160279&allowPublicKeyRetrieval=true");

        String url = properties.getProperty("db.url", "");

        return url;
    }
}