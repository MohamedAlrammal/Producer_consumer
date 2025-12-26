package com.example.backend.producer_consumer_backend.main;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultMachine implements MachineInterface, Runnable{

    private List<Queue> readyQueues = new ArrayList<>();
    private List<Queue> productionQueues = new ArrayList<>();
    public int id;
    private Product product;

    public DefaultMachine(){

    }

    public DefaultMachine(List<Queue> productionQueues){
        this.productionQueues = productionQueues;
    }

    public synchronized void consume(Queue q){
            System.out.println("machine " + id  + "is consuming from queue " + q.id);
            product = q.dequeue();
            Main.notifyMain();
            readyQueues.remove(q);
    }

    public synchronized void produce(){
        Queue q = productionQueues.get((int)(Math.random() * productionQueues.size()));
        System.out.println("machine " + id  + " is producing to queue " + q.id);
        q.enqueue(product);
        product = null;
        Main.notifyMain();
    }

    @Override
    public synchronized void update(Queue q) {
        readyQueues.add(q);
        notify();
        System.out.println("notifying machine " + id);
    }

    public synchronized void run() {
        while(true){
            while (!readyQueues.isEmpty()) {
                Queue q = readyQueues.get(0);
                synchronized (q) {
                    if (q.isEmpty()) {
                        readyQueues.remove(q);
                    } else {
                        consume(q);
                        try {
                            System.out.println("machine " + id + " is processing product " + product.id);
                            Thread.sleep((long) (Math.random() * 10));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        produce();
                    }
                }
            }
            try {
                System.out.println("waiting machine " + id);
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
