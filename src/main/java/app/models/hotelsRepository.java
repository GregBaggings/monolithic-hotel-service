package app.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
public interface hotelsRepository extends CrudRepository<hotels, Integer> {
    List<hotels> findAll();
}
