package com.example.backend.producer_consumer_backend.main;
import com.example.backend.producer_consumer_backend.Controller.Dto;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    private static Dto dto;
    private static WebSocketStompClient client = new WebSocketStompClient(new StandardWebSocketClient());
    private static StompSession session;
    public static void main(String [] args){
        Queue prod = new Queue(null);
        List<Queue> qList = new ArrayList<>();
        qList.add(prod);
        DefaultMachine m0 = new DefaultMachine(qList);
        DefaultMachine m1 = new DefaultMachine(qList);
        m1.id = 1;
        List<DefaultMachine> machines = new ArrayList<>();
        machines.add(m0);
        List<DefaultMachine> machines2 = new ArrayList<>();
        machines2.add(m1);
        Queue q = new Queue(machines);
        Queue q2 = new Queue(machines2);
        q.id = 1;
        q2.id = 2;
        Thread t1 = new Thread(m0);
        Thread t2 = new Thread(m1);

        q.enqueue(new Product(1));
        q.enqueue(new Product(2));

        q2.enqueue(new Product(3));

        q2.enqueue(new Product(4));
        t1.start();
        t2.start();
    }
    public static void start(List<DefaultMachine> machines, List<Queue> queues) throws ExecutionException, InterruptedException {
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        client.setMessageConverter(new JacksonJsonMessageConverter());
        Main.session = client.connectAsync("ws://localhost:8080/server", sessionHandler).get();
        Main.dto.setMachines(machines);
        Main.dto.setQueues(queues);
        for(DefaultMachine machine: machines) {
            Thread t = new Thread(machine);
            t.start();
        }
    }

    public static void notifyMain(){
       session.send("app/update", dto);
    }
   static class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe("/topic/update", this);
        }


        @Override
        public Type getPayloadType(StompHeaders headers) {
            // Defines the type the message payload should be converted to
            // Return your message class type, e.g., return MyMessage.class;
            return Dto.class; // Or your specific message DTO
        }

    }
}
