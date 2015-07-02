package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.CoachWork;
import com.harshi.dev.challenge.domain.Message;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateCoachworkActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    private volatile boolean stop;
    private final Random random = new Random();
    public CreateCoachworkActor(){
        receive(ReceiveBuilder.match(Message.class, message -> handleMessage(message)).build());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            LOG.info("Coachworks produced");
            produceCoachworks();
        } else{
            LOG.info("Stopped producing coachworks");
        }
    }

    private void produceCoachworks(){
        final ActorRef coachworkFilter = getContext().actorOf(Props.create(FilterCoachWorksActor.class), "coachworkFilter");

        final Inbox inbox = Inbox.create(getContext().system());
        while(!stop){

            for (int i = 1 ; i < 20; i++) {
                CoachWork coachWork = new CoachWork("Coach:"+UUID.randomUUID().toString(), random.nextBoolean());
                inbox.send(coachworkFilter, coachWork);
            }
        }
    }
}
