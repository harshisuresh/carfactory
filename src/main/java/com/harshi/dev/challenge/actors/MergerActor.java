package com.harshi.dev.challenge.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.harshi.dev.challenge.domain.Car;

/**
 * Created by harshitha.suresh on 30/06/2015.
 */
public class MergerActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    public MergerActor(){
        receive(ReceiveBuilder.match(Car.class, this::merge).build());
    }

    private void merge(Car car){
        LOG.info("Rolling out" + car);
    }
}
