package app.utils.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Eldair F. Dornelles
 */
public class ConnectionFactory {

    public Connection getConnection() throws DBException {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("url");
            return DriverManager.getConnection(url, props);
        } catch (SQLException ex) {
            throw new DBException("Não foi possível estabelecer conexão com o banco de dados: " + ex.getMessage());
        }
    }

    private Properties loadProperties() throws DBException {
        try (FileInputStream fs = new FileInputStream("src/app/utils/db/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException ex) {
            throw new DBException("Erro ao carregar o arquivo de propriedades do banco de dados: " + ex.getMessage());
        }
    }
}