package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Home", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(intent);
            }
        });


        // lvCustomerOrders.
        viewOrders();
    }

    public void viewOrders()
    {
        long orderId = SessionSingleton.getInstance().userSession;
        String url = "http://192.168.2.155:8080/api/orderInfo/login/customer/viewOrders/"+orderId;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                   final List<OrderInfo> orders =  getOrders(response);
                    Log.d("orders============",""+orders.size());
                    Log.d("cart Products=====",response);
                    ListView lvCustomerOrders = findViewById(R.id.lvCustomerOrders);
                    final String daat = response;
                    OrdersArrayAdapter adapter = new OrdersArrayAdapter(getApplicationContext(),orders);
                    lvCustomerOrders.setAdapter(adapter);
                    lvCustomerOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            List<Product> cartProds = orders.get(i).getCartItems();

//                            if(cartProds == null)
//                            {
//                                Log.d("Cart Error     :","Cart is Empty");
//                            }
//                            else{
//                                    ListView prodsListView = findViewById(R.id.orders);
//                            }



//                            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//                            JsonObjectBuilder objectBuilder = null;
//                            objectBuilder = Json.createObjectBuilder()
//                                    .add("id", orders.get(i).getCartItems().get(i))
//                                    .add("productName", productName)
//                                    .add("category", category)
//                                    .add("price", price)
//                                    .add("description", description)
//                                    .add("quantity",quantity);
//                            arrayBuilder.add(objectBuilder);


                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public List<OrderInfo> getOrders(String data)throws JSONException {
        List<OrderInfo>  orders = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(data);
            for(int x= 0; x < jsonArray.length();x++)
            {
                JSONObject obj = jsonArray.getJSONObject(x);
                OrderInfo orderInfo = new OrderInfo();

                orderInfo.setOrderId(obj.getString("orderId"));
                orderInfo.setUserId(obj.getInt("userId"));
                orderInfo.setSubTotal(obj.getDouble("subTotal"));
                orderInfo.setTotal(obj.getDouble("total"));
               orderInfo.setOrderDate(obj.getString("orderDate"));
               orderInfo.setCollectionType(obj.getString("collectionType"));
               orderInfo.setRestaurantName(obj.optString(" restaurantName"));
               orderInfo.setStatus(obj.getString("status"));
               orderInfo.setContactno(obj.getString("contactNO"));
               orderInfo.setDeliveryAddress(obj.getString("deliveryAddress"));
               orderInfo.setEmailAddress(obj.getString("emailAddress"));
                orders.add(orderInfo);
                // Log.d("Restaurants ------------>>:",restaurants.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orders;
    }

}
