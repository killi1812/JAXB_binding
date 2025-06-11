package dokumentapplication;

import generated.UserInfo;
import generated.ObjectFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class DokumentApplication {

    public static void main(String[] args) {

        //TODO: docPath
        var docPath = "dokumenti.xml";

        ObjectFactory of = new ObjectFactory();

        var noviUserInfo = of.createUserInfo();
        
        noviUserInfo.setID(1);
        try {
            JAXBContext jc = JAXBContext.newInstance(UserInfo.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(noviUserInfo, new File(docPath));
            m.marshal(noviUserInfo, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(DokumentApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done");
    }

}
