import java.sql.*;

public class MySQLManager {

	private static final String DB_USER = "";
	private static final String DB_PASS = "";

	private static final String HOST = ""; // 136.63.192.54
	private static final int PORT = 3306;

	protected static final String DB_NAME = "records_by_request";
	protected static final String DB_TABLE_NAME = "requests";

	protected static Connection connection = null;

	protected static DatabaseMetaData metaData = null;

	public static Database database = null;
	
	
	public static void main(String[] args) {
		connect(DB_USER, DB_PASS);
		database = new Database();
		database.executeQuery("SELECT * FROM " + DB_TABLE_NAME);
		GUI.createNewGUI();
	}

	private static void connect(String db_user, String db_pass) {
		String connectionURL = buildConnectionURL(HOST, PORT, DB_NAME);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, DB_USER,
					DB_PASS);
			metaData = connection.getMetaData();

			System.out.printf("Connected to %s:%d:%s %n", HOST, PORT, DB_NAME);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static String buildConnectionURL(String host, int port,
			String db_name) {
		StringBuilder URL = new StringBuilder("jdbc:mysql://");
		URL.append(host + ':').append(port).append('/').append(db_name);

		return URL.toString();
	}

}
