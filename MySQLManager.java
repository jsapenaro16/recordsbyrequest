
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

	private final String DB_USER = "pmrbr";
	private final String DB_PASS = "!tyler1";
	
	private final String HOST = "localhost";
	private final int PORT = 3306;

	private final String DB_NAME = "records_by_request";
	private final String DB_TABLE_NAME = "requests";

	private final static Logger LOGGER = Logger.getLogger(MySQLManager.class.getName());


	public static void main(String[] args) {
		MySQLManager mng = new MySQLManager();

		mng.connect();
	}

	private void connect() {
		// Build the database connection URL.
		StringBuilder url = new StringBuilder("jdbc:mysql://localhost:");
		url.append(PORT).append("/").append(DB_NAME);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url.toString(), DB_USER, DB_PASS);
			System.out.println("Connected to database!");
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
