package reusable;

/* Simple class, created because I was reusing the same code to parse the URL from JSOn
 * so decided to just make it a class so I can just reuse it */

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;

public class UrlParser {
    private String href;
    private String url;

    public UrlParser(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParsedUrl() {
        //Using OkHTTP access the web, with the provided href
        //with the href we can grab json data and grab the URL for the
        //the artists homepage, this will help us give this information to the user
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(href)
                .build();
        try {
            Response response = client.newCall(request).execute();

            //Get the body of the url contents
            String jsondata = response.body().string();
            //Parse the json data
            try {
                JSONObject baseData = new JSONObject(jsondata);
                JSONObject jsonUrl = baseData.getJSONObject("external_urls");
                url = jsonUrl.getString("spotify");
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(null, "JSON error occurred: " + e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
