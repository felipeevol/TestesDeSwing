import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadClassCheckUser {

    private JFrame jFrame = new JFrame();
    private JPanel methodsThatExistInFile2 = new JPanel();
    private JPanel methodsInFile1 = new JPanel();
    private ArrayList<JLabel> profileLabels = new ArrayList<>();
    private HashMap<String, ArrayList<String>> templatesPerProfile = new HashMap<>();

    public static void main(String args[]) {
        new ReadClassCheckUser("D:/felipe/LEOS/LEOS-Pilot_3.1.2");
    }

    public ReadClassCheckUser(String basePath) {

        String pathToFile1 = basePath + "/modules/js/src/main/js/editor/plugins/leosAnnexIndentList/leosAnnexIndentListPlugin.js";
        String pathToFile2 = basePath + "/modules/js/src/main/js/editor/plugins/leosAnnexList/leosAnnexListPlugin.js";

        File file1 = new File(pathToFile1);
        File file2 = new File(pathToFile2);

        jFrame.setSize(800, 400);

        JPanel informationPane = new JPanel(new GridLayout(1, 2));

        methodsThatExistInFile2.setLayout(new BoxLayout(methodsThatExistInFile2, BoxLayout.Y_AXIS));
        methodsThatExistInFile2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JScrollPane jspForFile2 = new JScrollPane(methodsThatExistInFile2);
        methodsInFile1.setLayout(new BoxLayout(methodsInFile1, BoxLayout.Y_AXIS));
        methodsInFile1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JScrollPane jspForFile1 = new JScrollPane(methodsInFile1);
        informationPane.add(jspForFile1);
        informationPane.add(jspForFile2);

        try {

            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            String line1 = "";

            while ((line1 = br1.readLine()) != null) {

                if (line1.trim().startsWith("function") && !line1.trim().startsWith("function(")) {

                    JLabel label1 = new JLabel(line1);
                    methodsInFile1.add(label1);
                    String methodName = line1.trim().substring(line1.trim().indexOf("function ")+9, line1.trim().indexOf("("));

                    BufferedReader br2 = new BufferedReader(new FileReader(file2));
                    String line2 = "";
                    while ((line2 = br2.readLine()) != null) {
                        if (line2.contains(methodName)) {
                            JLabel label2 = new JLabel(methodName);
                            methodsThatExistInFile2.add(label2);
                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        jFrame.add(informationPane, BorderLayout.CENTER);

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

    }

}
