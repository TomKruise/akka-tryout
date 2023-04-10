package com.tom.demo;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.tom.demo.greeter.Greeter;
import org.junit.ClassRule;
import org.junit.Test;

public class AkkaQuickStartTest {
    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testGreeterActorSendingOfGreeting() {
        TestProbe<Greeter.Greeted> testProbe = testKit.createTestProbe();
        ActorRef<Greeter.Greet> underTest = testKit.spawn(Greeter.create(), "greeter");
        underTest.tell(new Greeter.Greet("Tom", testProbe.getRef()));
        testProbe.expectMessage(new Greeter.Greeted("Tom", underTest));
    }
}
