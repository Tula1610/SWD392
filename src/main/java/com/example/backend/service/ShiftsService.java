package com.example.backend.service;

import com.example.backend.model.Shifts;
import java.util.List;
import java.util.Optional;

public interface ShiftsService {
    Shifts createShifts(Shifts shifts);
    Optional<Shifts> getShiftsById(String shiftsID);
    List<Shifts> getAllShifts();
    List<Shifts> getShiftsByEmployeeId(String employeeID);
    List<Shifts> getShiftsByCreatorId(String userID);
    Shifts updateShifts(String shiftsID, Shifts shifts);
    void deleteShifts(String shiftsID);
}