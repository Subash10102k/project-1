package shop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Label;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Window.Type;
import javax.swing.JScrollBar;

public class BOOKies {

	private JFrame frame;
	private JTextField textBname;
	private JTextField textEdition;
	private JTextField textPrice;
	private JTable table;
	private JTextField textBookID;
	private JTextField textBID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BOOKies window = new BOOKies();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BOOKies() {
		connection();
		initialize();
		tableLoad();
	}

	Connection conn;
	PreparedStatement pstmnt;
	ResultSet rs;

	private void connection() {

		String url = "jdbc:mysql://localhost:3306/bookies";
		String user = "root";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded");

			conn = DriverManager.getConnection(url, user, password);
			System.out.println("connection established");

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	void tableLoad() {
		try {
			pstmnt = conn.prepareStatement("select * from bookies");
			rs = pstmnt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e2) {
			System.out.println(e2);

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 165, 0));
		frame.getContentPane().setBackground(SystemColor.controlShadow);
		frame.setBounds(100, 100, 897, 544);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Label label = new Label("BOoKies");
		label.setBounds(430, 10, 123, 39);
		label.setFont(new Font("Harlow Solid Italic", Font.BOLD | Font.ITALIC, 26));
		frame.getContentPane().add(label);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlShadow);
		panel.setBounds(21, 68, 337, 203);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Book Details", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Book name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(21, 71, 102, 22);
		panel.add(lblNewLabel);

		JLabel lblAuthorName = new JLabel("Edition");
		lblAuthorName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAuthorName.setBounds(21, 117, 102, 22);
		panel.add(lblAuthorName);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(21, 163, 102, 22);
		panel.add(lblPrice);

		textBname = new JTextField();
		textBname.setBounds(133, 74, 159, 20);
		panel.add(textBname);
		textBname.setColumns(10);

		textEdition = new JTextField();
		textEdition.setColumns(10);
		textEdition.setBounds(133, 120, 159, 20);
		panel.add(textEdition);

		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(133, 166, 159, 20);
		panel.add(textPrice);

		JLabel lblBookId_1 = new JLabel("Book  ID");
		lblBookId_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBookId_1.setBounds(21, 28, 102, 22);
		panel.add(lblBookId_1);

		textBID = new JTextField();
		textBID.setColumns(10);
		textBID.setBounds(133, 31, 159, 20);
		panel.add(textBID);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(369, 73, 502, 290);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnSave = new JButton("Save ");
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		Image img = new ImageIcon(this.getClass().getResource("/save-icon.png")).getImage();
		Image nimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		btnSave.setIcon(new ImageIcon(nimg));

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, BID;

				BID = textBID.getText();
				bname = textBname.getText();

				edition = textEdition.getText();

				price = textPrice.getText();

				try {
					pstmnt = conn
							.prepareStatement("insert into bookies(book_id,book_name,edition,price)values(?,?,?,?)");
					pstmnt.setString(1, BID);
					pstmnt.setString(2, bname);
					pstmnt.setString(3, edition);
					pstmnt.setString(4, price);
					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added.!");

					tableLoad();
					textBID.setText("");
					textBname.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBID.requestFocus();

				} catch (Exception e1) {
					System.out.println(e1);

				}

			}
		});
		btnSave.setBounds(21, 299, 110, 45);
		frame.getContentPane().add(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnExit = new JButton("Exit");
		Image img4 = new ImageIcon(this.getClass().getResource("/Users-Exit-icon.png")).getImage();
		Image nimg4 = img4.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		btnExit.setIcon(new ImageIcon(nimg4));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		btnExit.setBounds(140, 299, 98, 45);
		frame.getContentPane().add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnClear = new JButton("Clear");
		Image img1 = new ImageIcon(this.getClass().getResource("/cell-clear-icon.png")).getImage();
		btnClear.setIcon(new ImageIcon(img1));
		
		btnClear.setBounds(248, 299, 110, 45);
		frame.getContentPane().add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textBID.setText("");
				textBname.setText("");
				textEdition.setText("");
				textPrice.setText("");
				textBookID.setText("");
				textBID.requestFocus();

			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.controlShadow);
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(21, 369, 337, 56);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBackground(SystemColor.windowBorder);
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBookId.setBounds(10, 23, 102, 22);
		panel_1.add(lblBookId);

		textBookID = new JTextField();
		textBookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String id = textBookID.getText();

				try {
					pstmnt = conn.prepareStatement(
							"Select book_id , book_name ,edition, price from bookies where book_id = ? ");

					pstmnt.setString(1, id);
					ResultSet rs = pstmnt.executeQuery();

					if (rs.next() == true) {

						String id1 = rs.getString(1);
						String name = rs.getString(2);
						String edition = rs.getString(3);
						String price = rs.getString(4);

						textBID.setText(id1);
						textBname.setText(name);
						textEdition.setText(edition);
						textPrice.setText(price);

					} else {

						textBID.setText("");
						textBname.setText("");
						textEdition.setText("");
						textPrice.setText("");

					}

				} catch (Exception e3) {
					System.out.println(e);
				}
			}
		});
		textBookID.setColumns(10);
		textBookID.setBounds(139, 23, 159, 20);
		panel_1.add(textBookID);

		JButton btnUpdate = new JButton("Update");
		Image img3 = new ImageIcon(this.getClass().getResource("/edit-icon.png")).getImage();
		Image nimg3 = img3.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		btnUpdate.setIcon(new ImageIcon(nimg3));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, bid;
				String id = textBookID.getText();

				bid = textBID.getText();
				bname = textBname.getText();

				edition = textEdition.getText();

				price = textPrice.getText();

				try {
					pstmnt = conn.prepareStatement(
							"update bookies set book_id= ? , book_name= ?,edition= ?,price= ? where book_id= ?");

					pstmnt.setString(1, bid);
					pstmnt.setString(2, bname);
					pstmnt.setString(3, edition);
					pstmnt.setString(4, price);
					pstmnt.setString(5, id);
					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record updated.!");

					tableLoad();
					textBID.setText("");
					textBname.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBookID.setText("");
					textBID.requestFocus();

				} catch (Exception e1) {
					System.out.println(e1);

				}

			}

		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(444, 386, 129, 39);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		Image img5 = new ImageIcon(this.getClass().getResource("/delete.png")).getImage();
		Image nimg5  = img5.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		btnDelete.setIcon(new ImageIcon(nimg5));
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = textBookID.getText();

				try {
					pstmnt = conn.prepareStatement("delete from bookies where book_id= ?");

					pstmnt.setString(1, id);

					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted.!");

					tableLoad();
					textBID.setText("");
					textBname.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBookID.setText("");
					textBID.requestFocus();

				} catch (Exception e1) {
					System.out.println(e1);

				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(677, 386, 123, 39);
		frame.getContentPane().add(btnDelete);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(369, 0, 55, 56);
		frame.getContentPane().add(lblNewLabel_1);
		Image img2 = new ImageIcon(this.getClass().getResource("/Sketch-Book-icon.png")).getImage();
		Image nimg2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		lblNewLabel_1.setIcon(new ImageIcon(nimg2));

	}
}
