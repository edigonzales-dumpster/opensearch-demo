package ch.so.agi.opensearch;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a9.opensearch._11.Image;
import com.a9.opensearch._11.OpenSearchDescription;
import com.a9.opensearch._11.Url;

@Controller
public class MainController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
        suggestJsonUrl.setTemplate("https://de.wikipedia.org/w/api.php?action=opensearch&amp;format=json&amp;search={searchTerms}");
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
    
    @GetMapping(value = "/search")
    public String searchByQuery(Model model, @RequestParam(value="q", required=false) String searchTerms) {        
        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
        searchResults.add(new SearchResult("Hallo Resultat."));
        searchResults.add(new SearchResult(searchTerms));

        model.addAttribute("searchResults", searchResults);
        return "search.result.html";        
    }
    
    
    private String getHost() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

}
