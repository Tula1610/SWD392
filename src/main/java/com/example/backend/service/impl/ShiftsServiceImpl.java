package com.example.backend.service.impl;

import com.example.backend.model.Shifts;
import com.example.backend.repository.ShiftsRepository;
import com.example.backend.service.ShiftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftsServiceImpl implements ShiftsService {

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Override
    public Shifts createShifts(Shifts shifts) {
        return shiftsRepository.save(shifts);
    }

    @Override
    public Optional<Shifts> getShiftsById(String shiftsID) {
        return shiftsRepository.findById(shiftsID);
    }

    @Override
    public List<Shifts> getAllShifts() {
        return shiftsRepository.findAll();
    }

    @Override
    public List<Shifts> getShiftsByEmployeeId(String employeeID) {
        return shiftsRepository.findByEmployee_EmployeeID(employeeID);
    }

    @Override
    public List<Shifts> getShiftsByCreatorId(String userID) {
        return shiftsRepository.findByCreatedBy_UserID(userID);
    }

    @Override
    public Shifts updateShifts(String shiftsID, Shifts shifts) {
        shifts.setShiftsID(shiftsID);
        return shiftsRepository.save(shifts);
    }

    @Override
    public void deleteShifts(String shiftsID) {
        shiftsRepository.deleteById(shiftsID);
    }
}