package app.services.search;

import app.models.hotels;
import app.models.hotelsDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class searchController {

    JSONArray dbContentInJson = new JSONArray();
    List<hotels> result = new ArrayList<hotels>();

    @Autowired
    @Qualifier("hotelsDAO")
    hotelsDAO dao;

    @RequestMapping("/v1/hotels")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public JSONArray hotels() {
        clearContent();
        result.addAll(dao.findAll());

        dbContentInJson.addAll(result);
        return dbContentInJson;
    }

    private void clearContent() {
        result.clear();
        dbContentInJson.clear();
    }
}
