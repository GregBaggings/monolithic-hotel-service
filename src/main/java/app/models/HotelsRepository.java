package app.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
public interface HotelsRepository extends CrudRepository<Hotel, Integer> {
    List<Hotel> findAll();

    List<Hotel> findAllByCity(String city);

    Hotel findByhotelName(String hotelName);

}
