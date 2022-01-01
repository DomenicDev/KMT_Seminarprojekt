package de.cassisi.kmt.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculatorController {

    private final DiscoveryClient discoveryClient;

    @Value("${myval}")
    private String myVal;

    private final ServletWebServerApplicationContext context;

    public CalculatorController(DiscoveryClient discoveryClient, ServletWebServerApplicationContext context) {
        this.discoveryClient = discoveryClient;
        this.context = context;
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/")
    public String getGreeting() {
        int port = context.getWebServer().getPort();
        return "Port: " + port + " - Message: " + myVal;
    }

    @GetMapping("/calculator")
    public String getController() {
        return "Hello from the calculator!";
    }

}
