package com.Hotels.YourHome.service;

import com.Hotels.YourHome.models.Hotel;
import com.Hotels.YourHome.projections.HotelView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HotelService {

    void addHotel(Hotel hotel, MultipartFile file) throws IOException;
    void delete(int id);
    List<HotelView> getHotels();
    void update(int id,String address);
    List<HotelView> getHotelByCity(String city);
    Optional<HotelView> getHotelByName(String name);
    void updateImage(int id, MultipartFile path) throws IOException;
}
