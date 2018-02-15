package lagecy.live.desh.com.mrdfoodmobilev2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import dmax.dialog.SpotsDialog;
import lagecy.live.desh.com.mrdfoodmobilev2.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CartActivity extends AppCompatActivity {

    private Button btOrder;
    public ArrayList<Double> grandTot = new ArrayList<>();
    public double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grandTot = SessionSingleton.getInstance().itemTot;

        //Snackbar.make()
        for(int i =0; i < grandTot.size();i ++)
        {
            total = total + grandTot.get(i);

        }
      //  placeOrder();
        Button order = (Button)findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),OrderReviewActivity.class);
                startActivity(intent);

            }
        });
        Button backTo = findViewById(R.id.backTo);
        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent);
            }
        });

        Log.d("Items Total---------->  ",":R"+total);

        final ListView listView = (ListView)findViewById(R.id.listView2);
       int size = SessionSingleton.getInstance().cartItems.size();
       List<String> prodNames = new ArrayList<>();

       for(int x = 0;x < size;x++)
       {
           DecimalFormat df2 = new DecimalFormat(".##");
           prodNames.add(SessionSingleton.getInstance().cartItems.get(x).getProductName()+"\n" +"Quantity :"
                   +SessionSingleton.getInstance().cartItems.get(x).getQuantity()+"\n"+"Item Total R:"
                   +df2.format(SessionSingleton.getInstance().cartItems.get(x).getQuantity()*SessionSingleton.getInstance().cartItems.get(x).getPrice())+"              Remove");
       }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,prodNames);
        adapter.setNotifyOnChange(true);
       listView.setAdapter(adapter);

     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // AlertDialog dialog = new SpotsDialog(getApplicationContext(),R.style.Custom);
             AlertDialog dialog = new SpotsDialog(getApplicationContext());
             dialog.show();
             Product product = SessionSingleton.getInstance().cartItems.get(i);
             SessionSingleton.getInstance().cartItems.remove(product);

             dialog.dismiss();

         }
     });

    }

//             for(Product product : SessionSingleton.getInstance().cartItems)
//             {
//                 SessionSingleton.getInstance().cartItems.remove(product);
//             }


}
