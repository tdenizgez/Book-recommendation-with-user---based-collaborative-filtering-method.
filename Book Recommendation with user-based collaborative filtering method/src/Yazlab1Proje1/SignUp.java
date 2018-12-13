package Yazlab1Proje1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.log.Log;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class SignUp extends JFrame {

	private JPanel contentPane;
	public static JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtLocation;
	private JTextField txtAge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setBounds(144, 40, 116, 22);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setBounds(144, 75, 116, 22);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtLocation = new JTextField();
		txtLocation.setText("Location");
		txtLocation.setBounds(144, 110, 116, 22);
		contentPane.add(txtLocation);
		txtLocation.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setText("Age");
		txtAge.setBounds(144, 145, 116, 22);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				String location = txtLocation.getText().toString();
				String age = txtAge.getText().toString();
				
				if(username.equals("Username") || password.equals("password") || location.equals("Location") || age.equals("age")) {
					System.out.println("Failed");
				}
				else {
					
					int temp = -1;
					LoginScreen.sql = "select userID from yazlab.users where username = '"+username+"';";
					try {
						LoginScreen.rs = LoginScreen.st.executeQuery(LoginScreen.sql);

						if(LoginScreen.rs.next()) {
							temp = LoginScreen.rs.getInt("userID");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(temp == -1) {
						try {
							LoginScreen.obje.SignUp(username, password, age, location);
							LoginScreen.LoginUsername=username;
							System.out.println("Succes.");

							LoginScreen.rateBookFrame = new RateBook();
							LoginScreen.rateBookFrame.setVisible(true);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					else {
						System.out.println("Hatali Giris Yaptiniz");
					}
					
					
					
				}
				
			}
		});
		btnSignUp.setBounds(154, 190, 97, 25);
		contentPane.add(btnSignUp);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				LoginScreen.signUpFrame.setVisible(false);
				LoginScreen.frame.setVisible(true);
			}
		});
		btnBack.setBounds(154, 228, 97, 25);
		contentPane.add(btnBack);
	}
}
