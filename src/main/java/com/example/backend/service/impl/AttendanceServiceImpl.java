package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Attendance;
import com.example.backend.repository.AttendanceRepository;
import com.example.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Optional<Attendance> getAttendanceById(String attendanceID) {
        return attendanceRepository.findById(attendanceID);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> getAttendancesByEmployeeId(String employeeID) {
        return attendanceRepository.findByEmployee_EmployeeID(employeeID);
    }

    @Override
    public List<Attendance> getAttendancesByShiftsId(String shiftsID) {
        return attendanceRepository.findByShifts_ShiftsID(shiftsID);
    }

    @Override
    public Attendance updateAttendance(String attendanceID, Attendance attendance) {
        attendance.setAttendanceID(attendanceID);
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(String attendanceID) {
        attendanceRepository.deleteById(attendanceID);
    }
}