package dokumentapplication;

import generated.Dokumenti;
import generated.Dokumenti.Dokument;
import generated.ObjectFactory;
import java.io.File;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class DokumentApplication {

    public static void main(String[] args) {
        ObjectFactory of = new ObjectFactory();
        
        Dokumenti noviDokumenti = of.createDokumenti();
        
        Dokument noviDokument = of.createDokumentiDokument();
        noviDokument.setId(new BigInteger("1"));
        noviDokument.setNaziv("Dokument 1");
        noviDokument.setFormat("DOCX");
        
        noviDokumenti.getDokument().add(noviDokument);
        
        try {
            JAXBContext jc = JAXBContext.newInstance(Dokumenti.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(noviDokumenti, new File("dokumenti.xml"));
            m.marshal(noviDokumenti, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(DokumentApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
