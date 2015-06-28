package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.CoachWork;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class FilterCoachWorksActor extends AbstractActor{
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    public FilterCoachWorksActor(){
        receive(ReceiveBuilder.match(CoachWork.class, this::filterCoachwork).build());
    }

    private void filterCoachwork(CoachWork coachwork){
        if(coachwork.isFaulty()){
            LOG.info("Filtered faulty coachwork {}", coachwork);
            return;
        }
        LOG.info("Filtered coachwork {}", coachwork);
        final ActorRef carAssembler = getContext().actorSelection("carAssembler").anchor();
        final Inbox inbox = Inbox.create(getContext().system());
        inbox.send(carAssembler, coachwork);
    }
}
