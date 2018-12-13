package Yazlab1Proje1;


import java.util.ArrayList;

public class User {
	
	double cosineSim;
	int userID;
	ArrayList<Book> commonBooks = new ArrayList<>();
	
	public User(String ISBN,double rating) {
		commonBooks.add(new Book(ISBN, rating));
	}
	public User() {
		
	}

}
