package com.game.virtualevil.utility;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;

/**
 * Exceptions should be exceptional.
 * @author vs
 */
public class VirtualEvilError {

	public static void show(String errorMessage) {
		try {
			throw new RuntimeException(errorMessage);
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null,
					"Error: \n" + e.getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
}
