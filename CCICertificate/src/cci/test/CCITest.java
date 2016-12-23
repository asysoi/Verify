package cci.test;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CCITest {
	
	public static void main(String[] args) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		 		
	}

}






