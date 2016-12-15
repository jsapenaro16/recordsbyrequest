import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Database extends MySQLManager {
	
	
	private static String currentQuery = null;
	
	public Database() {
		fillColumnNames();
	}

	private String[] tableColumnNames = { "Project", "Status", "Date Required",
			"Time Required", "Date Submitted", "Request ID" };

	public Vector columnNames = new Vector();

	private void fillColumnNames() {
		for (String columnName : tableColumnNames) {
			columnNames.addElement(columnName);
		}
	}

	protected void executeQuery(String sqlQuery) {
		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			currentQuery = sqlQuery;
			GUI.tableModel.setRowCount(0);
			// ////////////////////////////////////////////////////////////

			while (resultSet.next()) {
				String project = resultSet.getString(5);
				String status = resultSet.getString(6);
				String dateRequired = resultSet.getString(2);
				String timeRequired = resultSet.getString(3);
				String dateSubmitted = resultSet.getString(4);
				String requestID = resultSet.getString(1);
				Object[] rowData = { project, status, dateRequired,
						timeRequired, dateSubmitted, requestID };
				GUI.tableModel.addRow(rowData);
			}
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void refresh() {
		if (currentQuery != null) {
			executeQuery(currentQuery);
			System.out.println("Refresh method called.");
		} else {
			System.out.println("Current query has a value of: null");
		}
	}
}
