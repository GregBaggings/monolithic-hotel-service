package app.services.rooms;

import app.handlers.ErrorHandler;
import app.models.Rooms;
import app.models.RoomsDAO;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
@RestController
public class RoomsController {
    private ErrorHandler incorrectInputHandler = new ErrorHandler("Incorrect input. Please enter a valid hotel id!");
    private ErrorHandler missingParameterHandler = new ErrorHandler("Missing param: hotelId");

    @Autowired
    RoomsDAO roomsDAO;

    @RequestMapping("/v1/hotels/hotel/rooms")
    public ResponseEntity<?> roomPrices() {
        List<Rooms> rooms = roomsDAO.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping("/v1/hotels/hotel/{hotelId}/rooms")
    public ResponseEntity<?> roomPricesForAHotel(@PathVariable("hotelId") int hotelId) {
        List<Rooms> rooms = roomsDAO.findByhotelid(hotelId);
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(new ErrorHandler("No rooms are available for hotel with hotel id: " + hotelId + "!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);

    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<?> wrongType(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(incorrectInputHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<?> missingParam(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(missingParameterHandler, HttpStatus.BAD_REQUEST);
    }
}
