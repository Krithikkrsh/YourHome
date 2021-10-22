package com.Hotels.YourHome.controller;

import com.Hotels.YourHome.models.Hotel;
import com.Hotels.YourHome.projections.HotelView;
import com.Hotels.YourHome.responses.UpdateResponse;
import com.Hotels.YourHome.service.FileStorageService;
import com.Hotels.YourHome.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HotelController {

    @Autowired
    HotelService hotelS;

    @Autowired
    FileStorageService fileStorageService;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "admin/hotel-management/hotel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Add Hotel")
    public ResponseEntity<Void> addHotel(@RequestParam(value = "hotels") String hotels
            , @RequestParam(value="file") MultipartFile file) throws IOException {
        Hotel hotel = objectMapper.readValue(hotels,Hotel.class);
        hotelS.addHotel(hotel,file);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc","Add hotel by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @DeleteMapping("/hotel-management/{id}/delete")
    @ApiOperation(value = "Delete Hotel by id")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") int id)
    {
        hotelS.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("update-hotel/{id}/{address}")
    @ApiOperation(value = "Update Hotels Address only by id")
    public ResponseEntity<Void> update(@PathVariable("id") int id,@PathVariable("address") String address)
    {
        hotelS.update(id,address);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("admin/hotel-management/hotels")
    @ApiOperation(value = "Getting all the hotels")
    public ResponseEntity<List<HotelView>> getHotels()
    {
        List<HotelView> hotel =  hotelS.getHotels();
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("hotel-management/hotel")
    @ApiOperation(value = "Get list of hotels by city")
    public ResponseEntity<List<HotelView>> getHotelbycity(@RequestParam("city") String city)
    {
        List<HotelView> hotel = hotelS.getHotelByCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("hotel-management/hotel/search")
    @ApiOperation(value = "Get list of hotels by name")
    public ResponseEntity<Optional<HotelView>> getHotelbyname(@RequestParam("name") String name)
    {
        Optional<HotelView> hotel = hotelS.getHotelByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @PutMapping("update-hotel/{id}")
    @ApiOperation(value = "Update Hotels Image only by id")
    public ResponseEntity<?> updateImage(@PathVariable("id") int id,@RequestParam(value = "file") MultipartFile file)
    {
        try {
            hotelS.updateImage(id, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse());
    }

    @GetMapping({"downloadFile/{fileName}"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
        Resource resource = fileStorageService.loadImg(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

}
