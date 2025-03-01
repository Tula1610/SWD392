package com.example.backend.service;

import com.example.backend.model.mongoDB.Attendance;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    Attendance createAttendance(Attendance attendance);
    Optional<Attendance> getAttendanceById(String attendanceID);
    List<Attendance> getAllAttendances();
    List<Attendance> getAttendancesByEmployeeId(String employeeID);
    List<Attendance> getAttendancesByShiftsId(String shiftsID);
    Attendance updateAttendance(String attendanceID, Attendance attendance);
    void deleteAttendance(String attendanceID);
}