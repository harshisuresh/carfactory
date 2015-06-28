package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.CoachWork;
import com.harshi.dev.challenge.domain.Message;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateCoachworkActor extends AbstractActor {
    private volatile boolean stop;
    private final Random random = new Random();
    public CreateCoachworkActor(){
        receive(ReceiveBuilder.match(Message.class, message -> handleMessage(message)).build());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            System.out.println("Coachworks produced");
            produceCoachworks();
        } else{
            System.out.println("Stopped producing coachworks");
        }
    }

    private void produceCoachworks(){
        while(!stop){
            final ActorRef coachworkFilter = getContext().actorOf(Props.create(FilterCoachWorksActor.class), "coachworkFilter");

            final Inbox inbox = Inbox.create(getContext().system());
            for (int i = 10000 ; i < 10000000; i++) {
                CoachWork coachWork = new CoachWork("Coach:"+UUID.randomUUID().toString(), random.nextBoolean());
                inbox.send(coachworkFilter, coachWork);
            }
        }
    }
}