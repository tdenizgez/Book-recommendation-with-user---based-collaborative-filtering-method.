package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
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
import javax.swing.JTextField;

public class AdminDeleteBook extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public static JLabel lblForImagePress;
	public Image image=null;
	DefaultTableModel tableModel = new DefaultTableModel();
	String colums[] = {"Book ID","ISBN","Book Title","Book Author","Year Of Publication","Publisher","Rating"};
	Object rows[] = new Object[7];
	private JButton btnDelete;
	private JButton btnBack;
	private JButton btnReload;
	private JButton btnChoose;
	
	public void LoadData() throws SQLException {
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
		
		
		table = new JTable();
		table.setModel(tableModel);
		table.setBounds(66, 212, 219, 181);
		
	}
	
	public void DeleteBook(int bookID) throws SQLException {
		LoginScreen.sql = "delete from yazlab.Book where (bookID="+bookID+");";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		System.out.println(LoginScreen.sql);
		//ISBN PRIMARY KEY DEGIL ANCAK UNIQUE OLARAK DA AYARLANMADI AYNI ISBN ILE KITAP KAYDEDILEBILIYOR
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
					AdminDeleteBook frame = new AdminDeleteBook();
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
	public AdminDeleteBook() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 13, 438, 448);
		contentPane.add(scrollPane);
		
		
		tableModel.setColumnIdentifiers(colums);
		
		//contentPane.add(table);
		LoadData();
		scrollPane.setViewportView(table);
		
		 lblForImagePress = new JLabel("For Image Press Choose Button");
		lblForImagePress.setBounds(481, 70, 313, 325);
		contentPane.add(lblForImagePress);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					DeleteBook(Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
					//LoadData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnDelete.setBounds(590, 9, 97, 25);
		contentPane.add(btnDelete);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.adminDeleteBookFrame.setVisible(false);
				LoginScreen.adminPanelFrame.setVisible(true);
			}
		});
		btnBack.setBounds(676, 436, 97, 25);
		contentPane.add(btnBack);
		
		btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tableModel = new DefaultTableModel();
				tableModel.setColumnIdentifiers(colums);
				try {
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
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				table.setModel(tableModel);
			}
		});
		btnReload.setBounds(699, 9, 97, 25);
		contentPane.add(btnReload);
		
		btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					lblForImagePress.setIcon(Picture());
					lblForImagePress.setText("");
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnChoose.setBounds(481, 9, 97, 25);
		contentPane.add(btnChoose);
		
		
	}
}
