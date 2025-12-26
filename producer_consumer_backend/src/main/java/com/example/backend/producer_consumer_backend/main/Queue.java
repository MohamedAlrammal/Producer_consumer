package com.example.backend.producer_consumer_backend.main;

import lombok.Data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
public class Queue {
    private Deque<Product> queue = new ArrayDeque<>();
    private List<DefaultMachine> machines;
    public int id;
    public Queue(){

    }
    public Queue(List<DefaultMachine> machines){
        this.machines = machines;
    }
    public void enqueue(Product product){
        queue.add(product);
        System.out.println("queue " + id  + " contains " + queue.toString());
        alert();
        Main.notifyMain();
    }

    public Product dequeue(){
        Product p = queue.poll();
        System.out.println("queue " + id  + " contains " + queue.toString());
        return p;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void alert() {
        if (machines != null) {
            for (DefaultMachine machine : machines) {
                machine.update(this);
            }
        }
    }
}
