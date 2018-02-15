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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private List<Product> products;
    private ListView listView;
    private MenuCustomAdapter listAdapter;
    Button btnPlaceOrder,btnContinueShopping;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getProducts();

        listView = (ListView) findViewById(R.id.listView);
        //TextView tv =(TextView)findViewById((R.id.tvItemTot));
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        btnContinueShopping = (Button)findViewById(R.id.btnContinueShopping);
        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RestaurantActivity.class);

                startActivity(intent);
            }
        });
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);

            }
        });


    }

    public void getProducts()
    {

        String url =  "http://192.168.2.155:8080/api/product/restaurant/getAllProductsByCategoryName/"+SessionSingleton.getInstance().categoryName ;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("Categories--------->",response.toString());

                try {
                    final List<Product> temp = fetchProducts(response);
                    List<String> prodNames = new ArrayList<>();

                    listAdapter = new MenuCustomAdapter(getApplicationContext(),temp);
                    listView.setAdapter(listAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Product product = temp.get(i);

                                if(SessionSingleton.getInstance().cartItems != null){
                                for(int x = 0; x < SessionSingleton.getInstance().cartItems.size(); x++ ){
                                    if(product.getId() == SessionSingleton.getInstance().cartItems.get(x).getId())
                                    {
                                        SessionSingleton.getInstance().cartItems.remove(x);
                                    }
                                    if(product.getQuantity() == 0)
                                    {
                                        SessionSingleton.getInstance().cartItems.remove(x);
                                        Toast.makeText(getApplicationContext(),"Item's quantity cannot be 0",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            SessionSingleton.getInstance().cartItems.add(product);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error-------------->",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError------------>",error.getMessage());
            }
        });
        //


        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    public List<Product> fetchProducts(String data)throws JSONException {

        products = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int x = 0; x < jsonArray.length(); x++) {
            JSONObject obj = jsonArray.getJSONObject(x);
            Product product = new Product();

           product.setId(obj.getLong("id"));
           product.setProductName(obj.getString("productName"));
           product.setCategory(obj.getString("category"));
           product.setPrice(obj.getDouble("price"));
           product.setDescription(obj.getString("description"));
           product.setQuantity(obj.getInt("quantity"));
            products.add(product);


        }

        return products;

    }

}
