package it.zazos.cors.rest;

import it.zazos.cors.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zanutto on 2/24/15.
 */
@RestController
@RequestMapping("/rest")
public class CommonResource {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value="/printers", method = RequestMethod.GET)
    public List<String> getPrinters() {
        return commonService.getPrinters();
    }
}
