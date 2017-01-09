import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Database extends MySQLManager {

	private String currentQuery = null;
	private String currentKeyword = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	protected Vector<String> visibleColumnNames = new Vector<String>();
	protected ArrayList<String> columnNames = getColumnNames();

	public Database(String[] visibleColumnNames) {
		Collections.addAll(this.visibleColumnNames, visibleColumnNames);
	}

	protected void executeSearchQuery(String keyword) {
		currentKeyword = keyword;
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM ").append(DB_TABLE_NAME).append(" WHERE ");
		for (int i = 0; i < columnNames.size(); i++) {
			searchQuery.append(columnNames.get(i) + " LIKE '%" + keyword + "%'");

			if (i != (columnNames.size() - 1)) {
				searchQuery.append(" OR ");
			}
		}

		try {
			System.out.println(searchQuery.toString());
			statement = connection.createStatement();
			resultSet = statement.executeQuery(searchQuery.toString());

			mainGUI.tableModel.setRowCount(0);
			List<Row> rows = new ArrayList<Row>();
			while (resultSet.next()) {
				String[] information = new String[17];
				for (int i = 0; i < 17; i++) {
					information[i] = resultSet.getString(i + 1);
				}
				rows.add(new Row(information));

			}
			for (Row row : rows) {
				row.printRows();
				mainGUI.tableModel.addRow(row.visibleRowData);
			}

			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void refresh() {
		System.out.println("Refresh method invoked.");
		executeSearchQuery(currentKeyword);
	}

	private ArrayList<String> getColumnNames() {

		ArrayList<String> columnNames = new ArrayList<String>();

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("DESCRIBE " + DB_TABLE_NAME);

			while (resultSet.next()) {
				columnNames.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNames;
	}
}
