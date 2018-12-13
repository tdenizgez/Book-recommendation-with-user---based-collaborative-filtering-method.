package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class RateBook extends JFrame {
	static JLabel lblNewLabel;
	private JPanel contentPane;
	private JTable table;
	public JComboBox<String> comboBox;
	public Image image=null;
	DefaultTableModel tableModel = new DefaultTableModel();
	String colums[] = {"Book ID","ISBN","Book Title","Book Author","Year Of Publication","Publisher","Rating"};
	Object rows[] = new Object[7];
	public static ArrayList<String> ratedBooks = new ArrayList<>();//Oylanan Kitaplar� Tuttu�umuz List
	JButton btnContinue;
	
	public ImageIcon Picture() throws SQLException, IOException {
		LoginScreen.sql = "select urlL from yazlab.book where bookID = "+Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString())+";";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		String urlL=null;
		LoginScreen.rs.next();
		
			urlL= LoginScreen.rs.getString("urlL");
			System.out.println(urlL);
		 	URL url = new URL(urlL);
			Image image = ImageIO.read(url);
			return new ImageIcon(image);

	}
	
	public void RateBook() throws SQLException {
		int rating = comboBox.getSelectedIndex();
		double userId = 0;
		String ISBN = null;
		int sumOfRating=0;
		int countOfReading=0;
		
		int bookID = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());

		LoginScreen.sql="select userID from yazlab.users where username = '"+SignUp.txtUsername.getText()+"';";
		LoginScreen.rs=LoginScreen.st.executeQuery(LoginScreen.sql);
		while(LoginScreen.rs.next()) {
			userId = LoginScreen.rs.getDouble("userID");
		}
		
		LoginScreen.sql="select ISBN,sumOfRating,countOfReading from yazlab.book where bookID = "+bookID+";";
		LoginScreen.rs=LoginScreen.st.executeQuery(LoginScreen.sql);
		while(LoginScreen.rs.next()) {
			ISBN = LoginScreen.rs.getString("ISBN");
			sumOfRating=LoginScreen.rs.getInt("sumOfRating");
			countOfReading=LoginScreen.rs.getInt("countOfReading");
		}
		
		ratedBooks.add(ISBN);//Oylanan Kitab�n ISBN Numaras�
		
		
		LoginScreen.sql="update yazlab.book set sumOfRating = "+(sumOfRating+rating)+" where bookID = "+bookID+";";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		LoginScreen.sql="update yazlab.book set countOfReading = "+(countOfReading+1)+" where bookID = "+bookID+";";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		LoginScreen.sql="update yazlab.book set bookRating = "+((sumOfRating+rating)/(countOfReading+1))+" where bookID = "+bookID+";";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		

		LoginScreen.sql="insert into yazlab.bookrating (userID,ISBN,bookRating) values("+userId+",'"+ISBN+"',"+rating+");";
		LoginScreen.st.executeUpdate(LoginScreen.sql);
		System.out.println(LoginScreen.sql);
	
		
	
	}
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
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public RateBook() throws SQLException, IOException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 674);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JLabel labelCounter = new JLabel("10");
		labelCounter.setBounds(849, 68, 79, 25);
		contentPane.add(labelCounter);
		
		JLabel lblCounter = new JLabel("Counter");
		lblCounter.setBounds(777, 73, 66, 15);
		contentPane.add(lblCounter);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.rateBookFrame.setVisible(false);
				LoginScreen.signUpFrame.setVisible(true);
			}
		});
		btnBack.setBounds(862, 589, 97, 25);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 82, 554, 498);
		contentPane.add(scrollPane);
		
		
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
		
		table = new JTable();
		table.setModel(tableModel);
		
		table.setBounds(119, 299, 384, 233);
		scrollPane.setViewportView(table);
		
		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					/**
					 * getValuesAt
					 */
					lblNewLabel.setIcon(Picture());
					lblNewLabel.setText("");
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnChoose.setBounds(621, 78, 97, 25);
		contentPane.add(btnChoose);
		
		lblNewLabel = new JLabel("After choosing book press the choose button");
		lblNewLabel.setBounds(621, 116, 338, 426);
		contentPane.add(lblNewLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(621, 555, 97, 25);
		contentPane.add(comboBox);
		comboBox.addItem("0");
		comboBox.addItem("1");
		comboBox.addItem("2");
		comboBox.addItem("3");
		comboBox.addItem("4");
		comboBox.addItem("5");
		comboBox.addItem("6");
		comboBox.addItem("7");
		comboBox.addItem("8");
		comboBox.addItem("9");
		comboBox.addItem("10");
		
		
		JButton btnChoose_1 = new JButton("Choose");
		btnChoose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					RateBook();
					int value = Integer.parseInt(labelCounter.getText())-1;
					labelCounter.setText(String.valueOf(value));
					if(value == 5) {
						btnContinue.setVisible(true);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("Fonksiyon Coktu");
					e1.printStackTrace();
				}
				
				
			}
		});
		btnChoose_1.setBounds(730, 555, 97, 25);
		contentPane.add(btnChoose_1);
		
		btnContinue = new JButton("Continue");
		btnContinue.setVisible(true);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.rateBookFrame.setVisible(false);
				LoginScreen.signUpFrame.setVisible(false);
				LoginScreen.userPanelFrame = new UserPanel();
				LoginScreen.userPanelFrame.setVisible(true);
			}
		});
		btnContinue.setBounds(740, 589, 114, 25);
		contentPane.add(btnContinue);
		
		
 
		//contentPane.add(table);
	}
}
