import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MySQLManager {

	private static final String DB_USER = "";
	private static final String DB_PASS = "";

	private static final String HOST = "localhost"; // 136.63.192.54
	private static final int PORT = 3306;

	private static final String DB_NAME = "records_by_request";
	public static final String DB_TABLE_NAME = "requests";

	protected static Connection connection = null;

	public static MainGUI mainGUI = null;
	public static Database database = null;

	public static void main(String[] args) {
		setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		connect();
		database = new Database(new String[] { "Project", "Status",
				"Date Required", "Time Required", "Date Submitted",
				"Request ID", "Requester First", "Requester Last",
				"Patient First", "Patient Last" });
		mainGUI = new MainGUI();
		database.executeSearchQuery("");
	}

	private static void connect() {
		String connectionURL = buildConnectionURL(HOST, PORT, DB_NAME);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(connectionURL, DB_USER,
					DB_PASS);

			System.out.printf("Connected to %s:%d:%s%n", HOST, PORT, DB_NAME);
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

	private static void setLookAndFeel(String lookAndFeel) {
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

}
