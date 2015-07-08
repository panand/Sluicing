package edu.ucsc.googlesearch;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
 
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
        final private String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?";

        //api key
        final private String API_KEY = "AIzaSyALD0zNQjbJtUUnfUhpUUhkHlN6mC5s1VA";
        //custom search engine ID
        final private String SEARCH_ENGINE_ID = "010411409518044853323:voutokqevhk";

        final private String FINAL_URL= GOOGLE_SEARCH_URL + "key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID;

        public static void main(String[] args){

            App gsc = new App();
            String searchKeyWord = "clinton";
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

/*
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
*/