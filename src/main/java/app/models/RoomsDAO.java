package app.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
@RestController
public class RoomsDAO {

    private RoomsRepository repository;

    @Autowired
    public RoomsDAO(RoomsRepository repository) {
        super();
        this.repository = repository;
    }

    public List<Rooms> findAll() {
        return repository.findAll();
    }

    public List<Rooms> findByhotelid(int hotelid){
        return repository.findByhotelid(hotelid);
    }
}
