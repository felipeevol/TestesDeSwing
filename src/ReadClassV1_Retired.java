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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

////public class ReadClass extends com.intellij.openapi.actionSystem.AnAction {
public class ReadClassV1_Retired {

    private static JFrame jFrame = new JFrame();
    private static JPanel structuresPane = new JPanel();
    private static JPanel profilesPane = new JPanel();

    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder builder;

    ////@Override
    ////public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    public static void main(String args[]) {

        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception ex) {

        }

        //Messages.showDialog(e.getProject(), "Worked Fine!", "Read Class Option", new String[]{"OK"}, 0,
        //        Messages.getInformationIcon());

        ////String basePath = anActionEvent.getProject().getBasePath();
        String basePath = "D:/felipe/LEOS/LEOS-Pilot_3.1.2";
        String pathToTemplates = "/tools/cmis/chemistry-opencmis-server-inmemory/src/main/resources/leos/templates";
        File templatesFolder = new File(basePath + pathToTemplates);

        jFrame.setSize(600, 400);
        //jFrame.setLayout(new GridLayout(2, 1));

        JPanel radioGroupPane = new JPanel(new FlowLayout());
        JPanel informationPane = new JPanel(new GridLayout(1, 2));

        structuresPane.setLayout(new BoxLayout(structuresPane, BoxLayout.Y_AXIS));
        structuresPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        profilesPane.setLayout(new BoxLayout(profilesPane, BoxLayout.Y_AXIS));
        profilesPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        informationPane.add(structuresPane);
        informationPane.add(profilesPane);

        ButtonGroup radioGroup = new ButtonGroup();
        for (final File folderEntry : templatesFolder.listFiles()) {
            JRadioButton radioButton = new JRadioButton(folderEntry.getName());
            radioGroup.add(radioButton);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    fillStructuresPane(basePath, actionEvent.getActionCommand());
                }
            });
            radioGroupPane.add(radioButton);
        }

        jFrame.add(radioGroupPane, BorderLayout.NORTH);
        jFrame.add(informationPane, BorderLayout.CENTER);

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

    }

    public static void fillStructuresPane(String basePath, String template) {

        try {

            structuresPane.removeAll();
            profilesPane.removeAll();

            String pathToTemplates = "/tools/cmis/chemistry-opencmis-server-inmemory/src/main/resources/leos/templates/";
            File templatesFolder = new File(basePath + pathToTemplates + template);
            File[] structureFiles = templatesFolder.listFiles((dir, name) -> name.toLowerCase().startsWith("structure_"));

            for (File structureFile : structureFiles) {

                Document structureDocument = builder.parse(structureFile);

                XPath xPathParser = XPathFactory.newInstance().newXPath();
                NodeList nodes = (NodeList) xPathParser.evaluate("/structure/description", structureDocument, XPathConstants.NODESET);

                for (int nodeNumber = 0; nodeNumber < nodes.getLength(); nodeNumber++) {

                    String description = nodes.item(nodeNumber).getTextContent();
                    JLabel label = new JLabel(description);

                    label.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            fillProfilesPane(structureFile);
                        }

                    });
                    structuresPane.add(label);
                }

            }
            jFrame.repaint();
            jFrame.revalidate();

        } catch (Exception ex) {

        }

    }

    public static void fillProfilesPane(File structureFile) {

        try {

            profilesPane.removeAll();

            Document structureDocument = builder.parse(structureFile);

            XPath xPathParser = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPathParser.evaluate("//profiles/profile/profileName", structureDocument, XPathConstants.NODESET);
            for (int itemNumber = 0; itemNumber < nodes.getLength(); itemNumber++) {
                JLabel label = new JLabel(nodes.item(itemNumber).getTextContent());
                profilesPane.add(label);
            }
            jFrame.repaint();
            jFrame.revalidate();

        } catch (Exception ex) {

        }

    }

}
