package com.tom.demo;

import akka.actor.typed.ActorSystem;
import com.tom.demo.greetermain.GreeterMain;

public class AkkaQuickStart {
    public static void main(String[] args) {
        final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");

        greeterMain.tell(new GreeterMain.SayHello("Tom"));

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (Exception e) {

        } finally {
            greeterMain.terminate();
        }
    }
}
