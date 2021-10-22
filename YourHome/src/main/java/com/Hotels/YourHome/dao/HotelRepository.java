package com.Hotels.YourHome.dao;

import com.Hotels.YourHome.models.Hotel;
import com.Hotels.YourHome.projections.HotelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {


    @Query(value = "Select * from hotel_view order by hotel_name",nativeQuery = true)
    List<HotelView> showView();

    @Modifying
    @Query(value = "update hotels set flag=true where id=?1",nativeQuery = true)
    void deleteHotel(int id);

    @Modifying
    @Query(value = "update hotels set area=?2,updated_at=?3 where hotel_id=?1",nativeQuery = true)
    void updateHotel(int id,String address,String time);

   @Query(value= "select * from hotel_view where lower(city)=lower(?1) order by hotel_name", nativeQuery = true)
    List<HotelView> GetHotelByCity(String city);

    @Query(value= "select * from hotel_view where lower(hotel_name)=lower(?1)", nativeQuery = true)
    Optional<HotelView> getHotelByName(String name);

    @Modifying
    @Query(value = "update hotels set image_path=?2 where hotel_id=?1",nativeQuery = true)
    void updateImage(int id, String path);
}