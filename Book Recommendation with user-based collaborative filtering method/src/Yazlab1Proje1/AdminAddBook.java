package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminAddBook extends JFrame {

	private JPanel contentPane;
	private JTextField txtIsbn;
	private JTextField txtBookTitle;
	private JTextField txtBookAuthor;
	private JTextField txtYearOfPublication;
	private JTextField txtPublisher;
	private JTextField txtUrlSmall;
	private JTextField txtUrlMedium;
	private JTextField txtUrlLong;

	public void AddBook(String ISBN,String bookTitle,String bookAuthor,String yearOfPublication,String publisher,String urlS,String urlM,String urlL) throws SQLException {
			
			LoginScreen.sql = "insert into yazlab.book (ISBN,bookTitle,bookAuthor,yearOfPublication,publisher,urlS,urlM,urlL)"
					+ "values('"+ISBN+"','"+bookTitle+"','"+bookAuthor+"','"+yearOfPublication+"','"+publisher+"','"+urlS+"','"+urlM+"','"+urlL+"');";
			LoginScreen.st.executeUpdate(LoginScreen.sql);
			System.out.println(LoginScreen.sql);
		}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminAddBook frame = new AdminAddBook();
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
	public AdminAddBook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtIsbn = new JTextField();
		txtIsbn.setText("ISBN");
		txtIsbn.setBounds(142, 31, 116, 22);
		contentPane.add(txtIsbn);
		txtIsbn.setColumns(10);
		
		txtBookTitle = new JTextField();
		txtBookTitle.setText("Book Title");
		txtBookTitle.setBounds(142, 66, 116, 22);
		contentPane.add(txtBookTitle);
		txtBookTitle.setColumns(10);
		
		txtBookAuthor = new JTextField();
		txtBookAuthor.setText("Book Author");
		txtBookAuthor.setBounds(142, 101, 116, 22);
		contentPane.add(txtBookAuthor);
		txtBookAuthor.setColumns(10);
		
		txtYearOfPublication = new JTextField();
		txtYearOfPublication.setText("Year Of Publication");
		txtYearOfPublication.setBounds(142, 136, 116, 22);
		contentPane.add(txtYearOfPublication);
		txtYearOfPublication.setColumns(10);
		
		txtPublisher = new JTextField();
		txtPublisher.setText("Publisher");
		txtPublisher.setBounds(142, 171, 116, 22);
		contentPane.add(txtPublisher);
		txtPublisher.setColumns(10);
		
		txtUrlSmall = new JTextField();
		txtUrlSmall.setText("Url Small");
		txtUrlSmall.setBounds(142, 206, 116, 22);
		contentPane.add(txtUrlSmall);
		txtUrlSmall.setColumns(10);
		
		txtUrlMedium = new JTextField();
		txtUrlMedium.setText("Url Medium");
		txtUrlMedium.setBounds(142, 241, 116, 22);
		contentPane.add(txtUrlMedium);
		txtUrlMedium.setColumns(10);
		
		txtUrlLong = new JTextField();
		txtUrlLong.setText("Url Long");
		txtUrlLong.setBounds(142, 276, 116, 22);
		contentPane.add(txtUrlLong);
		txtUrlLong.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddBook(txtIsbn.getText().toString(), txtBookTitle.getText().toString(), txtBookAuthor.getText().toString(), txtYearOfPublication.getText().toString(), txtPublisher.getText().toString(), txtUrlSmall.getText().toString(), txtUrlMedium.getText().toString(), txtUrlLong.getText().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(152, 311, 97, 25);
		contentPane.add(btnAdd);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				LoginScreen.adminAddBookFrame.setVisible(false);
				LoginScreen.adminPanelFrame.setVisible(true);
			}
		});
		btnBack.setBounds(152, 350, 97, 25);
		contentPane.add(btnBack);
	}
}
