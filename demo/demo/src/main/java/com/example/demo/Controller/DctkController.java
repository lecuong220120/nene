package com.example.demo.Controller;

import com.example.demo.Service.DctkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class DctkController {
    @Autowired
    private DctkService dctkService;
    @GetMapping("/run")
    public String sendEmail() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            try {
                dctkService.playNow();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return "Run success";
    }
    @GetMapping("/stop")
    public String stop() {
        try {
            dctkService.stop();
        } catch (Exception e) {
            System.out.println("ERRORRRR Stopppp");
        }
        return "Stop success";
    }
}
