package com.example.demo.controller;
import com.example.demo.model.Greeting;
import com.example.demo.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    private final SimpMessagingTemplate template;

    @Autowired
    public GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
        for(int i = 0; i<10; i++) {
            Thread.sleep(1000); // simulated delay
            template.convertAndSend("/topic/greetings", message);
        }
    }

}