package com.tom.demo.greeter;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.Objects;

public class Greeter extends AbstractBehavior<Greeter.Greet> {
    public static final class Greet {
        public final String whom;
        public final ActorRef<Greeted> replyTo;

        public Greet(String whom, ActorRef<Greeted> replyTo) {
            this.whom = whom;
            this.replyTo = replyTo;
        }
    }

    public static final class Greeted {
        public final String whom;
        public final ActorRef<Greet> from;

        public Greeted(String whom, ActorRef<Greet> from) {
            this.whom = whom;
            this.from = from;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj || getClass() != obj.getClass()) return false;
            Greeted greeted = (Greeted) obj;
            return Objects.equals(whom, greeted.whom) && Objects.equals(from, greeted.from);
        }

        @Override
        public int hashCode() {
            return Objects.hash(whom,from);
        }

        @Override
        public String toString() {
            return "Greeted{" + "whom='" + whom + "', from='" + from + "'}";
        }
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(Greeter::new);
    }

    private Greeter(ActorContext<Greet> context) {super(context);}

    @Override
    public Receive<Greet> createReceive() {
        return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();
    }

    private Behavior<Greet> onGreet(Greet command) {
        getContext().getLog().info("Hello {}!", command.whom);
        command.replyTo.tell(new Greeted(command.whom,getContext().getSelf()));
        return this;
    }
}
