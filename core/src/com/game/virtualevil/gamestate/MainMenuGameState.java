package com.game.virtualevil.gamestate;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.virtualevil.Game;

public class MainMenuGameState extends GameState{
	
	// use the buttons only after digitsAnimationFinished is true
	private MenuButtons buttons;
	private TextureRegion background, blackTrapezoid;
	// 2D array of the images of the chips
	private TextureRegion[][] chipsImages;
	// the x coordinates of the chips on the screen
	private float[][] chipsXcoords;
	private final int CHIP_ROWS = 4, CHIP_COLS = 6, CHIP_SCROLL_SPEED = 2;
	// horizontal/vertical scale of the images: background, trapezoid, chips.
	private int vScale, hScale;
	private float trapezoidY, trapezoidVelocityY = -70.0f;
	private int trapezoidTargetPositionY, trapezoidX;
	// space between top line of digits and the top of the trapezoid
	private float digitsYoffset;
	// each element in the array is a line of digits
	private String[] renderDigits;
	// each digit is in its own cell
	private char[][] digits;
	private BitmapFont titleFont;
	private Random rand = new Random();
	// used to slow down the randomization of the digits
	private float deltaTimerDigits = 0.0f;
	// booleans to monitor the state of each separate animation
	private boolean digitsAnimationFinished = false, trapezoidAnimationFinished = false;
	
	public MainMenuGameState(GameStateManager gsm, Game game) {
		super(gsm, game);
	}

	@Override
	public void initialize() {		
		camera = new OrthographicCamera(
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(false, camera.viewportWidth/2, camera.viewportHeight/2);
		camera.update();
		
		ScreenViewport viewport = new ScreenViewport(camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		
		buttons = new MenuButtons(game);
		
		background = assetManager.getTextureManager().getImage("startScreenBackground");
		background.setRegion(0, 0, 120, 68);
		blackTrapezoid = new TextureRegion(
				assetManager.getTextureManager().getImage("startScreenBackground"),
				0, 68, 86, 30);
		
		// calculate the vertical and horizontal scale of the screen
		vScale = (int)Math.ceil(Gdx.graphics.getHeight() / (double)background.getRegionHeight());
		hScale = (int)Math.ceil(Gdx.graphics.getWidth() / (double)background.getRegionWidth());
		
		
		digitsYoffset = (int)(vScale * 0.875);
		
		trapezoidX = Gdx.graphics.getWidth()/2
				- blackTrapezoid.getRegionWidth()*hScale/2;
		
		
		TextureRegion chipsTileset = assetManager.getTextureManager()
				.getImage("chipBuildings");
		
		/* chop the tileset with chips and put each separate chip
		 * in the 2D array called chipsImages */
		chipsImages = chipsTileset.split(chipsTileset.getRegionWidth()/CHIP_COLS,
				chipsTileset.getRegionHeight()/CHIP_ROWS);
		
		/* the chipsXcoords 2D array holds the X position of each chip
		 * on the screen. The for loop initializes the chips to be
		 * evenly spread. 4 is the spacing between the chips. */
		chipsXcoords = new float[CHIP_ROWS][CHIP_COLS];
		for (int row = 0; row < chipsXcoords.length; row++) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				chipsXcoords[row][col] = col * (chipsImages[row][col].getRegionWidth() + 4);
			}
		}
		
		//set up the black trapezoid
		trapezoidY = Gdx.graphics.getHeight() + 250;
		trapezoidTargetPositionY = Gdx.graphics.getHeight()
				- blackTrapezoid.getRegionHeight() * vScale - (int)(vScale * 1.25);
		
		// the font size depends on the size of the screen
		titleFont = assetManager.getFontManager().getStartScreenDigits(hScale - 2);
		
		// initialize the hard coded digits
		setUpDigits();
		// the 2D chars array gates the string array's contents
		digits = new char[renderDigits.length][renderDigits[0].length()];
		for (int line = 0; line < digits.length; line++) {
			digits[line] = renderDigits[line].toCharArray();
		}
	}

	@Override
	public void update(final float delta) {
		
		if (digitsAnimationFinished) {
			buttons.checkClicks();
		}
		
		for (int row = 0; row < chipsXcoords.length; row++) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				/* move right and check if out of screen. In that case
				 * set x to the negative image width, so the rightmost
				 * part of the image is not yet in the screen. */
				chipsXcoords[row][col] += CHIP_SCROLL_SPEED * (CHIP_ROWS - row) * delta;
				if (chipsXcoords[row][col] > background.getRegionWidth()) {
					chipsXcoords[row][col] = -chipsImages[row][col].getRegionWidth();
				}
			}
		}
		
		if (!digitsAnimationFinished) {
			// animation for the black trapezoid
			trapezoidY += trapezoidVelocityY * delta;
			// if the target was overshot while going down, reverse the velocity to go back up
			if (trapezoidY < trapezoidTargetPositionY) {
				trapezoidVelocityY += 0.6f;
				/* if target is reached while going up, null the velocity
				 * and finish this phase of the animation */
			} else if (trapezoidVelocityY > 0 && trapezoidY > trapezoidTargetPositionY) {
				trapezoidVelocityY = 0;
				trapezoidAnimationFinished = true;
			}

			/* If it is time for the digits animation to run (the trapezoid one
			 * is over) start using the delta timer float variable to slow down
			 * the randomization. 1s and 0s never change, but the other numbers
			 * get randomized. If their int value is 58, that is the ':' colon,
			 * then they are changed to a ' ' space to not get rendered, but
			 * still take up screen space. Those spaces will remain unchanged,
			 * like the 1s and 0s. After the black trapezoid and the randomizing
			 * digits animations are over the animationsFinished boolean is set
			 * to true and the animations are no longer updated. */
			if (trapezoidAnimationFinished) {
				deltaTimerDigits += delta;
				// change up the digits
				if (deltaTimerDigits > 0.12) {
					deltaTimerDigits = 0.0f;
					// if no digits need changing then the animation is finished
					boolean someDigitChanged = false;
					for (int line = 0; line < digits.length; line++) {
						for (int digit = 0; digit < digits[line].length; digit++) {
							if (!(digits[line][digit] == '1' || digits[line][digit] == '0'
									|| digits[line][digit] == ' ')) {
								/* adding 48 to and int and casting it to a char
								 * makes it the char representing that integer:
								 * the char '0' has an ASCII id of 48 */
								char newDigit = (char) (48 + 2 + rand.nextInt(9));
								digits[line][digit] = newDigit == ':' ? ' ' : newDigit;
								someDigitChanged = true;
							}
						}
					}
					if (!someDigitChanged) {
						// if no digits were changed then the digits animation is finished
						digitsAnimationFinished = true;
						// and we set up variables for the last animation phase
						trapezoidTargetPositionY = Gdx.graphics.getHeight()
								- blackTrapezoid.getRegionHeight() * vScale + (int)(vScale * 8.75);
						trapezoidVelocityY = 30.0f;
					}
				}
			}
		} else if (trapezoidY < trapezoidTargetPositionY) { 
			// digitsAnimationFinished is true
			trapezoidY += trapezoidVelocityY * delta;
			digitsYoffset += 16 * delta;
		}
	}

	@Override
	public void draw() {
		super.draw();
		batch.begin();
		batch.draw(background, 0, 0,
				background.getRegionWidth()*hScale, background.getRegionHeight()*vScale);
		
		for (int row = chipsXcoords.length - 1; row >= 0; row--) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				// draw each chip image using its respective x coordinate
				batch.draw(chipsImages[row][col], chipsXcoords[row][col] * hScale, 0,
						chipsImages[row][col].getRegionWidth() * hScale,
						chipsImages[row][col].getRegionHeight() * vScale);
			}
		}
		
		batch.draw(blackTrapezoid, trapezoidX, trapezoidY,
				blackTrapezoid.getRegionWidth() * hScale,
				blackTrapezoid.getRegionHeight() * vScale);
		
		/* start drawing the digits by representing each sub-array
		 * of the char 2D array as a string to make a line */
		if (trapezoidAnimationFinished) {
			for (int line = 0; line < renderDigits.length; line++) {
				titleFont.draw(batch, String.copyValueOf(digits[line]),
						trapezoidX + 14 * hScale,
						trapezoidY + blackTrapezoid.getRegionHeight() * vScale
						- digitsYoffset - line * vScale);
			}
		}
		if (digitsAnimationFinished) {
			buttons.drawButtons(batch);
		}
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	/* Hard code the values of the digits. No need to load them at startup. */
	public void setUpDigits() {
		String[] hardDigits = {
		"596972977743259947688474379486655786637395578865344844467769537365554898469487937296873827766589649547459244824799383424",
		"369859943644325273493524869689583754252833354359497822367543586333754874966537275335259456276985975988242259947947593355",
		"973554957769585754779622675849786239746678872667724985459276256339835729434937973274383562872474666763247759323552655762",
		"273398229499599455475362229643769466638226482887765357742443923568665976237772593228543843296927926328238327685829457479",
		"523558624000108358010188101197111010110001146710011111110051010094521101129799242111015835236510107366723935449257798854",
		"694595547800007864011174110152011016824000112311010101110151011045850010199949651011110387839200119263634838328628789444",
		"584564524790011890111473000148000002865010015532771001127480101082990011169494811001111177289611115668553445997349593687",
		"549444488390000100101665110049010106110010008476340011163261010048671111164326000112411005848511006227762252279773757623",
		"894252639724011010003658001046111003000107564752971111093991000039681011077860010082300111824910004383644883296733626838",
		"484857443735501101096279000096111019400110435729950001143391000143840001194811000473560000044610114526388632825744949552",
		"652955353645411010036846100077001047623110083658631010054420101001010100089111109470001001133200010000000005443995327953",
		"265245742875621000787386110169101085562300113655490100064449011000111101149000084861111001053910100011000112792888433446",
		"729595873723762262929333734933993475238458866249870010093449767652645533444723422967588772375479435873524392572928729455",
		"872539963586832377959363222524269372625634862695280000097531111011000000371101934539010431011551001752385754358258344528",
		"733852327399946936734752745693727974584798747698420112734590010011010001489011174531111860011520010584589929488384672644",
		"586935836742427586837224528936498634755437244348220865369470101133327972542010115920100460001531010829695285582848284878",
		"846979242673964439647757834434659749639659899374756727334531001107275223799301003600118560111520001929455374934954382645",
		"444529288946699863292726892683264663385542272755829348553431100111111111766841011111006460100541110358879749339553787492",
		"579763436976647249579736652295753748789635534566854743779530011049262379869831000000149550001550010948687848738522596226",
		"662896589385257557979292639993559845979979988372933369758820010116569484948295001011765270100560100101011101362565296646",
		"493922375929797427979353854752649348682657678225975552466771010011000110769622911006474770110591001010110000388527747423",
		"786399329794574983543685658794586977325386288763966889726283287654852937583662429465962224577784629749955752777362687646",
		"697747829227728697347264938253443235737259644264324642753225656484378648485442589556428237465889277255259799526964624796",
		"664634573844599552462589334982279624285376728635653273479473858295463385537367576429939878435629848947428452884499474677",
		"376634747837874923499348636255496342795227357528538924828629355367965328285778264234522923932652688633268938292995787736",
		"458488766384229565555992975579922654428787655856672688574274232544482553343334777358547373679832624772567886979422833633"
		};
		renderDigits = hardDigits;
	}
}
