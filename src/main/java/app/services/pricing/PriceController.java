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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
@RestController
public class PriceController {

    JSONObject jsonObject = new JSONObject();
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

        JSONArray hotelsJSON = new JSONArray();
        JSONArray pricesJSON = new JSONArray();
        List<Hotel> hotels = hotelDAO.findAll();
        List<Price> prices = priceDAO.findAll();

        hotelsJSON.add(hotels);
        pricesJSON.add(prices);

        jsonObject.put("result", "OK");
        jsonObject.put("hotels", hotelsJSON);
        jsonObject.put("roomPrices", pricesJSON);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
