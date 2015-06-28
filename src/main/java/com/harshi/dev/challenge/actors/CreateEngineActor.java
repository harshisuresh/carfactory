package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.Engine;
import com.harshi.dev.challenge.domain.Message;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateEngineActor extends AbstractActor {

    private volatile boolean stop;
    private final Random random = new Random();

    public CreateEngineActor() {
        receive(ReceiveBuilder.
                match(Message.class, message -> handleMessage(message)).
                build());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            System.out.println("Engines produced");
            produceEngines();
        } else{
            System.out.println("Stopped producing engines");
        }
    }

    private void produceEngines(){

        while(!stop){
            final ActorRef engineFilter = getContext().actorOf(Props.create(FilterEngineActor.class), "engineFilter");

            final Inbox inbox = Inbox.create(getContext().system());
            for (int i = 10000 ; i < 10000000; i++) {
                Engine engine = new Engine("Engine:"+ UUID.randomUUID().toString(), random.nextBoolean());
                inbox.send(engineFilter, engine);
            }
        }
    }

}
