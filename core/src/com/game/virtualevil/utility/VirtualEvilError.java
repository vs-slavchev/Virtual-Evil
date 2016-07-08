package com.game.virtualevil.utility;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;

/**
 * Exceptions should be exceptional.
 * Class should be used for fatal errors which are
 * unrecoverable.
 */
public class VirtualEvilError {

	public static void show(String errorMessage) {
			JOptionPane.showMessageDialog(null,
					"Error: \n" + errorMessage,
				    "Error", JOptionPane.ERROR_MESSAGE);
			Gdx.app.exit();
	}
}
