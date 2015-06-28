package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import com.harshi.dev.challenge.domain.Engine;
import com.harshi.dev.challenge.domain.Wheel;


public class FilterWheelActor extends AbstractActor{
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    public FilterWheelActor(){
        receive(ReceiveBuilder.match(Wheel.class, this::filterWheel).build());
    }

    private void filterWheel(Wheel  wheel){
        if(wheel.isFaulty()){
            LOG.info("Filtered faulty wheel {}", wheel);
            return;
        }
        LOG.info("Filtered wheel {}", wheel);
        final ActorRef carAssembler = getContext().actorSelection("carAssembler").anchor();
        final Inbox inbox = Inbox.create(getContext().system());
        inbox.send(carAssembler, wheel);
        LOG.info("************"+carAssembler.path().toString());
    }
}
