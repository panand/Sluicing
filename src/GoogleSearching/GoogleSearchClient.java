//package com.aranin.spring.googleapi.search;
 
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
 
import java.util.List;
 
/**
 * Created by IntelliJ IDEA.
 * User: Niraj Singh
 * Date: 6/3/14
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleSearchClient {
 
    final private String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?";
 
    //api key
    final private String API_KEY = "your api key from step 3";
    //custom search engine ID
    final private String SEARCH_ENGINE_ID = "your search engine id from step 2";
 
    final private String FINAL_URL= GOOGLE_SEARCH_URL + "key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID;
 
    public static void main(String[] args){
 
        GoogleSearchClient gsc = new GoogleSearchClient();
        String searchKeyWord = "weblog4j";
        List<Result> resultList =    gsc.getSearchResult(searchKeyWord);
        if(resultList != null && resultList.size() > 0){
               for(Result result: resultList){
                   System.out.println(result.getHtmlTitle());
                   System.out.println(result.getFormattedUrl());
                   //System.out.println(result.getHtmlSnippet());
                   System.out.println("----------------------------------------");
               }
        }
    }
 
    public List<Result> getSearchResult(String keyword){
         // Set up the HTTP transport and JSON factory
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        //HttpRequestInitializer initializer = (HttpRequestInitializer)new CommonGoogleClientRequestInitializer(API_KEY);
        Customsearch customsearch = new Customsearch(httpTransport, jsonFactory,null);
 
        List<Result> resultList = null;
        try {
                Customsearch.Cse.List list = customsearch.cse().list(keyword);
                list.setKey(API_KEY);
                list.setCx(SEARCH_ENGINE_ID);
                //num results per page
                //list.setNum(2L);
 
                //for pagination
                list.setStart(10L);
                Search results = list.execute();
                resultList = results.getItems();
 
        }catch (Exception e) {
                e.printStackTrace();
        }
 
        return resultList;
 
    }
}