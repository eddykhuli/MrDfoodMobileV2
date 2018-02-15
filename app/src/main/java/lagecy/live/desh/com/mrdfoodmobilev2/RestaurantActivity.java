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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.LinkedHashMap;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {



    private List<Category> categories;
    private  long restaurantID;
    private ListView listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //TextView textView2 = (TextView)findViewById(R.id.textView2);
        listView2 = (ListView)findViewById(R.id.listView2);


      //  Intent intent = getIntent();
          restaurantID = SessionSingleton.getInstance().restaurantId;

        getCategories();
        //textView2.setText("id ="+restaurantID );


    }


    public void getCategories()
    {
//        //http://localhost:8080/api/category/categories/get/getCategoryByRestId/2
        String url =  "http://192.168.2.155:8080/api/category/categories/get/getCategoryByRestId/"+restaurantID ;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("Categories--------->",response.toString());

                try {
                    final List<Category> temp = fetchCategories(response);
                    List<String> catNames = new ArrayList<>();
                   for(Category category : temp)
                   {
                       String name = category.getCategoryName();
                       catNames.add(name);
                   }
                   // Log.d("Categories--------->",response.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,catNames);
                    listView2.setAdapter(adapter);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            SessionSingleton.getInstance().categoryName = temp.get(i).getCategoryName();
                            //Toast.makeText(RestaurantActivity.this, "Name   :"+temp.get(i).getCategoryName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                            intent.putExtra("categoryName",SessionSingleton.getInstance().categoryName);
                            startActivity(intent);

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

    public List<Category> fetchCategories(String data)throws JSONException {

        categories = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int x = 0; x < jsonArray.length(); x++) {
            JSONObject obj = jsonArray.getJSONObject(x);
            Category category = new Category();

            category.setId(obj.getLong("id"));
            category.setRestId(obj.getLong("restId"));
            category.setCategoryName(obj.getString("categoryName"));
            category.setProduct(null);

            categories.add(category);


        }

        return categories;

    }
}
