package com.rd.seboapp.Utils;

import android.content.Context;
import android.content.ContextWrapper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rd.seboapp.Beans.SebosItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by RD on 28/08/13.
 */
public class EstanteVirtualRequester {

    /**
     * This method calls an URL and save its return in internal storage
     *
     * @param context
     * @param url The url that data will be get from
     * @param fileName The name of the file that will be saved the response
     */
    public void urlRequest(final Context context, String url, final String fileName) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try {
                    ContextWrapper contextWrapper = new ContextWrapper(context);
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                            File(contextWrapper.getFilesDir() + File.separator + fileName)));
                    bufferedWriter.write(response);
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * This method will create a List<SebosItem> from internal storage saved from {@link #urlRequest(android.content.Context, String, String)}
     * This method may change if the url http://www.estantevirtual.com.br/ change its HTML
     *
     * @param context
     * @param urlDefault A default url if no internal storage html is found
     * @param fileName The name of the saved HTML in internal storage
     * @return Return a List<SebosItem>;
     */
    public List<SebosItem> getSebosListResponseParsed(Context context, String urlDefault, String fileName) {

    /* HTML EXAMPLE ====================================================================================================
        <li class="sebo-item clearfix">
            <a href="http://www.estantevirtual.com.br/sebooalienista" class="sebo-link">
                <h3 class="sebo-titulo">Sebo o Alienista</h3>
                <p class="left sebo-paragrafo sebo-local">Manaus, AM</p>
                <p class="left sebo-paragrafo">Livros no acervo: <span class="sebo-acervo" nlivros="167">167</span></p>
                <div class="right sebo-paragrafo sebo-paragrafo-smaller" >
                    <div class="starRate" >100%</div>
                </div>
                <p class="ordenarqualif" nqualif="100"/>
            </a>
        </li>
    ==================================================================================================================*/

        List<SebosItem> sebosArrayList = new ArrayList<SebosItem>();

        ContextWrapper contextWrapper = new ContextWrapper(context);
        File file = new File(contextWrapper.getFilesDir() + File.separator + fileName);
        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", urlDefault);

            // Get the list of all sebos
            Elements sebosList = doc.select("div#sebos-lista");

            // Get each sebo from the list
            Elements sebosItems = sebosList.select("li.sebo-item");
            Iterator<Element> sebosItemsIterator = sebosItems.iterator();
            while (sebosItemsIterator.hasNext()) {
                Element seboSingleElement = sebosItemsIterator.next();

                // Get the sebos url
                Elements seboUrl = seboSingleElement.select("a.sebo-link");
                String link = seboUrl.attr("abs:href").toString();

                // Get the sabos name
                Elements seboTitle = seboSingleElement.select("h3.sebo-titulo");
                String title = seboTitle.text();

                // Get the sabos city and state (last 3 ones doesnt have state)
                Elements seboLocale = seboSingleElement.select("p.left.sebo-paragrafo.sebo-local");
                String locale = seboLocale.text();

                // Separate locale as city and state
                List<String> localeSeparator = Arrays.asList(locale.split(", "));
                String city = localeSeparator.get(0);
                String state = null;
                if (localeSeparator.size() > 1) {
                    state = localeSeparator.get(1);
                } else {
                    state = "-";
                }

                // Get the sebos total number of books
                Elements seboTotalBooks = seboSingleElement.select("span.sebo-acervo");
                String totalBooks = seboTotalBooks.attr("nlivros").toString();

                // Get the sebos rank
                Elements seboRating = seboSingleElement.select("p.ordenarqualif");
                String rating = seboRating.attr("nqualif").toString();

                // Add sebo to return list
                SebosItem seboItem = new SebosItem(link, title, city, state, Integer.parseInt(totalBooks), Long.parseLong(rating));
                sebosArrayList.add(seboItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sebosArrayList;
    }
}
