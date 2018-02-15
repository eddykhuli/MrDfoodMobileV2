package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CashActivity extends AppCompatActivity {

    private TextView textView3,textView13,textView14,textView16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView3 = findViewById(R.id.textView3);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView16 = findViewById(R.id.textView16);

        textView3.setText("THANK YOU..!!!");
        //Log.d("Order Info-------", SessionSingleton.getInstance().orderObject.toString());

        textView16.setText("Please be sure to have enough cash on you when your order arrives."+"\nAnd make sure the provided the correct address and that your phone is with you for the delivery guy to communicate\nwith you.\nEnjoy!!!");

        Toast.makeText(getApplicationContext(),"Press Done to complete",Toast.LENGTH_LONG).show();
        Button btDone = findViewById(R.id.button);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button working",Toast.LENGTH_LONG).show();

                try {
                    String id = SessionSingleton.getInstance().orderObject.getString("orderId");
                    makePayment(id,SessionSingleton.getInstance().personObject.getLong("id"),"Paid","Card",SessionSingleton.getInstance().orderObject.getInt("total"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(intent);

            }
        });

    }

    public void makePayment(String id,long userId,String status,String paymentT,double amountPayed)
    {
        String url = "http://192.168.2.155:8080/api/Payment/order/payment";

        Map<String,Object> data = new HashMap<>();

        data.put("orderId",id);
        data.put("userId",userId);
        data.put("status",status);
        data.put("paymentType",paymentT);
        data.put("amountPayed",amountPayed);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),"Payment made successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Wrong bank details provided",Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }



}
