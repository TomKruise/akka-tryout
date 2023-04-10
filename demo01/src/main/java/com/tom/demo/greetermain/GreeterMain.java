package com.tom.demo.greetermain;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.tom.demo.greeter.Greeter;
import com.tom.demo.greeterbot.GreeterBot;

public class GreeterMain extends AbstractBehavior<GreeterMain.SayHello> {
    public static class SayHello{
        public final String name;

        public SayHello(String name) {
            this.name = name;
        }
    }

    private final ActorRef<Greeter.Greet> greeter;

    public static Behavior<SayHello> create() {
        return Behaviors.setup(GreeterMain::new);
    }

    private GreeterMain(ActorContext<SayHello> context) {
        super(context);
        greeter = context.spawn(Greeter.create(), "greeter");
    }

    @Override
    public Receive<SayHello> createReceive() {
        return newReceiveBuilder().onMessage(SayHello.class, this::onSayHello).build();
    }

    private Behavior<SayHello> onSayHello(SayHello command) {
        ActorRef<Greeter.Greeted> replyTo = getContext().spawn(GreeterBot.create(3),command.name);
        greeter.tell(new Greeter.Greet(command.name, replyTo));
        return this;
    }
}
