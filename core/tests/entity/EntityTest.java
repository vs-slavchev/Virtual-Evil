package entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.entity.PlayerCharacter;

public class EntityTest {
	
	GameCharacter gc;
	
	@Before
	public void prepareCharacter(){
		gc = new PlayerCharacter();
	}

	@Test
	public void settingHealthShouldNotExceedLimits(){
		gc.setCurrentHealth(100);
		assertEquals("hp set to 100", 100, gc.getCurrentHealth());
		
		gc.setCurrentHealth(gc.getMaxHealth() + 50);
		assertEquals("hp should not exceed maximum", gc.getMaxHealth(), gc.getCurrentHealth());
		
		gc.setCurrentHealth(-50);
		assertEquals("hp should not be below 0", 0, gc.getCurrentHealth());
		
		gc.setCurrentHealth(0);
		assertEquals("hp set to 0", 0, gc.getCurrentHealth());
	}
	
	@Test
	public void modifyingHealthShouldNotExceedLimits(){
		
		gc.setCurrentHealth(0);
		gc.modifyHealth(gc.getMaxHealth() + 50);
		assertEquals("0 + max + 50 should be max", gc.getMaxHealth(), gc.getCurrentHealth());
		
		gc.setCurrentHealth(0);
		gc.modifyHealth(-50);
		assertEquals("0 - 50 should be 0", 0, gc.getCurrentHealth());
	}
}
