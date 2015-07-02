package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.harshi.dev.challenge.domain.Car;
import com.harshi.dev.challenge.domain.CoachWork;
import com.harshi.dev.challenge.domain.Engine;
import com.harshi.dev.challenge.domain.Wheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class AssembleCarActor extends UntypedActor {
    Router router;
    {

        ActorRef r1 = getContext().actorOf(Props.create(PaintBlueActor.class));
        ActorRef r2 = getContext().actorOf(Props.create(PaintGreenActor.class));
        ActorRef r3 = getContext().actorOf(Props.create(PaintRedActor.class));
        router = new Router(new RoundRobinRoutingLogic(), Arrays.asList(new ActorRefRoutee(r1), new ActorRefRoutee(r2), new ActorRefRoutee(r3)));
    }

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    private BlockingQueue<Wheel> wheels = new ArrayBlockingQueue<Wheel>(10000);
    private BlockingQueue<Engine> engines = new ArrayBlockingQueue<Engine>(10000);
    private BlockingQueue<CoachWork> coachWorks = new ArrayBlockingQueue<CoachWork>(10000);

    private Long serialNumber = 0L;


    @Override
    public void onReceive(Object message) throws Exception {


        LOG.info("Car Assembler");
        if(message instanceof Wheel){
            wheels.add((Wheel)message);
        }
        else if(message instanceof Engine){
            engines.add((Engine)message);
        }
        else if(message instanceof CoachWork){
            coachWorks.add((CoachWork)message);
        }

        if(wheels.size() >= 4 && engines.size() >= 1 && coachWorks.size() >= 1) {
            Car car = new Car(serialNumber++, engines.take(), coachWorks.take(), Arrays.asList(wheels.take(), wheels.take(), wheels.take(), wheels.take()));
            router.route(car, ActorRef.noSender());
        }

    }
}
