import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ReadClassMenuV1 {

    private JFrame jFrame = new JFrame();
    private JButton buttonProfilesUsedInAStructure = new JButton("Profiles Used in a Structure");
    private JButton buttonStructuresThatUseAProfile = new JButton("Structures That Use a Profile");
    private JButton button3 = new JButton();

    private String basePath = "";

    public static void main(String args[]) {
        new ReadClassMenuV1();
    }

    public ReadClassMenuV1() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(jFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            basePath = file.getAbsolutePath();
        } else {
            System.exit(0);
        }

        jFrame.setSize(400, 400);
        jFrame.setLayout(new FlowLayout());
        jFrame.add(buttonProfilesUsedInAStructure);
        jFrame.add(buttonStructuresThatUseAProfile);
        jFrame.add(button3);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonProfilesUsedInAStructure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReadClassV2(basePath);
            }
        });

        buttonStructuresThatUseAProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReadClassV3(basePath);
            }
        });

        jFrame.setVisible(true);

    }

}
