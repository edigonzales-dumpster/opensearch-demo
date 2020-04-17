package ch.so.agi.opensearch;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.a9.opensearch._11.Image;
import com.a9.opensearch._11.ObjectFactory;
import com.a9.opensearch._11.OpenSearchDescription;

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
        
        return new ResponseEntity<OpenSearchDescription>(openSearchDescription, HttpStatus.OK);
    }

}
