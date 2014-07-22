package cci.cert.form.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ApplicationListener implements ActionListener {

	 public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,
                    "Основная панель приложения");
        }
    
}
