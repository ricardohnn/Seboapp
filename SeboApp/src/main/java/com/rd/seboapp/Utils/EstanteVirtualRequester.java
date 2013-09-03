package com.rd.seboapp.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by RD on 28/08/13.
 */
public class EstanteVirtualRequester {

    private static final String fileName = "example";

    /**
     * This method calls an URL and save its return in internal storage
     *
     * @param context
     */
    public void urlRequest(final Context context, String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try {
                    ContextWrapper cw = new ContextWrapper(context);
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                            File(cw.getFilesDir()+File.separator+fileName)));
                    bufferedWriter.write(response);
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * This method will create a json from internal storage saved from {@link #urlRequest(android.content.Context, String)}
     *
     * @param context
     * @param urlDefault A default url if no internal storage html is found
     */
    public void getSebosListResponseParsed(Context context, String urlDefault) {

        ContextWrapper cw = new ContextWrapper(context);
        File file = new File(cw.getFilesDir()+File.separator+fileName);
        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", urlDefault);
            Element sebosList = doc.getElementById("sebos-lista");
//            Element items = doc.getElementById("sebo-item clearfix");

            Log.d("HELP", sebosList.toString());
//            Iterator<Element> elementIterator = items.select("li class").iterator();
//            while (elementIterator.hasNext()) {
//                Log.d("HELP", elementIterator.next().toString());
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
