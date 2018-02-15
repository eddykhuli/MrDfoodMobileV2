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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText etFName,etSurname,etCellNo,etEmail,etPassword,etAddress;
    Button btRegister;
    private String TAG = "Message";
    private String firstName,surname,cellNo,email,address,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFName = findViewById(R.id.etFName);
        etSurname = findViewById(R.id.etSurname);
        etCellNo = findViewById(R.id.etCellNo);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = etFName.getText().toString();
                surname = etSurname.getText().toString();
                cellNo = etCellNo.getText().toString();
                email = etEmail.getText().toString();
                address = etAddress.getText().toString();
                password = etPassword.getText().toString();

                registrationVolley(firstName,surname,cellNo,email,address,password);

            }
        });


    }

    public void registrationVolley(final String name, final String surname, String cellNo, String email, String address, String password)
    {
        String url = "http://192.168.2.155:8080/api/person/registerPerson";
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> data = new HashMap<>();
        data.put("firstname",name);
        data.put("surname",surname);
        data.put("cellNo",cellNo);
        data.put("email",email);
        data.put("address",address);
        data.put("password",password);

        // JSONObject parameters = new JSONObject(data);
        //VolleyLog.d(TAG,"data :"+parameters);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST ,url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d(TAG,"Response :"+response.toString() );
                            //Toast.makeText(getApplicationContext(),"Successfully registered...!"+ name,Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(),"Account created successfully for "+
                                    response.getString("firstname") +" ...!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.d("--EDDY -- Json Error: : : ", e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("------------>", error.toString() );
                //VolleyLog.d(TAG,"Response :"+error.getMessage());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
        //return ;

        //  queue.add(request);


    }





}
