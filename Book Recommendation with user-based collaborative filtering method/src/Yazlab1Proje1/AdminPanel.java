package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminPanel extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 289, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDeleteBook = new JButton("Delete Book");
		btnDeleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginScreen.adminDeleteBookFrame = new AdminDeleteBook();
					LoginScreen.adminPanelFrame.setVisible(false);
					LoginScreen.adminDeleteBookFrame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDeleteBook.setBounds(59, 23, 144, 25);
		contentPane.add(btnDeleteBook);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.adminDeleteUserFrame = new AdminDeleteUser();
				LoginScreen.adminPanelFrame.setVisible(false);
				LoginScreen.adminDeleteUserFrame.setVisible(true);
			}
		});
		btnDeleteUser.setBounds(59, 61, 144, 25);
		contentPane.add(btnDeleteUser);
		
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.adminAddBookFrame =new AdminAddBook();
				LoginScreen.adminPanelFrame.setVisible(false);
				LoginScreen.adminAddBookFrame.setVisible(true);
			}
		});
		btnAddBook.setBounds(59, 99, 144, 25);
		contentPane.add(btnAddBook);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.adminPanelFrame.setVisible(false);
				LoginScreen.frame.setVisible(true);
			}
		});
		btnBack.setBounds(84, 137, 97, 25);
		contentPane.add(btnBack);
	}

}
