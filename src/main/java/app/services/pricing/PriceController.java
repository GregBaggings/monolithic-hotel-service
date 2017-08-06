package app.services.pricing;

import app.models.Hotel;
import app.models.HotelsDAO;
import app.models.Price;
import app.models.PriceDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
@RestController
public class PriceController {

    private JSONObject jsonObject = new JSONObject();
    private JSONArray hotelsJSON = new JSONArray();
    private JSONArray pricesJSON = new JSONArray();

    @Autowired
    PriceDAO priceDAO;
    @Autowired
    HotelsDAO hotelDAO;

    @RequestMapping("/v1/hotels/price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> roomPrices() {
        List<Price> prices = priceDAO.findAll();

        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @RequestMapping("/v1/hotels/prices")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> pricesWithHotel() {

        clearContent();

        List<Hotel> hotels = hotelDAO.findAll();
        List<Price> prices = priceDAO.findAll();

        hotelsJSON.add(hotels);
        pricesJSON.add(prices);

        jsonObject.put("result", "OK");
        jsonObject.put("hotels", hotelsJSON);
        jsonObject.put("roomPrices", pricesJSON);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @RequestMapping("/v2/hotels/prices")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> pricesForAHotelByID(@RequestParam("id") int id) {

        clearContent();

        Hotel hotels = hotelDAO.findById(id);
        List<Price> prices = priceDAO.findAllByhotelId(id);

        hotelsJSON.add(hotels);
        pricesJSON.add(prices);

        jsonObject.put("result", "OK");
        jsonObject.put("hotels", hotelsJSON);
        jsonObject.put("roomPrices", pricesJSON);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @RequestMapping("/v3/hotels/prices")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> pricesForAHotelByIDv2(@RequestParam("id") int id) {

        clearContent();

        Hotel hotels = hotelDAO.findById(id);
        List<Price> prices = priceDAO.findAllByhotelId(id);

        hotelsJSON.add(prices);

        HashMap<Object, Object> hotelDetails = new HashMap<>();
        hotelDetails.put("hotelName", hotels.getHotelName());
        hotelDetails.put("country", hotels.getCountry());
        hotelDetails.put("city", hotels.getCity());
        hotelDetails.put("address", hotels.getAddress());

        jsonObject.put("result", "OK");
        jsonObject.put("hotelDetails", hotelDetails);
        jsonObject.put("pricing", prices);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    private void clearContent() {
        hotelsJSON.clear();
        pricesJSON.clear();
        jsonObject.clear();
    }
}
