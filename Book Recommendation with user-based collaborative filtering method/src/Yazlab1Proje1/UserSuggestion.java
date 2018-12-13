package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class UserSuggestion extends JFrame {

	private JPanel contentPane;
	public static ArrayList<Book> suggestBook ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSuggestion frame = new UserSuggestion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Double cosineSimilarity(User secondUser) throws SQLException {
	
		int sameBookNumber = secondUser.commonBooks.size();
		double upper=0;
		double lower=0;
		String ISBN="null";
		int secondUserRating;
		int mainUserRating=0;
		int lowerMainSum=0;
		int lowerSecondSum=0;
		
		LoginScreen.sql = "select userID from yazlab.users where username='"+LoginScreen.LoginUsername+"';";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		int userID=0;
		while(LoginScreen.rs.next())
			userID=LoginScreen.rs.getInt(1);
		
		for(int i = 0;i<sameBookNumber;i++) {
			ISBN = secondUser.commonBooks.get(i).ISBN;
			LoginScreen.sql = "select bookRating from yazlab.bookrating where ISBN = '"+ISBN+"' and userID = "+secondUser.userID+";";
			LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
			LoginScreen.rs.next();
			secondUserRating = LoginScreen.rs.getInt("bookRating");
			
			LoginScreen.sql = "select bookRating from yazlab.bookrating where ISBN = '"+ISBN+"' and userID = "+userID+";";
			LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
			if(LoginScreen.rs.next()) {
				mainUserRating = LoginScreen.rs.getInt(1);	
			}
			
			lowerMainSum = lowerMainSum+(mainUserRating*mainUserRating);
			lowerSecondSum=lowerSecondSum+(secondUserRating*secondUserRating);
			
			upper = upper+(secondUserRating*mainUserRating);
		}

		
		

//		LoginScreen.sql = "select bookRating from yazlab.bookrating where userID = "+userID+";";
//		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
//		while(LoginScreen.rs.next()) {
//			int rating = LoginScreen.rs.getInt("bookRating");
//			lowerMainSum = (rating*rating)+lowerMainSum;
//			
//		}
//		LoginScreen.sql = "select bookRating from yazlab.bookrating where userID = "+secondUser.userID+";";
//	
//		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
//		while(LoginScreen.rs.next()) {
//			int rating= LoginScreen.rs.getInt("bookRating");
//			lowerSecondSum = (rating*rating)+lowerSecondSum;
//			
//		}
		
		lower = Math.sqrt(lowerMainSum)*Math.sqrt(lowerSecondSum);
		if(Double.isNaN(upper/lower))
			return (double) 0;
		else {
			return upper/lower;

		}
	}
	
	public void Suggest() throws SQLException {
		ArrayList<String> ratedBooks = new ArrayList<>();
		LoginScreen.sql = "select userID from yazlab.users where username='"+LoginScreen.LoginUsername+"';";

		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		int userID=0;
		while(LoginScreen.rs.next())
			userID=LoginScreen.rs.getInt(1);
		
		LoginScreen.sql = "select ISBN from yazlab.bookrating where userID = "+userID+";";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		while(LoginScreen.rs.next()) {
			ratedBooks.add(LoginScreen.rs.getString("ISBN"));
		}
		
		System.out.println(ratedBooks.size());
		
		String ratedBooksString="";
		for (int i = 0; i < ratedBooks.size(); i++) {
			ratedBooksString=ratedBooksString+",'"+ratedBooks.get(i)+"'";
		}
		ratedBooksString=ratedBooksString.substring(1);
		
		LoginScreen.sql = "select userID from yazlab.bookrating where ISBN in ("+ratedBooksString+") group by(userID);";
	
		LoginScreen.rs=LoginScreen.st.executeQuery(LoginScreen.sql);
		String sqlQuery="";
		ResultSet rsSql;
		java.sql.Statement stSql = LoginScreen.con.createStatement();
		
		ArrayList<User> commonUsers = new ArrayList<>();
		System.out.println("Cosinus Benzerliği");
		int counter =0;
		while(LoginScreen.rs.next()) {
			sqlQuery = "select * from yazlab.bookrating where ISBN in ("+ratedBooksString+") and userID="+LoginScreen.rs.getInt("userID")+";";
			rsSql = stSql.executeQuery(sqlQuery);
			commonUsers.add(counter,new User());
			commonUsers.get(counter).userID=LoginScreen.rs.getInt("userID");
			
			while(rsSql.next()) {
				commonUsers.get(counter).commonBooks.add(new Book(rsSql.getString("ISBN"),rsSql.getDouble("bookRating")));
			}
			counter++;
		}
		
		for (int i = 0; i < commonUsers.size(); i++) {
			commonUsers.get(i).cosineSim=cosineSimilarity(commonUsers.get(i));
		}
		
		
		for (int i = 0; i < commonUsers.size(); i++) {
			if(commonUsers.get(i).userID == userID) {
				commonUsers.remove(i);
				i--;
			}
		}
		

		for (int i = 0; i < commonUsers.size(); i++) {
			System.out.println("Benzerlik:"+commonUsers.get(i).cosineSim);
			System.out.println("UserID:"+commonUsers.get(i).userID);
			System.out.println("###################################################");
		}

		System.out.println("Cosinus Benzerliği Bitti");

//		for (int i = 0; i < commonUsers.size(); i++) {
//			if(commonUsers.get(i).cosineSim==0) {
//				commonUsers.remove(i);
//				i--;
//			}
//		}
		

		
		//################### Tahmin Kısmı ################################
		
		double Rai=0;
		double Ra=0;
		double Ru=0;
		double Rui=0;
		double cosineSimSum=0;
		
		//RA NIN BULUNMASI
		LoginScreen.sql="select avg(bookRating) from yazlab.bookrating where userID="+userID+";";
		LoginScreen.rs=LoginScreen.st.executeQuery(LoginScreen.sql);
		LoginScreen.rs.next();
		Ra = LoginScreen.rs.getDouble(1);
		
		//TOPLAM COSINE SIM BULUNMASI
		String userIds="";
		for (int i = 0; i < commonUsers.size(); i++) {
			cosineSimSum+=commonUsers.get(i).cosineSim;
			userIds=userIds+","+commonUsers.get(i).userID;
			
		}
		
		
		userIds = userIds.substring(1);
		
		LoginScreen.sql = "select ISBN from yazlab.bookrating where ISBN not in ("+ratedBooksString+") and userID in("+userIds+");";
		LoginScreen.rs=LoginScreen.st.executeQuery(LoginScreen.sql);
		
		
		System.out.println("Girdi.");
		suggestBook = new ArrayList<>();
		
		int countBook=0;
		String query  = "select count(ISBN) from yazlab.bookrating where ISBN not in ("+ratedBooksString+") and userID in("+userIds+");";
		ResultSet rsq;
		java.sql.Statement stq = LoginScreen.con.createStatement();
		rsq = stq.executeQuery(query);
		while(rsq.next()) {
			countBook = rsq.getInt(1);
		}
		
		int cntr=0;
		while(LoginScreen.rs.next() && cntr<50) {

			double result = 0;
			for (int i = 0; i < commonUsers.size(); i++) {
				sqlQuery="select avg(bookRating) from yazlab.bookrating where userID="+commonUsers.get(i).userID+";";
				
				rsSql=stSql.executeQuery(sqlQuery);
				rsSql.next();
				Ru=rsSql.getDouble(1);
				
				sqlQuery="select bookRating from yazlab.bookrating where userID="+commonUsers.get(i).userID+" and ISBN='"+LoginScreen.rs.getString("ISBN")+"';";     
				
				rsSql=stSql.executeQuery(sqlQuery);
				Rui=Ru*-1;
				while(rsSql.next()) {
					Rui = rsSql.getDouble(1);
				}
				result = result+(Rui-Ru)*commonUsers.get(i).cosineSim;
			}
			Rai = Ra+result/cosineSimSum;
			suggestBook.add(new Book(LoginScreen.rs.getString("ISBN"),Rai));
			cntr++;
		}
		
		System.out.println("Tahminleri Yaptı");
		Book tmp = new Book();
		
		for (int i = 1; i < suggestBook.size(); i++) {
			for (int j = suggestBook.size()-1; j > i; j--) {
				if(suggestBook.get(j-1).rating < suggestBook.get(j).rating) {
					
					tmp.ISBN=suggestBook.get(j-1).ISBN;
					tmp.rating=suggestBook.get(j-1).rating;
					
					suggestBook.get(j-1).ISBN=suggestBook.get(j).ISBN;
					suggestBook.get(j-1).rating=suggestBook.get(j).rating;
					
					suggestBook.get(j).ISBN=tmp.ISBN;
					suggestBook.get(j).rating=tmp.rating;
					
				}
			}
			
		}
		System.out.println("Siraladı");
		System.out.println("Siralanmıs");
		for (int i = 0; i < 10; i++) {
			System.out.println("ISBN:"+suggestBook.get(i).ISBN);
			System.out.println("Rating:"+suggestBook.get(i).rating);
			System.out.println("#################################");
		}

	}
	
	public UserSuggestion() throws SQLException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1150, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.userPanelFrame.setVisible(true);
				LoginScreen.userSuggestionFrame.setVisible(false);
			}
		});
		btnBack.setBounds(1024, 413, 114, 25);
		contentPane.add(btnBack);
		
		
		
		
		Suggest();
		
		System.out.println("bitti");
		
		ArrayList<String> MediumImageUrls = new ArrayList<>();
		ArrayList<String> BookTitles = new ArrayList<>();
		
		String book="";
		
		for (int i = 0; i < 20; i++) {
			book = book +"'"+ suggestBook.get(i).ISBN +"' or ISBN = ";
		}
		
			LoginScreen.sql="select * from yazlab.book where ISBN = "+book+" 'kkkkk';";
			LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
			int cnt=0;
			while(LoginScreen.rs.next()&&cnt<10) {
				
				BookTitles.add(LoginScreen.rs.getString("bookTitle"));
				MediumImageUrls.add(LoginScreen.rs.getString("urlM"));
				cnt++;
			}

			UserImageButton btnButton[][] = new UserImageButton[5][2];
			
			JLabel lblBookName[][] = new JLabel[5][2];
			
			for(int i=0;i<5;i++) {
				for(int j=0;j<2;j++) {
					btnButton[i][j] = new UserImageButton(MediumImageUrls.get(i*2+j),i*2+j);
					btnButton[i][j].setBounds(50+(200+10)*i, 50+(150+50)*j, 104, 160);
					contentPane.add(btnButton[i][j]);
					lblBookName[i][j] = new JLabel(BookTitles.get(i*2+j));
					lblBookName[i][j].setBounds(57+(200+10)*i, 30+(150+50)*j, 200, 15);
					contentPane.add(lblBookName[i][j]);
					btnButton[i][j].addActionListener(btnButton[i][j]);
					
				}
			}
			
		
	}
}
