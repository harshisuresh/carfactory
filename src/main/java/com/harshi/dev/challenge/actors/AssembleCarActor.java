package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.harshi.dev.challenge.domain.CoachWork;
import com.harshi.dev.challenge.domain.Engine;
import com.harshi.dev.challenge.domain.Wheel;


public class AssembleCarActor extends UntypedActor {

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Wheel){
            Wheel wheel = (Wheel)message;
            LOG.info("Received wheel");
        }
        else if(message instanceof Engine){
            Engine engine = (Engine)message;
            LOG.info("Received engine");
        }
        else if(message instanceof CoachWork){
            CoachWork coachWork = (CoachWork)message;
            LOG.info("Received coachwork");
        }

    }
}
