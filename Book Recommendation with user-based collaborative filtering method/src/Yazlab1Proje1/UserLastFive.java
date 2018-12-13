package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserLastFive extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public Image image=null;
	public static JLabel label;
	DefaultTableModel tableModel = new DefaultTableModel();
	String colums[] = {"Book ID","ISBN","Book Title","Book Author","Year Of Publication","Publisher","Rating"};
	Object rows[] = new Object[7];
	
	public void LoadData() throws SQLException {
		tableModel=new DefaultTableModel();
		tableModel.setColumnIdentifiers(colums);
		LoginScreen.sql = "select bookID,ISBN,bookTitle,bookAuthor,yearOfPublication,publisher,bookRating from yazlab.book order by(bookID) desc LIMIT 5;";
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
		
		
		table = new JTable();
		table.setModel(tableModel);
		table.setBounds(66, 212, 219, 181);
		
	}
	
	
	public ImageIcon Picture() throws SQLException, IOException {
		LoginScreen.sql = "select urlM from yazlab.book where bookID = "+(Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()))+";";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		String urlM=null;
		LoginScreen.rs.next();
		
			urlM= LoginScreen.rs.getString("urlM");
			System.out.println(urlM);
		 	URL url = new URL(urlM);
			Image image = ImageIO.read(url);
			return new ImageIcon(image);

	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLastFive frame = new UserLastFive();
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
	public UserLastFive() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.userPanelFrame.setVisible(true);
				LoginScreen.userLastFiveFrame.setVisible(false);
			}
		});
		btnBack.setBounds(266, 154, 114, 25);
		contentPane.add(btnBack);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoadData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnReload.setBounds(139, 154, 114, 25);
		contentPane.add(btnReload);
		
		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					label.setIcon(Picture());
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnChoose.setBounds(13, 154, 114, 25);
		contentPane.add(btnChoose);
		
		label = new JLabel("");
		label.setBounds(597, 12, 170, 203);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 567, 130);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(41, 285, 243, 192);
		

		tableModel.setColumnIdentifiers(colums);
		LoginScreen.sql = "select bookID,ISBN,bookTitle,bookAuthor,yearOfPublication,publisher,bookRating from yazlab.book order by(bookID) desc LIMIT 5;";
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
		
		
		table = new JTable();
		table.setModel(tableModel);
		table.setBounds(66, 212, 219, 181);
		

		scrollPane.setViewportView(table);
		
	}
}
