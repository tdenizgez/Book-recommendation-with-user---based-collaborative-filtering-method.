package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UserPanel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPanel frame = new UserPanel();
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
	public UserPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 339, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnFavouritebook = new JButton("FavouriteBook");
		btnFavouritebook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				LoginScreen.userFavouriteBooksFrame = new UserFavouriteBooks();
				LoginScreen.userFavouriteBooksFrame.setVisible(true);
				LoginScreen.userPanelFrame.setVisible(false);
			}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		btnFavouritebook.setBounds(67, 12, 167, 25);
		contentPane.add(btnFavouritebook);
		
		JButton btnPopularBooks = new JButton("Popular Books");
		btnPopularBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					LoginScreen.userPopularBooksFrame = new  UserPopularBooks();
					LoginScreen.userPopularBooksFrame.setVisible(true);
					LoginScreen.userPanelFrame.setVisible(false);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		btnPopularBooks.setBounds(67, 49, 167, 25);
		contentPane.add(btnPopularBooks);
		
		JButton btnListingBooks = new JButton("Listing Books");
		btnListingBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					LoginScreen.userPanelFrame.setVisible(false);
					LoginScreen.userListingFrame = new UserListing();
					LoginScreen.userListingFrame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnListingBooks.setBounds(67, 85, 167, 25);
		contentPane.add(btnListingBooks);
		
		JButton btnSuggestion = new JButton("Suggestion");
		btnSuggestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginScreen.userPanelFrame.setVisible(false);
					try {
						LoginScreen.userSuggestionFrame = new UserSuggestion();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LoginScreen.userSuggestionFrame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSuggestion.setBounds(67, 122, 167, 25);
		contentPane.add(btnSuggestion);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.userPanelFrame.setVisible(false);
				LoginScreen.frame.setVisible(true);
				
			}
		});
		btnBack.setBounds(67, 257, 167, 25);
		contentPane.add(btnBack);
		
		JButton btnLastFive = new JButton("Last Five");
		btnLastFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginScreen.userLastFiveFrame = new UserLastFive();
					LoginScreen.userLastFiveFrame.setVisible(true);
					LoginScreen.userPanelFrame.setVisible(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLastFive.setBounds(67, 162, 167, 25);
		contentPane.add(btnLastFive);
	}
}
