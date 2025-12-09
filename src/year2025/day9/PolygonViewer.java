package year2025.day9;

import javax.swing.*;
import java.awt.*;

public class PolygonViewer extends JPanel {

    int[][] points; // points[0][] = x, points[1][] = y

    public PolygonViewer(int[][] points) {
        this.points = points;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2f));

        //find max coordinats for scaling
        int maxX = 0, maxY = 0;
        for (int i = 0; i < points[0].length; i++) {
            maxX = Math.max(maxX, points[0][i]);
            maxY = Math.max(maxY, points[1][i]);
        }

        double scaleX = getWidth()  / (maxX * 1.05);
        double scaleY = getHeight() / (maxY * 1.05);
        double scale = Math.min(scaleX, scaleY);

        //build polygon
        Polygon poly = new Polygon();
        for (int i = 0; i < points[0].length; i++) {
            int sx = (int)(points[0][i] * scale);
            int sy = (int)(points[1][i] * scale);
            poly.addPoint(sx, sy);
        }

        g2.setColor(new Color(0, 120, 255, 80)); // blue with transparency
        g2.fillPolygon(poly);
        g2.setColor(Color.BLUE);
        g2.drawPolygon(poly);

        // Draw points
        g2.setColor(Color.BLACK);
        for (int i = 0; i < points[0].length; i++) {
            int sx = (int)(points[0][i] * scale);
            int sy = (int)(points[1][i] * scale);
            g2.fillOval(sx - 3, sy - 3, 6, 6);
        }
    }

    public static void showWindow(int[][] points) {
        JFrame frame = new JFrame("Polygon Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.add(new PolygonViewer(points));
        frame.setVisible(true);
    }
}