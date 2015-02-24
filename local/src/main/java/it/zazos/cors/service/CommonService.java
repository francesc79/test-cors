package it.zazos.cors.service;

import org.springframework.stereotype.Service;

import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zanutto on 2/24/15.
 */
@Service
public class CommonService {

    public List<String> getPrinters () {
        List<String> printers = new ArrayList<String>();
        for (PrintService ps : PrinterJob.lookupPrintServices()) {
            printers.add(ps.getName());
        }
        return printers;
    }
}
