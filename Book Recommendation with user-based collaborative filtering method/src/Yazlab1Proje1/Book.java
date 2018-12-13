package Yazlab1Proje1;

public class Book {

	String ISBN;
	double rating;
	
	public Book(String ISBN,double rating) {
		this.ISBN = ISBN;
		this.rating=rating;
	}
	public Book() {
		this.ISBN="";
		this.rating=0;
	}
	
}
