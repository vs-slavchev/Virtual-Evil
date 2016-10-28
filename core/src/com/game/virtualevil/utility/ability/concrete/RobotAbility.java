package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;

public class RobotAbility extends Ability {

    public RobotAbility(String abilityName, GameCharacter character) {
        super(abilityName, character, AbilityConstants.ROBOT_CD);
    }

    @Override
    public void useAbility() {


    }

}
