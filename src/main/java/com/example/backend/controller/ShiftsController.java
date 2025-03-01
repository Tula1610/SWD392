package com.example.backend.controller;

import com.example.backend.model.Shifts;
import com.example.backend.service.ShiftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftsController {

    @Autowired
    private ShiftsService shiftsService;

    @GetMapping
    public List<Shifts> getAllShifts() {
        return shiftsService.getAllShifts();
    }

    @GetMapping("/{shiftsID}")
    public ResponseEntity<Shifts> getShiftsById(@PathVariable String shiftsID) {
        return shiftsService.getShiftsById(shiftsID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeID}")
    public List<Shifts> getShiftsByEmployeeId(@PathVariable String employeeID) {
        return shiftsService.getShiftsByEmployeeId(employeeID);
    }

    @GetMapping("/creator/{userID}")
    public List<Shifts> getShiftsByCreatorId(@PathVariable String userID) {
        return shiftsService.getShiftsByCreatorId(userID);
    }

    @PostMapping
    public Shifts createShifts(@RequestBody Shifts shifts) {
        return shiftsService.createShifts(shifts);
    }

    @PutMapping("/{shiftsID}")
    public ResponseEntity<Shifts> updateShifts(@PathVariable String shiftsID, @RequestBody Shifts shifts) {
        try {
            Shifts updatedShifts = shiftsService.updateShifts(shiftsID, shifts);
            return ResponseEntity.ok(updatedShifts);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{shiftsID}")
    public ResponseEntity<Void> deleteShifts(@PathVariable String shiftsID) {
        shiftsService.deleteShifts(shiftsID);
        return ResponseEntity.ok().build();
    }
}