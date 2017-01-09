import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.prompt.PromptSupport;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	private JLabel logoLabel;
	private JTextField searchBar;
	private JButton searchBarButton;
	private JButton refreshButton;
	private JScrollPane tableScrollPane;
	private JTable dataTable;

	protected DefaultTableModel tableModel = new DefaultTableModel(MySQLManager.database.visibleColumnNames, 0);

	public MainGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
				initComponents();
			}
		});
	}

	private void initComponents() {

		logoLabel = new JLabel();
		logoLabel.setIcon(new ImageIcon("opus_logo.png"));

		searchBar = new JTextField();
		searchBar.addFocusListener(searchFocus);
		searchBar.addActionListener(searchAction);
		PromptSupport.setPrompt("Search . . .", searchBar);

		searchBarButton = new JButton();
		searchBarButton.setText("OK");
		searchBarButton.addActionListener(searchAction);

		refreshButton = new JButton();
		refreshButton.setIcon(new ImageIcon("refresh_image.png"));
		refreshButton.addActionListener(refreshAction);

		dataTable = new JTable();
		dataTable.setModel(tableModel);
		dataTable.changeSelection(0, 0, false, false);
		dataTable.setDefaultEditor(Object.class, null);
		dataTable.setAutoCreateRowSorter(true);
		dataTable.registerKeyboardAction(enterPressed, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		dataTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					System.out.println("Double Mouse Click");
					newEditorGUI();
				}
			}
		});

		tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(dataTable);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(new Dimension(1000, 600));
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("opus_logo_small.png"));
		setTitle("Records By Request Database");
		initLayout();
		setVisible(true);

		dataTable.requestFocus();
	}

	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap()
								.addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(searchBarButton, GroupLayout.PREFERRED_SIZE, 55,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(0, 28, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(searchBar, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(searchBarButton).addComponent(refreshButton)))
										.addGroup(layout.createSequentialGroup()
												.addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 47,
														GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(tableScrollPane,
										GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
								.addGap(14, 14, 14)));

		pack();

	}

	private FocusListener searchFocus = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) {
			searchBar.setText(null);
		}

		@Override
		public void focusLost(FocusEvent e) {
			// do nothing
		}

	};

	private AbstractAction searchAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MySQLManager.database.executeSearchQuery(searchBar.getText());
			dataTable.requestFocus();
		}

	};

	private AbstractAction refreshAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MySQLManager.database.refresh();
			dataTable.requestFocus();
		}

	};

	private AbstractAction enterPressed = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Enter Pressed");
			newEditorGUI();
		}

	};

	private void newEditorGUI() {
		System.out.println("New Editor GUI");
		new EditorGUI();
	}
}
