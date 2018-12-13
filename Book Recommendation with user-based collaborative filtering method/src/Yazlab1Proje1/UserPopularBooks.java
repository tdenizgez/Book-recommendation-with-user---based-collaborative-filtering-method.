package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserPopularBooks extends JFrame {

	private JPanel contentPane;

	private static ArrayList<String> BookTitles;
	private static ArrayList<String> MediumImageUrls;

	public void getPopularBooks() throws SQLException {
		

		String bookSql="";
		LoginScreen.sql = "select count(ISBN),ISBN from yazlab.bookrating group by ISBN order by count(ISBN) desc LIMIT 10;";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		while(LoginScreen.rs.next()) {
			bookSql = bookSql+",'"+LoginScreen.rs.getString("ISBN")+"'";
		}
		bookSql=bookSql.substring(1);
		
		BookTitles= new ArrayList<>();
		MediumImageUrls = new ArrayList<>();
		LoginScreen.sql="select * from yazlab.book where ISBN in ("+bookSql+");";
		LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);
		int cnt=0;
		
		
		while(LoginScreen.rs.next()&&cnt<10) {
			
			BookTitles.add(LoginScreen.rs.getString("bookTitle"));
			MediumImageUrls.add(LoginScreen.rs.getString("urlM"));
			cnt++;
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPopularBooks frame = new UserPopularBooks();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public UserPopularBooks() throws IOException, SQLException {
		getPopularBooks();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1150, 480);
		// yatay 450 ,dikey 300      90    60
		//setbounds(x,y,width,height);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.userPopularBooksFrame.setVisible(false);
				LoginScreen.userPanelFrame.setVisible(true);
				
			}
		});
		btnBack.setBounds(1024, 413, 114, 25);
		contentPane.add(btnBack);
		
		JLabel lblBookName[][] = new JLabel[5][2];

		System.out.println("boyut:"+MediumImageUrls.size());
		for(int k =0 ;k<MediumImageUrls.size();k++) {
			System.out.println(MediumImageUrls.get(k));
		}
		UserImageButton btnButton[][] = new UserImageButton[5][2];
		int i,j;
		for(i=0;i<5;i++) {
			for(j=0;j<2;j++) {
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
