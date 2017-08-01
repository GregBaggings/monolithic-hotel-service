package app.services.hotelDetails;

import app.handlers.ErrorHandler;
import app.models.Hotel;
import app.models.HotelsDAO;
import app.models.HotelsRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Gergely_Agnecz on 7/28/2017.
 */
@RestController
public class DetailsController {

    @Autowired
    HotelsDAO dao;

    @RequestMapping("/v1/hotel")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotelDetails(@RequestParam("name") String name) {
        Hotel hotel = dao.findByhotelName(name);

        if (hotel == null) {
            return new ResponseEntity(new ErrorHandler(name + " was not found in the database!"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
    }

    @RequestMapping("/v1/hotels/shortDetails")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> test(@RequestParam("name") String name) {
        HotelsRepository.ShortHotelDetails shortHotel = dao.findByHotelName(name);

        if (shortHotel == null) {
            return new ResponseEntity(new ErrorHandler(name + " was not found in the database!"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<HotelsRepository.ShortHotelDetails>(shortHotel, HttpStatus.OK);
    }
}
