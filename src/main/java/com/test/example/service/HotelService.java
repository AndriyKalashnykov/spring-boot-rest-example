package com.test.example.service;

import com.test.example.dao.jpa.HotelRepository;
import com.test.example.domain.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.CounterService;
//import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private HotelRepository hotelRepository;

//    @Autowired
//    CounterService counterService;
//
//    @Autowired
//    GaugeService gaugeService;

    public HotelService() {
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getHotel(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Hotel> getAllHotels(Integer page, Integer size) {
        Page pageOfHotels = hotelRepository.findAll(PageRequest.of(page, size));
        // example of adding to the /metrics
        if (size > 50) {
//            counterService.increment("test.HotelService.getAll.largePayload");
        }
        return pageOfHotels;
    }
}
