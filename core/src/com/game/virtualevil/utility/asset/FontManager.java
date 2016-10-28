package com.game.virtualevil.utility.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/* The FontManager class contains all the fonts.
 * TODO If there are more fonts: create a hashmap? and access them by a key.
 * TODO store fonts in a collection and check if they are null when disposing them;
 * - a font should have more than 1 size?
 * - maybe put ttf loading boilerplate into a method? */
public class FontManager {

    private final String PATH = "fonts/", FORMAT = ".ttf";
    private BitmapFont debugFont;
    private BitmapFont startScreenDigits;
    private BitmapFont HUDHealthFont;

    public FontManager() {
        debugFont = new BitmapFont();
    }

    public BitmapFont getDebugFont() {
        return debugFont;
    }

    public BitmapFont getStartScreenDigits(int fontSize) {
        if (startScreenDigits == null) {
            startScreenDigits = generateFont("VeraMono", fontSize);
        }
        return startScreenDigits;
    }

    //LCD style font used for HP display on the HUD
    public BitmapFont getHUDHealthFont(int fontSize) {
        if (HUDHealthFont == null) {
            HUDHealthFont = generateFont("24Display", fontSize);
        }
        return HUDHealthFont;
    }

    private BitmapFont generateFont(String fontName, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(PATH + fontName + FORMAT));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = com.badlogic.gdx.graphics.Color.GREEN;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        BitmapFont product = generator.generateFont(parameter);
        generator.dispose();
        return product;
    }

    public void disposeAllFonts() {
        debugFont.dispose();
        startScreenDigits.dispose();
    }
}
