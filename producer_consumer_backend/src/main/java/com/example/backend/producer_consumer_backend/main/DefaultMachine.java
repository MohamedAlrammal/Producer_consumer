package com.example.backend.producer_consumer_backend.main;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DefaultMachine implements MachineInterface, Runnable{
    private final List<Queue> readyQueues = new ArrayList<>();
    private List<Queue> productionQueues = new ArrayList<>();
    public int id;
    @Getter
    private Product product;

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
        Queue q = productionQueues.get(0);
        System.out.println("machine " + id  + " is producing to queue " + q.id);
        q.enqueue(product);
        product = null;
        Main.notifyMain();
    }

    @Override
    public synchronized void update(Queue q) {
        readyQueues.add(q);
        notify();
    }

    public synchronized void run() {
        while (!readyQueues.isEmpty()) {
            Queue q = readyQueues.get(0);
            synchronized (q){
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
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
