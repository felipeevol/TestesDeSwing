import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExampleRectangle2 {

    public static void main(String[] args) {
        new ExampleRectangle2();
    }

    public ExampleRectangle2() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }

                    JFrame frame = new JFrame("Testing");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(new TestPane());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public class TestPane extends JPanel {

        private BufferedImage img;
        private RoundRectangle2D drawRectangle;
        private boolean highlight = false;

        public TestPane() throws IOException {
            addMouseMotionListener(new MouseAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    highlight = drawRectangle.contains(e.getPoint());
                    repaint();
                }

            });
            drawRectangle = new RoundRectangle2D.Double(30, 50, 100, 30, 20, 20);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            //g2d.drawRoundRect(30, 50, 100, 30, 20, 20);
            g2d.setColor(Color.GRAY);
            g2d.fill(drawRectangle);
            Stroke stroke1 = new BasicStroke(1.5f);
            g2d.setColor(Color.BLUE);
            g2d.setStroke(stroke1);
            g2d.draw(drawRectangle);

            if (highlight) {
                g2d.setColor(Color.WHITE);
                g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
                //g2d.fill(drawRectangle);
                //g2d.setColor(Color.BLUE);
                //g2d.drawRoundRect(30, 50, 100, 30, 20, 20);
                g2d.draw(drawRectangle);
            }
            g2d.dispose();
        }

    }

}