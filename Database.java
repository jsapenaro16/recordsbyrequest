import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;

public class Database extends MySQLManager {

	private String currentQuery = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	protected Vector<String> visibleColumnNames = new Vector<String>();

	public Database(String[] visibleColumnNames) {
		Collections.addAll(this.visibleColumnNames, visibleColumnNames);
	}

	protected void executeQuery(String sqlQuery) {
		try {
			currentQuery = sqlQuery;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);

			System.out.printf("SQL Query: %s%n", currentQuery);

			mainGUI.tableModel.setRowCount(0);

			while (resultSet.next()) {
				String project = resultSet.getString(5);
				String status = resultSet.getString(6);
				String dateRequired = resultSet.getString(2);
				String timeRequired = resultSet.getString(3);
				String dateSubmitted = resultSet.getString(4);
				String requestID = resultSet.getString(1);

				Object[] rowData = { project, status, dateRequired, timeRequired, dateSubmitted, requestID };

				mainGUI.tableModel.addRow(rowData);
			}
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void refresh() {
		if (currentQuery != null) {
			System.out.println("Refresh method invoked.");
			executeQuery(currentQuery);
		} else {
			System.out.println("Current query has a value of: null.");
		}
	}
}
