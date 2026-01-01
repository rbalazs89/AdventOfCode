package year2025.day9;

import javax.swing.*;
import java.awt.*;

class PolygonViewer extends JPanel {

    private final int[][] points; // points[0][] = x, points[1][] = y

    public PolygonViewer(int[][] points) {
        this.points = points;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2f));

        //find max coordinates for scaling
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

        /// ///////////
        // drawrect 1
        /// //////////
        int maxI = 217;

        int realx1 = (points[0][248]);
        int realy1 = (points[1][248]);
        int realx3 = (points[0][maxI]);
        int realy3 = (points[1][maxI]);

        int realArea = (Math.abs(realx1 - realx3) + 1) * (Math.abs(realy1 - realy3) + 1);
        //System.out.println("drawing: " + realArea);

        int x1 = (int)(points[0][248] * scale);
        int y1 = (int)(points[1][248] * scale);
        int x3 = (int)(points[0][maxI] * scale);
        int y3 = (int)(points[1][maxI] * scale);

        // normalize
        int left = Math.min(x1, x3);
        int right = Math.max(x1, x3);
        int top = Math.min(y1, y3);
        int bottom = Math.max(y1, y3);

        int width = right - left;
        int height = bottom - top;

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawRect(left, top, width, height);

        /// ///////////
        // drawrect 2
        /// //////////
        int maxI2 = 278;

        x1 = (int)(points[0][249] * scale);
        y1 = (int)(points[1][249] * scale);
        x3 = (int)(points[0][maxI2] * scale);
        y3 = (int)(points[1][maxI2] * scale);

        // normalize
        left = Math.min(x1, x3);
        right = Math.max(x1, x3);
        top = Math.min(y1, y3);
        bottom = Math.max(y1, y3);

        width = right - left;
        height = bottom - top;

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawRect(left, top, width, height);

        // Draw points
        g2.setColor(Color.BLACK);
        for (int i = 0; i < points[0].length; i++) {
            g2.setColor(Color.BLACK);
            int sx = (int)(points[0][i] * scale);
            int sy = (int)(points[1][i] * scale);
            g2.fillOval(sx - 3, sy - 3, 6, 6);
            // starting point, going clockwise
            if(i < 1){
                g2.setColor(Color.RED);
                g2.fillOval(sx - 3, sy - 3, 10, 10);
            }
            // unique points
            if(i == 248 || i == 249){
                g2.setColor(Color.GREEN);
                g2.fillOval(sx - 3, sy - 3, 6, 6);
            }
            if(i == 25 || i == 225 || i == 465 || i == 275){
                g2.setColor(Color.BLUE);
                g2.fillOval(sx - 3, sy - 3, 8, 8);
            }
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