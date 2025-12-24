package com.example.backend.producer_consumer_backend.Controller;

import com.example.backend.producer_consumer_backend.main.DefaultMachine;
import com.example.backend.producer_consumer_backend.main.Main;
import com.example.backend.producer_consumer_backend.main.Queue;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ServerController {

    @MessageMapping("/start")
    public void start(Dto dto) throws Exception{
        Main.start(dto.getMachines(), dto.getQueues(), dto.getNumberOfItems());
    }

    @MessageMapping("update")
    @SendTo("/topic/update")
    public Dto update(Dto dto){
        return dto;
    }

}
