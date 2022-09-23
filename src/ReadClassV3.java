import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadClassV3 {

    private JFrame jFrame = new JFrame();
    private JPanel structuresPane = new JPanel();
    private JPanel profilesPane = new JPanel();
    private ArrayList<JLabel> profileLabels = new ArrayList<>();
    private HashMap<String, ArrayList<String>> templatesPerProfile = new HashMap<>();

    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;

    public static void main(String args[]) {
        new ReadClassV3("D:/felipe/LEOS/LEOS-Pilot_3.1.2");
    }

    public ReadClassV3(String basePath) {

        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception ex) {

        }

        //String basePath = "D:/felipe/LEOS/LEOS-Pilot_3.1.2";
        String pathToTemplates = "/tools/cmis/chemistry-opencmis-server-inmemory/src/main/resources/leos/templates";
        String pathToProfiles = "/modules/js/src/main/js/editor/profiles";

        File profilesFolder = new File(basePath + pathToProfiles);

        jFrame.setSize(800, 400);

        JPanel informationPane = new JPanel(new GridLayout(1, 2));

        structuresPane.setLayout(new BoxLayout(structuresPane, BoxLayout.Y_AXIS));
        structuresPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JScrollPane structuresPaneScroll = new JScrollPane(structuresPane);
        profilesPane.setLayout(new BoxLayout(profilesPane, BoxLayout.Y_AXIS));
        profilesPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JScrollPane profilesPaneScroll = new JScrollPane(profilesPane);
        informationPane.add(profilesPaneScroll);
        informationPane.add(structuresPaneScroll);

        for (final File profileFile : profilesFolder.listFiles()) {
            JLabel label = new JLabel(profileFile.getName());
            profileLabels.add(label);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    for (JLabel label: profileLabels) {
                        label.setForeground(Color.BLACK);
                    }
                    JLabel label = (JLabel) e.getComponent();
                    label.setForeground(Color.RED);
                    fillStructuresPane(basePath, pathToTemplates, label.getText());
                }

            });
            profilesPane.add(label);
        }

        jFrame.add(informationPane, BorderLayout.CENTER);

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

    }

    public void fillStructuresPane(String basePath, String pathToTemplates, String labelName) {

        try {

            structuresPane.removeAll();
            File templatesFolder = new File(basePath + pathToTemplates);

            for (final File folderEntry : templatesFolder.listFiles()) {

                String templatesType = folderEntry.getName();
                String pathToTemplatesFiles = basePath + pathToTemplates + "/" + templatesType;
                File templatesFilesFolder = new File(pathToTemplatesFiles);
                File[] structureFiles = templatesFilesFolder.listFiles((dir, name) -> name.toLowerCase().startsWith("structure_"));

                for (File structureFile : structureFiles) {

                    Document structureDocument = builder.parse(structureFile);

                    XPath xPathParser = XPathFactory.newInstance().newXPath();
                    String description = (String) xPathParser.evaluate("/structure/description", structureDocument, XPathConstants.STRING);

                    NodeList nodes = (NodeList) xPathParser.evaluate("//profiles/profile/profileName", structureDocument, XPathConstants.NODESET);
                    for (int itemNumber = 0; itemNumber < nodes.getLength(); itemNumber++) {
                        Node node = nodes.item(itemNumber);
                        String profileName = node.getTextContent();
                        if (profileName.equals(labelName.substring(0, labelName.indexOf(".")))) {
                            String elementType = (String) xPathParser.evaluate("ancestor::tocItem/aknTag", node, XPathConstants.STRING);
                            JLabel label = new JLabel(templatesType + " - " + description + " - " + elementType + " - " + structureFile.getName());
                            structuresPane.add(label);
                        }
                    }

                }

            }

            jFrame.repaint();
            jFrame.revalidate();

        } catch (Exception ex) {

        }

    }

}
