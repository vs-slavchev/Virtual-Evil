package com.game.virtualevil.utility;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;

public class VirtualEvilException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public VirtualEvilException(String s) {
		super(s);
	}
	
	public static void showException(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Error: \n" + e.getMessage(),
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		Gdx.app.exit();
	}
}
