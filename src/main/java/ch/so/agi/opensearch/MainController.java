package ch.so.agi.opensearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a9.opensearch._11.Image;
import com.a9.opensearch._11.OpenSearchDescription;
import com.a9.opensearch._11.Url;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


@Controller
public class MainController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Value("${app.searchServiceUrl}")
    private String searchServiceUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/ping")
    public ResponseEntity<String>  ping() {
        logger.info("opensearch demo");
        return new ResponseEntity<String>("opensearch demo", HttpStatus.OK);
    }
    
    @GetMapping(value = "/opensearchdescription.xml", produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OpenSearchDescription> getOpenSearchDescription() {
        OpenSearchDescription openSearchDescription = new OpenSearchDescription();
        openSearchDescription.setShortName("SO!GIS Search");
        openSearchDescription.setLongName("SO!GIS Suche - Orte, Karten, Daten, etc.");
        Image image = new Image();
        image.setType("image/x-icon");
        image.setWidth(BigInteger.valueOf(16));
        image.setHeight(BigInteger.valueOf(16));
        image.setValue("data:image/x-icon;base64,AAABAAEAEBAAAAEAIABoBAAAFgAAACgAAAAQAAAAIAAAAAEAIAAAAAAAAAQAAMMOAADDDgAAAAAAAAAAAAD///////////////////////////z8/P9ycnL/cnJy/3Jycv9ycnL//v7+////////////////////////////////////////////8PDw/3Jycv9ycnL//////////////////////3Jycv9ycnL/////////////////////////////////9/f3/3Jycv///////////////////////////////////////////3Jycv/z8/P//////////////////////3Jycv//////////////////////////////////////////////////////bGxs//////////////////7+/v9ycnL//////////////////////////////////////////////////////3Jycv/v7+////////////9ycnL/////////////////////////////////////////////////////////////////cnJy////////////cnJy/////////////////////////////////////////////////////////////////3Jycv///////////3Jycv////////////////////////////////////////////////////////////////9ycnL///////////8mKIv/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/JiiL////////////JiiL/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/yYoi////////////yYoi/8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8mKIv///////////8mKIv/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/JiiL////////////JiiL/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/yYoi////////////yYoi/8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8mKIv///////////8mKIv/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/AADM/wAAzP8AAMz/JiiL////////////JiiL/yYoi/8mKIv/JiiL/yYoi/8mKIv/JiiL/yYoi/8mKIv/JiiL/yYoi/8mKIv/JiiL/yYoi///////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==");
        openSearchDescription.getImages().add(image);
        
        Url searchUrl = new Url();
        searchUrl.setType("text/html");
        searchUrl.setMethod("get");
        searchUrl.setTemplate(getHost() + "/search?q={searchTerms}");
        openSearchDescription.getUrls().add(searchUrl);

        Url suggestJsonUrl = new Url();
        suggestJsonUrl.setType("application/x-suggestions+json");
        suggestJsonUrl.setMethod("get");
        suggestJsonUrl.setTemplate(getHost() + "/search/suggestions?q={searchTerms}");
//        suggestJsonUrl.setTemplate("http://ff.search.yahoo.com/gossip?output=fxjson&command={searchTerms}");
        openSearchDescription.getUrls().add(suggestJsonUrl);
        
//        Url suggestXmlUrl = new Url();
//        suggestXmlUrl.setType("application/x-suggestions+xml");
//        suggestXmlUrl.setMethod("get");
//        suggestXmlUrl.setTemplate("https://de.wikipedia.org/w/api.php?action=opensearch&amp;format=xml&amp;search={searchTerms}");
//        openSearchDescription.getUrls().add(suggestXmlUrl);
        
//        <Url type="text/html" method="get" template="https://de.wikipedia.org/w/index.php?title=Spezial:Suche&amp;search={searchTerms}"/>
//        <Url type="application/x-suggestions+json" method="get" template="https://de.wikipedia.org/w/api.php?action=opensearch&amp;search={searchTerms}&amp;namespace=0"/>
//        <Url type="application/x-suggestions+xml" method="get" template="https://de.wikipedia.org/w/api.php?action=opensearch&amp;format=xml&amp;search={searchTerms}&amp;namespace=0"/>
        
//        logger.info(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
        
        return new ResponseEntity<OpenSearchDescription>(openSearchDescription, HttpStatus.OK);
    }
    
    @RequestMapping(
            value = "/search/suggestions", 
            method = RequestMethod.GET, 
            //headers = "Accept=application/x-suggestions+json",
            produces = {MediaType.APPLICATION_JSON_VALUE})    
    public ResponseEntity<?> jsonSuggestions(@RequestParam(value="q", required=false) String searchTerms) throws IOException {
        logger.info("suggestions - searchTerms: " + searchTerms);
        
        String encodedSearchText = URLEncoder.encode(searchTerms, StandardCharsets.UTF_8.toString());  
        URL url = new URL(searchServiceUrl+encodedSearchText);
        logger.info(url.toString());
        URLConnection request = url.openConnection();
        request.connect();
        
        JsonNode response = objectMapper.readTree(new InputStreamReader((InputStream) request.getContent()));        
        ArrayNode resultsArray = (ArrayNode) response.get("results");

        ArrayNode suggestions = objectMapper.createArrayNode();
        suggestions.add(searchTerms);
        
        ArrayNode completions = objectMapper.createArrayNode();
        Iterator<JsonNode> it = resultsArray.iterator();
        while(it.hasNext()) {
            JsonNode node = it.next();
            JsonNode feature = node.get("feature");            
            completions.add(feature.get("display"));
        }
        suggestions.add(completions);
        
//        ArrayNode descriptions = objectMapper.createArrayNode();
//        descriptions.add("");
//        descriptions.add("");
//        descriptions.add("");
//        root.add(descriptions);

          // Will be ignored by Firefox. 
//        ArrayNode queryUrl = objectMapper.createArrayNode();
//        queryUrl.add("https://www.google.com/search?q=foo");
//        queryUrl.add("https://www.google.com/search?q=bar");
//        queryUrl.add("https://www.google.com/search?q=bubar");
//        root.add(queryUrl);
        
        logger.info(suggestions.toPrettyString());
        return new ResponseEntity<JsonNode>(suggestions, HttpStatus.OK);
    }
    
    
    // Man hat nur noch den Displaytext zur Verfügung nachdem man auf den Vorschlag 
    // geklickt hat. Stand heute kann man aber anhand der Klammer (Adresse, Flurname, 
    // Liegenschaft, ...) auf ein gewisses Mass an Intelligenz zurückgreifen und
    // die eigentliche Suche resp. dann die Antwort steuern.
    
    
    @GetMapping(value = "/search")
    public String searchByQuery(Model model, @RequestParam(value="q", required=false) String searchTerms) throws IOException {        
        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
        
        String encodedSearchText = URLEncoder.encode(searchTerms, StandardCharsets.UTF_8.toString());        
        URL url = new URL(searchServiceUrl+encodedSearchText);
        logger.info(url.toString());

        URLConnection request = url.openConnection();
        request.connect();
        
        JsonNode root = objectMapper.readTree(new InputStreamReader((InputStream) request.getContent()));        
        ArrayNode resultsArray = (ArrayNode) root.get("results");

        Iterator<JsonNode> it = resultsArray.iterator();
        while(it.hasNext()) {
            JsonNode node = it.next();
            JsonNode feature = node.get("feature");
            SearchResult result = new SearchResult();
            result.setDisplay(feature.get("display").asText());
            result.setDataproductId(feature.get("dataproduct_id").asText());
            result.setFeatureId(feature.get("feature_id").asInt());            
            ArrayNode bbox = (ArrayNode) feature.get("bbox");
            result.setMinX(bbox.get(0).asDouble());
            result.setMinY(bbox.get(1).asDouble());
            result.setMaxX(bbox.get(2).asDouble());
            result.setMaxY(bbox.get(3).asDouble());
            searchResults.add(result);
        }
     
        model.addAttribute("searchResults", searchResults);
        return "search.result.html";        
    }
    
    private String getHost() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

}
