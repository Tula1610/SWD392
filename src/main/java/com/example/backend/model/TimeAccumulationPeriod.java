package com.example.backend.model;

import com.example.backend.model.mongoDB.Period;
import com.example.backend.model.mongoDB.TimeAccumulation;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeAccumulationPeriod {

     int tapid;

     TimeAccumulation timeAccumulation;

     Period period;
}