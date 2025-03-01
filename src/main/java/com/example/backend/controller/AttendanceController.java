package com.example.backend.controller;

import com.example.backend.model.mongoDB.Attendance;
import com.example.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/{attendanceID}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable String attendanceID) {
        return attendanceService.getAttendanceById(attendanceID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeID}")
    public List<Attendance> getAttendancesByEmployeeId(@PathVariable String employeeID) {
        return attendanceService.getAttendancesByEmployeeId(employeeID);
    }

    @GetMapping("/shifts/{shiftsID}")
    public List<Attendance> getAttendancesByShiftsId(@PathVariable String shiftsID) {
        return attendanceService.getAttendancesByShiftsId(shiftsID);
    }

    @PostMapping
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.createAttendance(attendance);
    }

    @PutMapping("/{attendanceID}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable String attendanceID, @RequestBody Attendance attendance) {
        try {
            Attendance updatedAttendance = attendanceService.updateAttendance(attendanceID, attendance);
            return ResponseEntity.ok(updatedAttendance);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{attendanceID}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String attendanceID) {
        attendanceService.deleteAttendance(attendanceID);
        return ResponseEntity.ok().build();
    }
}