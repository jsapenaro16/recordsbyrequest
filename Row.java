import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class EditorGUI extends JFrame {

	private JButton cancelButton;
	private JButton submitButton;
	private JLabel requestIDLabel;
	private JLabel databaseEditorLabel;
	private JScrollPane tableScrollPane;
	private JTable dataTable;

	private Vector<String> columnNames = new Vector<String>();

	private String[] tableColumnOneData = new String[17];
	private String[] tableColumnTwoData = new String[17];

	private DefaultTableModel tableModel;

	private Row selectedRow;

	public EditorGUI(int rowNumber) {
		columnNames.add("Field");
		columnNames.add("Value");

		tableModel = new DefaultTableModel(columnNames, 0);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				selectedRow = MySQLManager.database.rows.get(rowNumber);
				for (int i = 0; i < tableColumnTwoData.length; i++) {
					tableColumnOneData[i] = selectedRow.information[i];
					String[] rowData = new String[2];
					rowData[0] = MySQLManager.database.columnNames.get(i);
					rowData[1] = tableColumnOneData[i] = selectedRow.information[i];
					tableModel.addRow(rowData);
				}
				initComponents();
			}
		});
	}

	private void initComponents() {

		requestIDLabel = new JLabel();
		requestIDLabel.setText(selectedRow.information[0]);

		databaseEditorLabel = new JLabel();
		databaseEditorLabel.setText("Database Editor");
		databaseEditorLabel.setHorizontalTextPosition(SwingConstants.CENTER);

		dataTable = new JTable();
		dataTable.setModel(tableModel);

		tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(dataTable);

		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(cancelPressed);

		submitButton = new JButton();
		submitButton.setText("Submit");
		submitButton.addActionListener(submitPressed);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(new java.awt.Dimension(300, 405));
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("opus_logo_small.png"));
		setTitle("Records By Request Database Editor");
		initLayout();
		setVisible(true);
	}

	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														tableScrollPane,
														GroupLayout.PREFERRED_SIZE,
														0, Short.MAX_VALUE)
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGap(0,
																		103,
																		Short.MAX_VALUE)
																.addComponent(
																		databaseEditorLabel)
																.addGap(52, 52,
																		52)
																.addComponent(
																		requestIDLabel))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		cancelButton)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		submitButton)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														requestIDLabel,
														GroupLayout.PREFERRED_SIZE,
														14,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														databaseEditorLabel))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tableScrollPane,
										GroupLayout.DEFAULT_SIZE, 317,
										Short.MAX_VALUE)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(cancelButton)
												.addComponent(submitButton))
								.addGap(16, 16, 16)));
		pack();
	}

	private AbstractAction submitPressed = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] fieldData = new String[17];
			String[] valueData = new String[17];

			for (int i = 0; i < 17; i++) {
				fieldData[i] = (String) tableModel.getValueAt(i, 0);
			}

			for (int i = 0; i < 17; i++) {
				valueData[i] = (String) tableModel.getValueAt(i, 1);
			}

			MySQLManager.database.performUpdates(valueData,
					selectedRow.information, fieldData);

			MySQLManager.database.refresh();

			dispose();
		}

	};

	private AbstractAction cancelPressed = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	};
}
