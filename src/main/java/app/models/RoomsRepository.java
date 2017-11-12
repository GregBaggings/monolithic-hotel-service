package app.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    List<Rooms> findAll();
    List<Rooms> findByhotelid(int hotelid);

}
