package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.CoachWork;
import com.harshi.dev.challenge.domain.Message;
import com.harshi.dev.challenge.domain.Wheel;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateWheelActor extends AbstractActor {
    private volatile boolean stop;
    private final Random random = new Random();
    public CreateWheelActor(){
        receive(ReceiveBuilder.match(Message.class, message -> handleMessage(message)).build());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            System.out.println("Wheel produced");
            produceWheels();
        } else{
            System.out.println("Stopped producing wheels");
        }
    }

    private void produceWheels(){
        while(!stop){
            final ActorRef wheelFilter = getContext().actorOf(Props.create(FilterWheelActor.class), "wheelFilter");

            final Inbox inbox = Inbox.create(getContext().system());
            for (int i = 1 ; i < 20; i++) {
                Wheel wheel = new Wheel("Wheel:" + UUID.randomUUID().toString(), random.nextBoolean());
                inbox.send(wheelFilter, wheel);
            }
        }
    }
}
