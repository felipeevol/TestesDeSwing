import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TestDesignScreen {

    private static JFrame jFrame = new JFrame();
    private static JPanel profilesPane = new JPanel();

    public static void main(String args[]) {

        jFrame.setSize(400, 400);
        jFrame.setLayout(new GridLayout(2, 1));

        JPanel radioGroupPane = new JPanel(new FlowLayout());
        JPanel informationPane = new JPanel(new GridLayout(1, 2));

        profilesPane.setLayout(new BoxLayout(profilesPane, BoxLayout.X_AXIS));
        informationPane.add(profilesPane);

        JRadioButton radioButton = new JRadioButton("os");
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //fillProfilesPane(basePath, actionEvent.getActionCommand());
            }
        });
        radioGroupPane.add(radioButton);

        jFrame.add(radioGroupPane);
        jFrame.add(informationPane);

        jFrame.setVisible(true);

    }

    public void fillProfilesPane(String basePath, String template) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            String pathToStructureFiles = "/tools/cmis/chemistry-opencmis-server-inmemory/src/main/resources/leos/templates/os";
            File structure01 = new File(basePath + pathToStructureFiles +  "/structure_01.xml");
            Document document01 = builder.parse(structure01);

            XPath xPathParser = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPathParser.evaluate("//profiles/profile/profileName", document01, XPathConstants.NODESET);

            for (int itemNumber = 0; itemNumber < nodes.getLength(); itemNumber++) {
                Label label = new Label(nodes.item(itemNumber).getTextContent());
                profilesPane.add(label);
            }
            jFrame.repaint();

        } catch (Exception ex) {

        }
    }

}
