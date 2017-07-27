package app.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class hotelsDAO {

    private hotelsRepository repository;

    @Autowired
    public hotelsDAO(hotelsRepository countriesRepo) {
        super();
        this.repository = countriesRepo;
    }

    public List<hotels> findAll() {
        return repository.findAll();
    }
}
