package lagecy.live.desh.com.mrdfoodmobilev2;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import javax.json.JsonObject;

/**
 * Created by MANDELACOMP7 on 2018/02/01.
 */

public class JsonArrayRequest extends JsonRequest<JsonObject> {
    public JsonArrayRequest(int method, String url, String requestBody, Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
        return Response.success( null, HttpHeaderParser.parseCacheHeaders(response) );

    }




//    @Override
//    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
//        try {
//            String jsonString = new String(response.data,
//                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//            return Response.success(new JSONArray(jsonString),
//                    HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JSONException je) {
//            return Response.error(new ParseError(je));
//        }
//    }
}
