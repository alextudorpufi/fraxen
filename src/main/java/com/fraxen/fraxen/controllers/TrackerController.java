package com.fraxen.fraxen.controllers;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;

@RestController
public class TrackerController {

    private AtomicInteger totalVisits = new AtomicInteger();
    private Set<String> uniqueVisitors = ConcurrentHashMap.newKeySet();

    @GetMapping("/track")
    public void trackVisit(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        totalVisits.incrementAndGet();
        uniqueVisitors.add(ip);

        System.out.println("Visitor: " + ip + " | Agent: " + agent);
    }

    @GetMapping("/stats")
    public String stats() {
        return "Total visits: " + totalVisits.get() +
                ", Unique visitors: " + uniqueVisitors.size();
    }
}
