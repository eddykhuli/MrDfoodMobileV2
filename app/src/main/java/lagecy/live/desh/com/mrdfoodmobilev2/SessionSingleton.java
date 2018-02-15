package lagecy.live.desh.com.mrdfoodmobilev2;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

/**
 * Created by MANDELACOMP7 on 2018/01/11.
 */

class SessionSingleton {
    public Long userSession = null;
    public Long restaurantId = null;
    public String categoryName = null;
    public List<Product> cartItems = new ArrayList<>();
    public ArrayList<Double> itemTot = new ArrayList<>();
    public String restaurantName = "";
    public JSONObject personObject = null;
    public JsonObject orderObject = null;

    private static final SessionSingleton ourInstance = new SessionSingleton();

    public static SessionSingleton getInstance() {
        return ourInstance;
    }
    private SessionSingleton() {}
    public  void logout()
    {
          userSession = null;
          restaurantId = null;
          categoryName = null;
         List<Product> cartItems = null;
         ArrayList<Double> itemTot =null;
         String restaurantName = "";
         JSONObject personObject = null;
         JsonObject orderObject = null;
    }
}
