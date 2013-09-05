package com.rd.seboapp.Utils;

import com.rd.seboapp.Beans.SebosItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by RD on 28/08/13.
 */
public class EstanteVirtualParserUtil {

    /**
     * This method will create a List<SebosItem> from a htmlResponse string
     * This method may change if the url http://www.estantevirtual.com.br/ change its HTML
     *
     * @param htmlResponse The Html string
     * @return Return a List<SebosItem>
     */
    public List<SebosItem> getSebosList(String htmlResponse) {

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

        Document doc;

        doc = Jsoup.parse(htmlResponse);

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
            city = city.replace(",", "");
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
            SebosItem seboItem = new SebosItem(link, title, city, state, Integer.parseInt(totalBooks), rating);
            sebosArrayList.add(seboItem);
        }

        return sebosArrayList;
    }
}
