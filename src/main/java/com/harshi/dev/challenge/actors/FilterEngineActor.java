package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.Engine;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class FilterEngineActor extends AbstractActor {

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    public FilterEngineActor(){
        receive(ReceiveBuilder.match(Engine.class, this::filterEngine).build());
    }

    private void filterEngine(Engine  engine){
        if(engine.isFaulty()){
            LOG.info("Filtered faulty engine {}", engine);
            return;
        }
        LOG.info("Filtered engine {}", engine);
        final ActorRef carAssembler = getContext().actorFor("akka://carfactory/user/carAssembler");
        final Inbox inbox = Inbox.create(getContext().system());
        inbox.send(carAssembler, engine);
    }
}

