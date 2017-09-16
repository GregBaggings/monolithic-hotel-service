package app.search;


import app.handlers.ErrorHandler;
import app.handlers.ResponseBuilder;
import app.models.Hotel;
import app.models.HotelsDAO;
import app.models.Price;
import app.models.PriceDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    HotelsDAO hotelsDAO;

    @Autowired
    PriceDAO priceDAO;

    @RequestMapping(value = "/v1/hotels/search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> searchByName(@Validated @RequestParam(value = "hotelName", required = true) String hotelName) {
        ResponseEntity<List<Price>> pricesResponse;
        Hotel hotel = hotelsDAO.findByhotelName(hotelName);

        if (hotel == null) {
            return new ResponseEntity<>(new ErrorHandler(incorrectInputHandler.getError()), HttpStatus.NOT_FOUND);
        }

        int hotelId = hotel.getId();
        logger.info("HotelID is : " + hotelId);

        List<Price> prices = priceDAO.findAllByhotelId(hotelId);

        if (prices.isEmpty()) {
            return new ResponseEntity<>(new ErrorHandler("No pricing data is available for: " + hotelName), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(builder.buildResponse(hotel, prices), HttpStatus.OK);
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
