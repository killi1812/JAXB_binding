package dokumentapplication;

import generated.UserInfo;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public class DokumentApplication {

    public static void main(String[] args) {
        String xmlFilePath, xsdFilePath;

        if (args.length == 0) {
            System.err.println("GREŠKA: Nije zadana putanja do XML datoteke.");
            System.err.println("Uporaba: java -jar DokumentApplication.jar <putanja_do_xml_datoteke>");
            xmlFilePath = "dokumenti.xml";
            xsdFilePath = "dokumenti.xsd";
            System.exit(1);
        } else {
            xmlFilePath = args[1];
            xsdFilePath = args[0];
        }

        // =====================================================================
        //              Validacija XML datoteke prema XSD shemi
        // =====================================================================
        System.out.println("--- Validacija XML-a prema XSD-u ---");
        try {
            JAXBContext jc = JAXBContext.newInstance(UserInfo.class);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File xsdFile = new File(xsdFilePath);
            if (!xsdFile.exists()) {
                System.err.println("GREŠKA: XSD shema nije pronađena na putanji: " + xsdFilePath);
                System.exit(1);
            }
            Schema schema = sf.newSchema(xsdFile);
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
            System.out.println(ex);

        }
        System.out.println("---------------------------------------");
    }
}
