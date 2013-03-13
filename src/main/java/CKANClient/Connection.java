package CKANClient;

import java.net.URL;

import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * Connection holds the connection details for this session
 *
 * @author Ross Jones <ross.jones@okfn.org>
 * @version 1.7
 * @since 2012-05-01
 */
public final class Connection {

    private String m_host;
    private int m_port;
    private String _apikey = "24a733ea-a4b9-4a5f-890b-b8a6096e8efa";

    public Connection() {
        this("http://datahub.io", 80);
    }

    public Connection(String host) {
        this(host, 80);
    }

    public Connection(String host, int port) {
        this.m_host = host;
        this.m_port = port;

        try {
            URL u = new URL(this.m_host + ":" + this.m_port + "/api");
        } catch (MalformedURLException mue) {
            System.out.println(mue);
        }

    }

    public void setApiKey(String key) {
        this._apikey = key;
    }

    /**
     * Makes a POST request
     *
     * Submits a POST HTTP request to the CKAN instance configured within the
     * constructor, returning tne entire contents of the response.
     *
     * @param path The URL path to make the POST request to
     * @param data The data to be posted to the URL
     * @returns The String contents of the response
     * @throws A CKANException if the request fails
     */
    protected String Post(String path, String data)
            throws CKANException {
        URL url = null;

        try {
            url = new URL(this.m_host + ":" + this.m_port + path);
        } catch (MalformedURLException mue) {
            System.err.println(mue);
            return null;
        }

        String body = "";

        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost postRequest = new HttpPost(url.toString());
            postRequest.setHeader("X-CKAN-API-Key", this._apikey);

            StringEntity input = new StringEntity(data);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpclient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line = "";
            while ((line = br.readLine()) != null) {
                body += line;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return body;
    }

    protected String MuliPartPost(String path, String data)
            throws CKANException {
//        URL url = null;
//
//        try {
//            url = new URL(this.m_host + ":" + this.m_port + path);
//        } catch (MalformedURLException mue) {
//            System.err.println(mue);
//            return null;
//        }

        String body = "";

        HttpClient httpclient = new DefaultHttpClient();
        try {
            String fileName = "D:\\Docs Pro Clement\\NESSHI\\Project ICONOGRAPHY\\Guardian pdfs\\Docs Dutch Team\\Telegraaf\\1999\\test.jpg";

            FileBody bin = new FileBody(new File(fileName), "image/jpeg");
            
            StringBody comment = new StringBody("Filename: " + fileName);

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("bin", bin);
            reqEntity.addPart("comment", comment);
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            String currDate = df.format(new Date());
            currDate = currDate.replaceAll("[-:\\+]","");
            System.out.println("date: " + currDate);
            HttpPost postRequest = new HttpPost("http://datahub.io/api/action/storage/auth/form/"+currDate+"/test.jpg");
            postRequest.setEntity(reqEntity);
            postRequest.setHeader("X-CKAN-API-Key", this._apikey);
            HttpResponse response = httpclient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("status code: " + statusCode);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            while ((line = br.readLine()) != null) {
                body += line;
            }
            System.out.println("body: " + body);
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return body;
    }
}
