package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.customException.BusinessException;
import com.Hotels.YourHome.dao.HotelRepository;
import com.Hotels.YourHome.models.Hotel;
import com.Hotels.YourHome.projections.HotelView;
import com.Hotels.YourHome.service.FileStorageService;
import com.Hotels.YourHome.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.Hotels.YourHome.constants.MyConstants.NOT_CREATED;
import static com.Hotels.YourHome.constants.MyConstants.NOT_UPDATED;

@Service
public class HotelServiceImp implements HotelService {

    @Autowired
    HotelRepository hotelRepo;

    @Autowired
    FileStorageService fileStorageService;


    @Override
    public void addHotel(Hotel hotel, MultipartFile file) throws IOException {
        Timestamp t = Timestamp.from(Instant.now());
        hotel.setC_time(t.toLocalDateTime().toString());
        hotel.setU_time(t.toLocalDateTime().toString());
        try {
            String fileName = fileStorageService.saveImg(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(fileName)
                    .toUriString();
            hotel.setImagePath(fileDownloadUri);
            hotelRepo.save(hotel);
        }
        catch (Exception e){
            throw new BusinessException(404,NOT_CREATED);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        hotelRepo.deleteHotel(id);
    }

    @Override
    public List<HotelView> getHotels() {
        return hotelRepo.showView();
    }

    @Override
    @Transactional
    public void update(int id, String address) {
        Timestamp t = Timestamp.from(Instant.now());
        hotelRepo.updateHotel(id,address,t.toLocalDateTime().toString());
    }

    @Override
    public List<HotelView> getHotelByCity(String city) {
        return hotelRepo.GetHotelByCity(city);
    }

    @Override
    public Optional<HotelView> getHotelByName(String name) {
        return  hotelRepo.getHotelByName(name);
    }

    @Override
    @Transactional
    public void updateImage(int id, MultipartFile file) throws IOException {
        try {
            String fileName = fileStorageService.saveImg(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(fileName)
                    .toUriString();
            hotelRepo.updateImage(id,fileDownloadUri);
        }
        catch (Exception e){
            System.out.print(e);
            throw new BusinessException(404,NOT_UPDATED);
        }
    }
}
