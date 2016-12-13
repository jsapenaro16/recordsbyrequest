import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Database extends MySQLManager {

	// holds all of the columns from the database
	private String[] dbColumns = new String[getColumnCount()];
	// holds the columns that will be displayed on the table
	private String[] tableColumns = { "Project,", "Status", "Date Required",
			"Time Required", "Date Submitted", "Request ID" };
	// holds the rows from the current SQL query
	private Object[][] tableRows;

	public Database() {
	//	fillDBColumns();
	//	printDBColumns();
		fillTableColumns("SELECT * FROM requests");
	}

	//executes specified query
	private void fillTableColumns(String query) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			int rowCount = 0;
			
			while(resultSet.next()){
				String project = resultSet.getString(5);
				String status = resultSet.getString(6);
				String dateRequired = resultSet.getString(2);
				String timeRequired = resultSet.getString(3);
				String dateSubmitted = resultSet.getString(4);
				String requestID = resultSet.getString(1);
				String[] rowData = {project, status, dateRequired, timeRequired, dateSubmitted, requestID};
				
				
				for(int rowIndex = 0; rowIndex < 5; rowIndex++){
					tableRows[rowCount][rowIndex] = rowData[rowIndex];
				}
				
				for(String cellData : rowData){
					System.out.println(cellData);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns the amount of columns in the specified table
	private int getColumnCount() {
		int numberOfColumns = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("DESCRIBE "
					+ DB_TABLE_NAME);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				numberOfColumns++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfColumns;

	}

	// loop through dbColumns array and print value of each index
	private void printDBColumns() {
		for (String column : dbColumns) {
			System.out.println(column);
		}

	}

	// fills the dbColumns array (every single column from database)
	private void fillDBColumns() {

		try {
			String[] tableType = { "TABLE", "VIEW" };
			ResultSet tables = metaData.getTables(null, null, "%", tableType);
			String tableName;

			while (tables.next()) {
				tableName = tables.getString(1);
				metaData.getColumns(null, tableName, null, null);

				String columnName;
				int dataType;

				ResultSet columns = metaData.getColumns(null, tableName, null,
						null);

				int i = 0;

				while (columns.next()) {
					columnName = columns.getString(4);
					dataType = columns.getInt(5);
					dbColumns[i] = columnName;
					i++;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
