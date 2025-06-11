package dokumentapplication;

import generated.UserInfo;
import generated.ObjectFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public class DokumentApplication {

    public static void main(String[] args) {

        String xmlFilePath = "dokumenti.xml";
        // Putanja do XSD sheme mora biti relativna u odnosu na korijen projekta
        String xsdFilePath = "xml-resources/jaxb/DokumentXsd/dokumenti.xsd";


        // =====================================================================
        //              Validacija XML datoteke prema XSD shemi
        // =====================================================================
        
        System.out.println("--- Validacija XML-a prema XSD-u ---");
        try {
            JAXBContext jc = JAXBContext.newInstance(UserInfo.class);
            
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(xsdFilePath));
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(event -> {
                System.out.println("VALIDACIJSKA GREŠKA:");
                System.out.println("  - LINIJA:    " + event.getLocator().getLineNumber());
                System.out.println("  - KOLONA:  " + event.getLocator().getColumnNumber());
                System.out.println("  - PORUKA: " + event.getMessage());
                return false; 
            });


            unmarshaller.unmarshal(new File(xmlFilePath));
            System.out.println("VALIDACIJA USPJEŠNA: Datoteka '" + xmlFilePath + "' je u skladu s XSD shemom.");

        } catch (JAXBException | SAXException ex) {
            System.out.println("\nVALIDACIJA NEUSPJEŠNA: XML datoteka sadrži greške.");
        }
        System.out.println("---------------------------------------");
    }
}
