package com.harshi.dev.challenge.factory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.harshi.dev.challenge.actors.*;
import com.harshi.dev.challenge.domain.Message;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CarFactory {

    public static void main(String args[]){
        final ActorSystem system = ActorSystem.create("carfactory");

        final ActorRef engineCreator = system.actorOf(Props.create(CreateEngineActor.class), "engineCreator");
        final ActorRef coachworkCreator = system.actorOf(Props.create(CreateCoachworkActor.class), "coachworkCreator");
        final ActorRef wheelCreator = system.actorOf(Props.create(CreateWheelActor.class), "wheelCreator");
        final ActorRef carAssembler = system.actorOf(Props.create(AssembleCarActor.class), "carAssembler");
        final ActorRef mergerActor = system.actorOf(Props.create(MergerActor.class), "carMerger");
        System.out.println(carAssembler.path());
        engineCreator.tell(Message.START, ActorRef.noSender());
        coachworkCreator.tell(Message.START, ActorRef.noSender());
        wheelCreator.tell(Message.START, ActorRef.noSender());

    }
}
