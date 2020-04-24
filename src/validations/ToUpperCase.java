package validations;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class ToUpperCase {

	public ToUpperCase(JTextField textfield) {
		textfield.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLowerCase(c)) {
					String data=("" + c).toUpperCase();
					c=data.charAt(0);
					e.setKeyChar(c);
					//e.consume();
				}
				//e.consume();
			}
		});
	}
}
