package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;

import javax.swing.*;

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
