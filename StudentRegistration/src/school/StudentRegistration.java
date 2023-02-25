package school;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentRegistration {

	private JFrame frame;
	private JTextField txt_name;
	private JTextField txt_no;
	private JTextField txt_course;
	private JTable table;
	private JTextField txt_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRegistration window = new StudentRegistration();
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
	public StudentRegistration() {
		connection();
		initialize();
		tableLoad();
	}
	
	
	Connection conn;
	PreparedStatement pstmnt;
	ResultSet rs;

	private void connection() {

		String url = "jdbc:mysql://localhost:3306/school";
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
			pstmnt = conn.prepareStatement("select * from student");
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
		frame.setBounds(100, 100, 696, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Registration");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 24));
		lblNewLabel.setBounds(233, 21, 225, 34);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 66, 326, 241);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel std_name = new JLabel("StudentName");
		std_name.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		std_name.setBounds(10, 70, 98, 14);
		panel.add(std_name);
		
		JLabel std_no = new JLabel("Mobile Number");
		std_no.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		std_no.setBounds(10, 112, 98, 14);
		panel.add(std_no);
		
		JLabel std_course = new JLabel("Course");
		std_course.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		std_course.setBounds(10, 153, 78, 14);
		panel.add(std_course);
		
		txt_name = new JTextField();
		txt_name.setBounds(140, 68, 156, 20);
		panel.add(txt_name);
		txt_name.setColumns(10);
		
		txt_no = new JTextField();
		txt_no.setColumns(10);
		txt_no.setBounds(140, 110, 156, 20);
		panel.add(txt_no);
		
		txt_course = new JTextField();
		txt_course.setColumns(10);
		txt_course.setBounds(140, 151, 156, 20);
		panel.add(txt_course);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sname, num, course, id;

				id = txt_id.getText();
				sname = txt_name.getText();

				num = txt_no.getText();

				course = txt_course.getText();

				try {
					pstmnt = conn
							.prepareStatement("insert into student(student_id,student_name,mobile_number,course)values(?,?,?,?)");
					pstmnt.setString(1, id);
					pstmnt.setString(2, sname);
					pstmnt.setString(3, num);
					pstmnt.setString(4, course);
					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added.!");

					tableLoad();
					txt_id.setText("");
					txt_name.setText("");
					txt_no.setText("");
					txt_course.setText("");
					txt_id.requestFocus();

				} catch (Exception e1) {
					System.out.println(e1);

				}
				
				
			}
		});
		add.setBounds(10, 189, 89, 23);
		panel.add(add);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				String id = txt_id.getText();

				try {
					pstmnt = conn.prepareStatement("delete from student where student_id= ?");

					pstmnt.setString(1, id);

					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted.!");

					tableLoad();
					txt_id.setText("");
					txt_name.setText("");
					txt_no.setText("");
					txt_course.setText("");
					txt_id.requestFocus();


				} catch (Exception e1) {
					System.out.println(e1);

				}

			}
		});
		delete.setBounds(123, 189, 89, 23);
		panel.add(delete);
		
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				String sname, num, course, id;
				

				id = txt_id.getText();
				sname = txt_name.getText();

				num = txt_no.getText();

				course = txt_course.getText();
				try {
					pstmnt = conn.prepareStatement(
							"update student set student_id= ? , student_name= ?,mobile_number= ?,course= ? where student_id= ?");

					pstmnt.setString(1, id);
					pstmnt.setString(2, sname);
					pstmnt.setString(3, num);
					pstmnt.setString(4, course);
					pstmnt.setString(5, id);

					pstmnt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record updated.!");

					tableLoad();
					txt_id.setText("");
					txt_name.setText("");
					txt_no.setText("");
					txt_course.setText("");
					txt_id.requestFocus();


				} catch (Exception e1) {
					System.out.println(e1);

				}
			}
		});
		edit.setBounds(229, 189, 89, 23);
		panel.add(edit);
		
		JLabel std_id = new JLabel("Student ID");
		std_id.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		std_id.setBounds(10, 33, 98, 14);
		panel.add(std_id);
		
		txt_id = new JTextField();
		txt_id.setColumns(10);
		txt_id.setBounds(140, 31, 156, 20);
		panel.add(txt_id);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 66, 324, 241);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
