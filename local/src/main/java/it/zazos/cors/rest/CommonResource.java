package it.zazos.cors.rest;

import it.zazos.cors.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by zanutto on 2/24/15.
 */
@RestController
@RequestMapping("/rest")
public class CommonResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommonService commonService;

    @RequestMapping(value="/printers", method = RequestMethod.GET)
    public List<String> getPrinters() {
        logger.debug("GET printers");
        return commonService.getPrinters();
    }


    @RequestMapping(value="/printers", method = RequestMethod.POST)
    public List<String> getPrinters(@RequestParam("param") String param) {
        logger.debug("POST printers param:" + param);
        return commonService.getPrinters();
    }

}
