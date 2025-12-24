package com.example.backend.producer_consumer_backend.Controller;

import com.example.backend.producer_consumer_backend.main.DefaultMachine;
import com.example.backend.producer_consumer_backend.main.Queue;
import lombok.Data;

import java.util.List;

@Data
public class Dto {
    private List<DefaultMachine> machines;
    private List<Queue> queues;
}
