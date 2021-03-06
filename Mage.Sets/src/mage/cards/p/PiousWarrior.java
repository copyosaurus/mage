
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public final class PiousWarrior extends CardImpl {

    public PiousWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

		// Whenever Pious Warrior is dealt combat damage, you gain that much life.
        this.addAbility(new PiousWarriorTriggeredAbility());
    }

    private PiousWarrior(final PiousWarrior card) {
        super(card);
    }

    @Override
    public PiousWarrior copy() {
        return new PiousWarrior(this);
    }
}

class PiousWarriorTriggeredAbility extends TriggeredAbilityImpl {

    public PiousWarriorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PiousWarriorGainLifeEffect());
    }

    public PiousWarriorTriggeredAbility(final PiousWarriorTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public PiousWarriorTriggeredAbility copy() {
        return new PiousWarriorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId) && ((DamagedCreatureEvent)event).isCombatDamage() ) {
   			this.getEffects().get(0).setValue("damageAmount", event.getAmount());
       		return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt combat damage, " + super.getRule();
    }
}


class PiousWarriorGainLifeEffect extends OneShotEffect {

	public PiousWarriorGainLifeEffect() {
		super(Outcome.GainLife);
		staticText = "you gain that much life";
	}

    public PiousWarriorGainLifeEffect(final PiousWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PiousWarriorGainLifeEffect copy() {
        return new PiousWarriorGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game, source);
        }
        return true;
    }

}
