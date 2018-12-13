package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class LoginScreen extends JFrame {
	
	public void CreateTable() throws SQLException {
		
		sql = "create database if not exists yazLab;";
		st.executeUpdate(sql);
		
		//Users Tablosu
		sql = "create table if not exists yazLab.Users("
				+ "userID double NOT NULL AUTO_INCREMENT,"
				+ "username varchar(255) NOT NULL UNIQUE,"
				+ "password varchar(255) NOT NULL,"
				+ "location varchar(255) NOT NULL,"
				+ "age varchar(255) ,"
				+ "primary key(userID)"
				+ ");";
		st.executeUpdate(sql);
		
		//Books Tablosu
		sql = "create table if not exists yazLab.Book("
				+ "ISBN varchar(255) NOT NULL,"
				+ "bookID int NOT NULL AUTO_INCREMENT,"
				+ "bookTitle varchar(255),"
				+ "bookAuthor varchar(255),"
				+ "yearOfPublication varchar(255),"
				+ "publisher varchar(255),"
				+ "urlS varchar(255),"
				+ "urlM varchar(255),"
				+ "urlL varchar(255),"
				+ "sumOfRating int DEFAULT NULL,"
				+ "countOfReading int DEFAULT 0,"
				+ "bookRating int DEFAULT NULL,"
				+ "primary key(bookID)"
				+ ");";
		st.executeUpdate(sql);
		
		//BookRating Tablosu
		sql = "create table if not exists yazLab.BookRating("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ "userID double NOT NULL ,"
				+ "ISBN varchar(255) NOT NULL,"
				+ "bookRating int,"
				+ "primary key(id),"
				+ "foreign key(userID) references yazLab.Users(userID)  on delete cascade on update cascade"
				+ ");";
		st.executeUpdate(sql);
		
	}

	public void DataInsertUsers() throws IOException {
		
		File file = new File("BX-Users.csv");
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		
		String line="";
		int sayac=0;
		while((line = bf.readLine())!=null) {
			if(sayac == 0 ) {sayac ++; continue;}
			
			String[] tempArray = line.split(";");
			String userID = tempArray[0].substring(1, tempArray[0].length()-1);
			int userIDint = Integer.parseInt(userID);
			String location = tempArray[1].substring(1, tempArray[1].length()-1);
			try {
			String age = tempArray[2];
			if(tempArray[2].startsWith("\"")){
				age = tempArray[2].substring(1, tempArray[2].length()-1);
			}
			String username = "username"+userID;
			int password = 0;
			sql = "insert into yazlab.users (userID,username,password,location,age)"
					+ "values("+userIDint+" , '"+username+"' ,'"+password+"','"+location+"','"+age+"');";
			st.executeUpdate(sql);
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		bf.close();
		
	}
	
	public void DataInsertBook() throws NumberFormatException, IOException, SQLException {
		
		File file = new File("BX-Books.csv");
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		
		String line="";
		int sayac=0;
		while((line = bf.readLine())!=null) {
			if(sayac == 0 ) {sayac ++; continue;}
			try {
				//System.out.println("lsndfsa");
				String k = "₺";
				String[] tempArray = line.split(k);
				String ISBN = tempArray[0].substring(1, tempArray[0].length()-1);
				String bookTitle = tempArray[1].substring(1, tempArray[1].length()-1);
				String bookAuthor = tempArray[2].substring(1, tempArray[2].length()-1);
				String yearOfPublication = tempArray[3].substring(1,tempArray[3].length()-1);
				String publisher = tempArray[4].substring(1, tempArray[4].length()-1);
				String urlS = tempArray[5].substring(1, tempArray[5].length()-1);
				String urlM = tempArray[6].substring(1, tempArray[6].length()-1);
				String urlL = tempArray[7].substring(1, tempArray[7].length()-1);
				
				sql = "insert into yazlab.book (ISBN,bookTitle,bookAuthor,yearOfPublication,publisher,urlS,urlM,urlL)"
						+ "values('"+ISBN+"','"+bookTitle+"','"+bookAuthor+"','"+yearOfPublication+"','"+publisher+"','"+urlS+"','"+urlM+"','"+urlL+"');";
				st.executeUpdate(sql);
				
			}catch (Exception e) {

				e.printStackTrace();
				continue;
			}
			
			
		}
		bf.close();
		
	}
	
	public void DataInsertBookRating() throws IOException, SQLException {
		
		File file = new File("BX-Book-Ratings.csv");
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		
		String line="";
		int sayac=0;
		while((line = bf.readLine())!=null) {
			if(sayac == 0 ) {sayac ++; continue;}
			
			String[] tempArray = line.split(";");
			System.out.println(line);
			Double userID = Double.parseDouble(tempArray[0].substring(1,tempArray[0].length()-1));
			String ISBN = tempArray[1].substring(1,tempArray[1].length()-1);
			int bookRating = Integer.parseInt(tempArray[2].substring(1, tempArray[2].length()-1));
			
			sql = "insert into yazlab.bookrating (userID,ISBN,bookRating) "
					+ "values("+userID+",'"+ISBN+"',"+bookRating+");";
			st.executeUpdate(sql);
					
		}
		bf.close();
		
		
			
	}
	
	public boolean LoginControl(String username,String password) throws SQLException {
		
		sql = "select * from yazlab.users where username = \""+username+"\";";
		System.out.println(sql);
		rs = st.executeQuery(sql);
		String rsUsername=null;
		String rsPassword=null;
		while(rs.next()) {
			rsUsername = rs.getString("username");
			rsPassword = rs.getString("password");
		}
		
		if(rsUsername == null) {
			System.out.println("Giris Haklsajdflasfsatali");
			return false;
		}
		else {
			if(rsUsername.equals(username) && rsPassword.equals(password)) {
				System.out.println("Giris Basarili");
				return true;
			}
			else {
				System.out.println("Giris Hatali");
				return false;
			}
		}
	}
	
	public void SignUp(String username,String password,String age,String location) throws SQLException {
		try {

			sql = "insert into  yazlab.users (username,password,location,age)"
					+ "values('"+username+"','"+password+"','"+location+"','"+age+"')";
			st.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	
	public int SumOfRating(String ISBN) throws NumberFormatException, SQLException {
		sql = "select sum(bookRating) as sumofrating FROM yazlab.bookrating where ISBN = '"+ISBN+"';";
		ResultSet rsSum = st.executeQuery(sql);
		rsSum.first();
		return rsSum.getInt(1);
	}
	
	public int CountOfReading(String ISBN) throws SQLException {
		sql ="select count(ISBN) FROM yazlab.bookrating where yazlab.bookrating.ISBN='"+ISBN+"';";
		ResultSet rsCount = st.executeQuery(sql);
		rsCount.first();
		return rsCount.getInt(1);
	}
	
	public void BookRating(int count,int sum,String ISBN) throws SQLException {
		int average;
		if(count==0) {
			average = 0;
		}
		else {
			average = sum/count;
		sql="update yazlab.book set bookRating = "+average+" where ISBN= '"+ISBN+"';";
		st.executeUpdate(sql);
		sql="update yazlab.book set sumOfRating = "+sum+" where ISBN= '"+ISBN+"';";
		st.executeUpdate(sql);
		sql="update yazlab.book set countOfReading = "+count+" where ISBN= '"+ISBN+"';";
		st.executeUpdate(sql);
		System.out.print(".");
		}
	}
	
	public void CalculateBookRating() throws SQLException {
		sql = "select ISBN from yazlab.book";
		ResultSet rsCalculate = null;
		
		try {
			rsCalculate = stCalculate.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(rsCalculate.next()) {
			String ISBN = rsCalculate.getString("ISBN");
			int sum=SumOfRating(ISBN);
			int count=CountOfReading(ISBN);
			BookRating(count, sum, ISBN);
		}
	}
	
	public static void OpenPdf(int number) throws IOException {
		String path="/home/sentinel/Masaüstü/YazLab 1 Proje 1";
		switch (number) {
		case 0:
			path = path+"/"+number+".pdf";
			break;
		case 1:
			path = path+"/"+number+".pdf";
			break;
		case 2:
			path = path+"/"+number+".pdf";
			break;
		case 3:
			path = path+"/"+number+".pdf";
			break;
		case 4:
			path = path+"/"+number+".pdf";
			break;
		case 5:
			path = path+"/"+number+".pdf";
			break;
		case 6:
			path = path+"/"+number+".pdf";
			break;
		case 7:
			path = path+"/"+number+".pdf";
			break;
		case 8:
			path = path+"/"+number+".pdf";
			break;
		case 9:
			path = path+"/"+number+".pdf";
			break;
			

		default:
			break;
		}
		File myFile = new File( path);
        Desktop.getDesktop().open(myFile);
	}
	
	private static final long serialVersionUID = 1L;
	public static java.sql.Statement st = null;
	public static java.sql.Statement stCalculate = null;
    public static ResultSet rs = null;
    public static Connection con = null;
    public static String sql = null;
    public static String url = "jdbc:mysql://localhost:3306?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String kullaniciad = "root";
    public static String sifre = "123456789";
    
    
    public static LoginScreen obje;
	public static LoginScreen frame;
	public static SignUp signUpFrame;
	public static RateBook rateBookFrame;
	public static AdminDeleteBook adminDeleteBookFrame;
	public static AdminDeleteUser adminDeleteUserFrame;
	public static AdminAddBook adminAddBookFrame;
	public static AdminPanel  adminPanelFrame;
	public static UserPanel userPanelFrame;
	public static UserFavouriteBooks userFavouriteBooksFrame;
	public static UserPopularBooks userPopularBooksFrame;
	public static UserListing userListingFrame;
	public static UserLastFive userLastFiveFrame;
	public static UserSuggestion userSuggestionFrame;
	

	private JPanel contentPane;
	public  JTextField txtUsername;
	private JTextField txtPassword;
	public static String LoginUsername;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			 
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, kullaniciad, sifre);
			st = con.createStatement();
			stCalculate = con.createStatement();
			System.out.println("Baglandi");
			
			obje = new LoginScreen();
			//obje.CreateTable();
			//obje.DataInsertUsers();
			//obje.DataInsertBook();
			//obje.DataInsertBookRating();
			//obje.CalculateBookRating();
			
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}			
	}

	
	public LoginScreen() throws SQLException, IOException {
		
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 240, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(57, 46, 116, 22);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(57, 81, 116, 22);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtUsername.getText().toString().equals("admin")) {
					try {
						boolean check =LoginControl(txtUsername.getText().toString(), txtPassword.getText().toString());
						if(check==true) {
							System.out.println("Succes.");
							LoginUsername = txtUsername.getText();
							adminPanelFrame = new AdminPanel();
							LoginScreen.frame.setVisible(false);
							LoginScreen.adminPanelFrame.setVisible(true);
						}
						else
							System.out.println("Failed.");
					} 
					catch (SQLException e) {
						System.out.println("Failed.");
						e.printStackTrace();
					}
					
				}
				else {
					try {
						boolean check =LoginControl(txtUsername.getText().toString(), txtPassword.getText().toString());
						if(check==true) {
							System.out.println("Succes.");
							LoginUsername = txtUsername.getText();
							LoginScreen.userPanelFrame = new UserPanel();
							LoginScreen.userPanelFrame.setVisible(true);
							LoginScreen.frame.setVisible(false);
							
						}
						else
							System.out.println("Failed.");
					} 
					catch (SQLException e) {
						System.out.println("Failed.");
						e.printStackTrace();
					}
					
				}
				
			}
		});
		btnLogin.setBounds(68, 120, 97, 25);
		contentPane.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				signUpFrame = new SignUp();
				signUpFrame.setVisible(true);
				frame.setVisible(false);
				
				
			}
		});
		btnSignUp.setBounds(68, 158, 97, 25);
		contentPane.add(btnSignUp);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					rateBookFrame = new RateBook();
					adminDeleteBookFrame = new AdminDeleteBook();
					adminAddBookFrame = new AdminAddBook();
					adminDeleteUserFrame = new AdminDeleteUser();
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//rateBookFrame.setVisible(true);
				adminAddBookFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(443, 80, 97, 25);
		contentPane.add(btnNewButton);
		
		
	}
}
