package Yazlab1Proje1;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class UserImageButton extends JButton implements ActionListener {
	public Image img;
	public int height;
	public int width;
	public int control;
	public String urlM="";
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(control);
		try {
			LoginScreen.OpenPdf(control);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public UserImageButton(String urlM,int control) throws IOException {
		this.urlM=urlM;
		this.control=control;
		URL url = new URL(urlM);
		img  = ImageIO.read(url);
		
		
		this.height=img.getHeight(this);
		this.width=img.getWidth(this);
		
		this.setIcon(new ImageIcon(img));
	}

}
