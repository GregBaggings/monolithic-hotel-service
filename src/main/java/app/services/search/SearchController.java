package app.services.search;

import app.handlers.ErrorHandler;
import app.handlers.ResponseBuilder;
import app.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class SearchController {
    private ErrorHandler incorrectInputHandler = new ErrorHandler("Incorrect input. Please enter a valid hotel name!");
    private ErrorHandler missingParameterHandler = new ErrorHandler("Missing param: hotelName");
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    private ResponseBuilder builder = new ResponseBuilder();

    @Autowired
    private HotelsDAO hotelsDAO;
    @Autowired
    private RoomsDAO roomsDAO;
    @Autowired
    private PriceDAO priceDAO;

    @RequestMapping(value = "/v1/search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> searchByDestination(@Validated @RequestParam(value = "destination", required = true) String destination) {
        List<Price> allPrices = null;
        List<Hotel> allHotels = null;
        List<Rooms> allRooms = null;
        List<Integer> hotelIds = new ArrayList<Integer>();
        List<List<Hotel>> hotelList = new ArrayList<>();
        List<List<Price>> priceList = new ArrayList<>();
        List<List<Rooms>> roomList = new ArrayList<>();

        try {
            allHotels =  hotelsDAO.findAllByCity(destination);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new ErrorHandler("No hotel found for the given destination: " + destination), HttpStatus.NOT_FOUND);
        }

        for (Hotel h : allHotels) {
            hotelIds.add(h.getId());
            hotelList.add(allHotels);
        }

        logger.info("HotelIDs are: " + hotelIds);

        for (int i = 0; i < hotelIds.size(); i++) {
            try {
                logger.info("Getting price details for hotelID: " + hotelIds.get(i));
                allPrices = priceDAO.findAllByhotelId(hotelIds.get(i));
                priceList.add(allPrices);
                logger.info("Price list for hotelID " + hotelIds.get(i) + " : " + priceList.toString());
            } catch (HttpClientErrorException e) {
                logger.info("No pricing details for hotel id: " + hotelIds.get(i));
            }
        }

        logger.info("Full price list: " + priceList.toString());

        for (int i = 0; i < hotelIds.size(); i++) {
            try {
                logger.info("Getting room details for hotelID: " + hotelIds.get(i));
                allRooms = roomsDAO.findByhotelid(hotelIds.get(i));
                roomList.add(allRooms);
                logger.info("Rooms list for hotelID " + hotelIds.get(i) + " : " + roomList.toString());
            } catch (HttpClientErrorException e) {
                logger.info("No room details for hotel id: " + hotelIds.get(i));
            }
        }

        logger.info("Full room list: " + roomList.toString());

        if(roomList.isEmpty() || priceList.isEmpty()){
            return new ResponseEntity<>(new ErrorHandler("No hotel found"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(builder.buildResponseFromLists(hotelList, priceList, roomList), HttpStatus.OK);
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
