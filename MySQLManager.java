
/*
 * The purpose of the MySQLManager class is to establish a SSH connection to the remote development server
 * and to also establish a connection to the MySQL database via localhost.
 * 
 * JSch is a pure Java implementation of SSH2.
 * JSch allows you to connect to an sshd server and use port forwarding, X11 forwarding, file transfer, etc., 
 * and you can integrate its functionality into your own Java programs. JSch is licensed under BSD style license.
 */

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Properties;
import com.jcraft.jsch.*;
import java.sql.*;

public class MySQLManager {

	private final int LOCAL_PORT = 3307;
	private final int REMOTE_PORT = 3306;
	private final String REMOTE_HOST = "66.6.115.212";

	private final String REMOTE_USER = "pmrbr";
	private final String REMOTE_PASS = "!tyler1";

	private final String DB_USER = "pmrbr";
	private final String DB_PASS = "!tyler1";

	private final String DB_NAME = "records_by_request";
	private final String DB_TABLE_NAME = "requests";

	private final static Logger LOGGER = Logger.getLogger(MySQLManager.class.getName());

	private Session session = null;

	public static void main(String[] args) {
		MySQLManager mng = new MySQLManager();

		mng.connect();
	}

	private void connect() {
		int assigned_port;

		JSch jsch = new JSch();
		Session session;
		try {
			session = jsch.getSession(REMOTE_USER, REMOTE_HOST, 22);
			session.setPassword(REMOTE_PASS);
			// Additional SSH options. See your ssh_config manual for more
			// options.
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("Compression", "yes");
			config.put("ConnectionAttempts", "2");
			
			session.setConfig(config);
			session.connect();

			assigned_port = session.setPortForwardingL(LOCAL_PORT, REMOTE_HOST, REMOTE_PORT);
		} catch (JSchException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return;
		}

		if (assigned_port == 0) {
			LOGGER.log(Level.SEVERE, "Port forwarding failed!");
			return;
		}

		// Build the database connection URL.
		StringBuilder url = new StringBuilder("jdbc:mysql://localhost:");

		// Use 'assigned_port' to establish database connection.
		url.append(assigned_port).append("/").append(DB_NAME).append("?user=").append(DB_USER).append("&password=")
				.append(DB_PASS);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url.toString());
			DatabaseMetaData metadata = connection.getMetaData();
			String[] tableType = { "TABLE", "VIEW" };
			ResultSet tables = metadata.getTables(null, null, "%", tableType);
			String tableName;
			while (tables.next()) {
				tableName = tables.getString(3);

				// Get the columns from this table
				ResultSet columns = metadata.getColumns(null, tableName, null, null);

				String columnName;
				int dataType;
				while (columns.next()) {
					columnName = columns.getString(4);
					dataType = columns.getInt(5);
					System.out.println(columnName);
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
