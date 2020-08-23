package com.backend.demo.services;

import com.backend.demo.models.Device;
import com.backend.demo.models.Measuring;
import com.backend.demo.repos.MeasuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasuringService {
    private MeasuringRepository measuringRepository;

    @Autowired
    public MeasuringService(MeasuringRepository measuringRepository) {
        this.measuringRepository = measuringRepository;
    }

    public List<Measuring> findByDevice(Device device){
        return (List<Measuring>) measuringRepository.findByDevice(device);
    }
}
