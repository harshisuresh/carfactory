package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.Car;
import com.harshi.dev.challenge.domain.Colour;

/**
 * Created by harshitha.suresh on 30/06/2015.
 */
public class PaintRedActor extends AbstractActor {
    public PaintRedActor(){
        receive(ReceiveBuilder.match(Car.class, this::paint).build());
    }

    private void paint(Car car){
        car.setColour(Colour.RED);
        final ActorRef carMerger = getContext().actorFor("akka://carfactory/user/carMerger");
        final Inbox inbox = Inbox.create(getContext().system());
        inbox.send(carMerger, car);
    }
}
