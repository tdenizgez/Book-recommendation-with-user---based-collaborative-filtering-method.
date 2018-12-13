package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminDeleteUser extends JFrame {
	
	
	
	
	public void DeleteUser(String username) throws SQLException {

		LoginScreen.sql = "delete from yazlab.users where (username='"+username+"');";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		System.out.println(LoginScreen.sql);
	}

	private JPanel contentPane;
	private JTable table;
	
	DefaultTableModel tableModel = new DefaultTableModel();
	String colums[] = {"Username","Location","Age"};
	Object rows[] = new Object[3];
	private JButton btnReload;
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteUser frame = new AdminDeleteUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminDeleteUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 27, 426, 441);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(45, 191, 224, 181);
		scrollPane.setViewportView(table);
		//contentPane.add(table);
		
		
		tableModel.setColumnIdentifiers(colums);
		
		try {
			
			LoginScreen.sql = "select username,location,age from yazlab.users;";
			LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
			
			while(LoginScreen.rs.next()) {
				rows[0]=LoginScreen.rs.getObject("username");
				rows[1]=LoginScreen.rs.getObject("location");
				rows[2]=LoginScreen.rs.getObject("age");
				tableModel.addRow(rows);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		table.setModel(tableModel);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DeleteUser(table.getValueAt(table.getSelectedRow(),0).toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(496, 405, 97, 25);
		contentPane.add(btnDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LoginScreen.adminDeleteUserFrame.setVisible(false);
				LoginScreen.adminPanelFrame.setVisible(true);
			}
		});
		btnBack.setBounds(496, 443, 97, 25);
		contentPane.add(btnBack);
		
		btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel = new DefaultTableModel();
				tableModel.setColumnIdentifiers(colums);
				try {
					
					LoginScreen.sql = "select username,location,age from yazlab.users;";
					LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
					
					while(LoginScreen.rs.next()) {
						rows[0]=LoginScreen.rs.getObject("username");
						rows[1]=LoginScreen.rs.getObject("location");
						rows[2]=LoginScreen.rs.getObject("age");
						tableModel.addRow(rows);
					}
				}catch (Exception e1) {
					// TODO: handle exception
				}
				
				
				table.setModel(tableModel);
			}
		});
		btnReload.setBounds(496, 34, 97, 25);
		contentPane.add(btnReload);
	}

}
