package com.example.backend.controller;

import com.example.backend.model.mongoDB.TimeAccumulation;
import com.example.backend.service.TimeAccumulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/time-accumulations")
public class TimeAccumulationController {

    @Autowired
    private TimeAccumulationService timeAccumulationService;

    @GetMapping
    public List<TimeAccumulation> getAllTimeAccumulations() {
        return timeAccumulationService.getAllTimeAccumulations();
    }

    @GetMapping("/{accumulationID}")
    public ResponseEntity<TimeAccumulation> getTimeAccumulationById(@PathVariable String accumulationID) {
        return timeAccumulationService.getTimeAccumulationById(accumulationID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TimeAccumulation createTimeAccumulation(@RequestBody TimeAccumulation timeAccumulation) {
        return timeAccumulationService.createTimeAccumulation(timeAccumulation);
    }

    @PutMapping("/{accumulationID}")
    public ResponseEntity<TimeAccumulation> updateTimeAccumulation(@PathVariable String accumulationID, @RequestBody TimeAccumulation timeAccumulation) {
        try {
            TimeAccumulation updated = timeAccumulationService.updateTimeAccumulation(accumulationID, timeAccumulation);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{accumulationID}")
    public ResponseEntity<Void> deleteTimeAccumulation(@PathVariable String accumulationID) {
        timeAccumulationService.deleteTimeAccumulation(accumulationID);
        return ResponseEntity.ok().build();
    }
}