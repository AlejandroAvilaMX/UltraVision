package validations;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class ValidLength {

	public ValidLength(JTextField text, int qtty) {
		text.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int size = text.getText().length();
				if(size>=qtty) {
					e.consume();
				}
			}
		});
	}

}