import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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

	private TableModel tableModel = new DefaultTableModel();

	public EditorGUI() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				initComponents();
			}
		});
	}

	private void initComponents() {

		requestIDLabel = new JLabel();
		requestIDLabel.setText("######");

		databaseEditorLabel = new JLabel();
		databaseEditorLabel.setText("Database Editor");
		databaseEditorLabel.setHorizontalTextPosition(SwingConstants.CENTER);

		dataTable = new JTable();
		// dataTable.setModel(tableModel);
		dataTable
				.setModel(
						new DefaultTableModel(
								new Object[][] { { null, null }, { null, null }, { null, null }, { null, null },
										{ null, null }, { null, null }, { null, null }, { null, null }, { null, null },
										{ null, null }, { null, null }, { null, null }, { null, null }, { null, null },
										{ null, null }, { null, null }, { null, null }, { null, null } },
								new String[] { "Field", "Value" }));

		tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(dataTable);

		cancelButton = new JButton();
		cancelButton.setText("Cancel");

		submitButton = new JButton();
		submitButton.setText("Submit");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(new java.awt.Dimension(300, 405));
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("opus_logo_small.png"));
		setTitle("Records By Request Database Editor");
		initLayout();
		setVisible(true);
	}

	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addGroup(GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addGap(0, 103, Short.MAX_VALUE)
												.addComponent(databaseEditorLabel).addGap(52, 52, 52)
												.addComponent(requestIDLabel))
								.addGroup(GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addComponent(cancelButton)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(submitButton)))
						.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(requestIDLabel, GroupLayout.PREFERRED_SIZE, 14,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(databaseEditorLabel))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(cancelButton).addComponent(submitButton))
										.addGap(16, 16, 16)));
		pack();
	}
}
