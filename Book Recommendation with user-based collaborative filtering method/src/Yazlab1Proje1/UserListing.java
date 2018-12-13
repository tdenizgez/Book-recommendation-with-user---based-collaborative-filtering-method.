package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserListing extends JFrame {

	private JPanel contentPane;
	DefaultTableModel tableModel = new DefaultTableModel();
	String colums[] = {"Book ID","ISBN","Book Title","Book Author","Year Of Publication","Publisher","Rating"};
	Object rows[] = new Object[7];
	private JTable table_1;
	JLabel lblAfterTheChoosing;
	public ImageIcon Picture() throws SQLException, IOException {
		LoginScreen.sql = "select urlL from yazlab.book where bookID = "+Integer.parseInt(table_1.getValueAt(table_1.getSelectedRow(),0).toString())+";";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		String urlL=null;
		LoginScreen.rs.next();
		
			urlL= LoginScreen.rs.getString("urlL");
			System.out.println(urlL);
		 	URL url = new URL(urlL);
			Image image = ImageIO.read(url);
			return new ImageIcon(image);

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserListing frame = new UserListing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public UserListing() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 607);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.userPanelFrame.setVisible(true);
				LoginScreen.userListingFrame.setVisible(false);
			}
		});
		btnBack.setBounds(809, 540, 114, 25);
		contentPane.add(btnBack);
		
		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				try {
					lblAfterTheChoosing.setIcon(Picture());
					lblAfterTheChoosing.setText("");
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnChoose.setBounds(454, 8, 114, 25);
		contentPane.add(btnChoose);
		
		JButton btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
	                try {
	                	LoginScreen.OpenPdf(table_1.getSelectedRow()%10);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		btnRead.setBounds(809, 8, 114, 25);
		contentPane.add(btnRead);
		
		 lblAfterTheChoosing = new JLabel("After the choosing book press choose button");
		lblAfterTheChoosing.setBounds(464, 45, 415, 483);
		contentPane.add(lblAfterTheChoosing);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 430, 553);
		contentPane.add(scrollPane);
		


		
		table_1 = new JTable();
		table_1.setBounds(52, 146, 289, 193);
		//contentPane.add(table_1);
		
		tableModel.setColumnIdentifiers(colums);
		
		LoginScreen.sql = "select bookID,ISBN,bookTitle,bookAuthor,yearOfPublication,publisher,bookRating from yazlab.book;";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		
		while(LoginScreen.rs.next()) {
			rows[0]=LoginScreen.rs.getObject("bookID");
			rows[1]=LoginScreen.rs.getObject("ISBN");
			rows[2]=LoginScreen.rs.getObject("bookTitle");
			rows[3]=LoginScreen.rs.getObject("bookAuthor");
			rows[4]=LoginScreen.rs.getObject("yearOfPublication");
			rows[5]=LoginScreen.rs.getObject("publisher");
			rows[6]=LoginScreen.rs.getObject("bookRating");
			tableModel.addRow(rows);
		}
		

		table_1.setModel(tableModel);
		

		scrollPane.setViewportView(table_1);
		
		//contentPane.add(table);
	}
}
