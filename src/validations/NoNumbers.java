package validations;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NoNumbers {

	public NoNumbers(JTextField textfield) {
		textfield.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c)) {
					e.consume();
				}
			}
		});
	}
}
