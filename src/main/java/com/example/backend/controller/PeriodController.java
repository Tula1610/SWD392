package com.example.backend.controller;

import com.example.backend.model.mongoDB.Period;
import com.example.backend.enums.PeriodType;
import com.example.backend.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/periods")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping
    public List<Period> getAllPeriods() {
        return periodService.getAllPeriods();
    }

    @GetMapping("/{periodID}")
    public ResponseEntity<Period> getPeriodById(@PathVariable String periodID) {
        return periodService.getPeriodById(periodID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{periodType}")
    public List<Period> getPeriodsByType(@PathVariable PeriodType periodType) {
        return periodService.getPeriodsByType(periodType);
    }

    @GetMapping("/year/{year}")
    public List<Period> getPeriodsByYear(@PathVariable int year) {
        return periodService.getPeriodsByYear(year);
    }

    @PostMapping
    public Period createPeriod(@RequestBody Period period) {
        return periodService.createPeriod(period);
    }

    @PutMapping("/{periodID}")
    public ResponseEntity<Period> updatePeriod(@PathVariable String periodID, @RequestBody Period period) {
        try {
            Period updatedPeriod = periodService.updatePeriod(periodID, period);
            return ResponseEntity.ok(updatedPeriod);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{periodID}")
    public ResponseEntity<Void> deletePeriod(@PathVariable String periodID) {
        periodService.deletePeriod(periodID);
        return ResponseEntity.ok().build();
    }
}