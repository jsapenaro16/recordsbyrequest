
/*
 * The purpose of the MySQLManager class is to establish a SSH connection to the remote development server
 * and to also establish a connection to the MySQL database via localhost.
 * 
 * JSch is a pure Java implementation of SSH2.
 * JSch allows you to connect to an sshd server and use port forwarding, X11 forwarding, file transfer, etc., 
 * and you can integrate its functionality into your own Java programs. JSch is licensed under BSD style license.
 */

import java.sql.*;

public class MySQLManager {

	private final String DB_USER = "";
	private final String DB_PASS = "";

	private final String HOST = "136.63.192.54";
	private final int PORT = 3306;

	private final String DB_NAME = "records_by_request";
	private final String DB_TABLE_NAME = "requests";

	private Connection connection = null;
	private DatabaseMetaData metadata;
	public MySQLManager mng = null;
	
	
	public MySQLManager(){
		
	}

	public static void main(String[] args) {
		MySQLManager mng = new MySQLManager();

		mng.connect();
	}

	private void connect() {
		// Build the database connection URL.
		StringBuilder url = new StringBuilder("jdbc:mysql://136.63.192.54:");
		url.append(PORT).append("/").append(DB_NAME);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url.toString(), DB_USER, DB_PASS);
			System.out.println("Connected to database!");
			DatabaseMetaData metadata = connection.getMetaData();
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private String buildConnectionURL() {
		StringBuilder url = new StringBuilder("jdbc:mysql://");
		url.append(HOST + ':').append('/').append(DB_NAME);

		return url.toString();
	}
	// InstantiationException
	// ClassNotFoundException
	// SQLException
	/*
	 * String[] tableType = { "TABLE", "VIEW" }; ResultSet tables =
	 * metadata.getTables(null, null, "%", tableType); String tableName; while
	 * (tables.next()) { tableName = tables.getString(3);
	 * 
	 * // Get the columns from this table ResultSet columns =
	 * metadata.getColumns(null, tableName, null, null);
	 * 
	 * String columnName; int dataType; while (columns.next()) { columnName =
	 * columns.getString(4); dataType = columns.getInt(5);
	 * System.out.println(columnName); } } } catch (InstantiationException |
	 * IllegalAccessException | ClassNotFoundException | SQLException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 */

}
