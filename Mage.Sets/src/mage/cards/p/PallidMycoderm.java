
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PallidMycoderm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature you control that's a Fungus or a Saproling");
    private static final FilterControlledCreaturePermanent filterSaproling = new FilterControlledCreaturePermanent("a Saproling");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(SubType.FUNGUS.getPredicate(), SubType.SAPROLING.getPredicate()));
        filterSaproling.add(SubType.SAPROLING.getPredicate());
    }

    public PallidMycoderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, put a spore counter on Pallid Mycoderm.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        // Remove three spore counters from Pallid Mycoderm: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
        // Sacrifice a Saproling: Each creature you control that's a Fungus or a Saproling gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostAllEffect(1,1,Duration.EndOfTurn, filter, false),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filterSaproling, false))));
    }

    private PallidMycoderm(final PallidMycoderm card) {
        super(card);
    }

    @Override
    public PallidMycoderm copy() {
        return new PallidMycoderm(this);
    }
}
