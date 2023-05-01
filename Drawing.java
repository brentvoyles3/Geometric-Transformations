import java.util.Scanner;
import java.lang.Math;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {
    public static ArrayList<Line> dataLines;
    public BufferedImage canvas;
    public static int numOfLines;
    public static String ogFile;
    public static int width = 1000, height = 1000;

    // Took a lot of the graphic set up using Buffered image, Jframe and Jpanel from Utyf
    // Set up the stage/canvas for our pixel line drawing algorithms.
    public Drawing(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.WHITE);
        dataLines = new ArrayList<>();
        numOfLines = 0;
        ogFile = "";
    }

        /*
        * Getter method to return the lines
        */
    public ArrayList<Line> getList() {
        return this.dataLines;
    }

    /*
     * Setter method to return the lines 
     */
    public void setList(ArrayList<Line> data) {
        this.dataLines  = data;
    }

    // Return the number of lines, n, input by the user.
    public int getNumLines() {
        return numOfLines;
    }

    // Setter method for instance var line num
    public void setNumLines(int n) {
        this.numOfLines =  n;
    }

    // Return new dimension object width and height
    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    // Graphics component 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    // Fill Canvas - Set color of the stage to draw the lines on 
    public void fillCanvas(Color c) {
        // Chose to fill the background with grey
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                // Set each pixel to white
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    // Bresenham's line drawing algorithm
   // Bresenham's line drawing algorithm
    public void bresenhamLine (int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
	    int dy = Math.abs(y1 - y0);
	    int x, y;
    //slope <= 1.0;
        if (dx >= dy) {
            // 2 * dy (shifted 1 bit) - dx  
            int e = (dy << 1) - dx;
            // 2 * dy
            int inc1 = dy << 1;
            // 2 * dy - dx
            int inc2 = (dy - dx) << 1;	
        // Set the left point to the starting point
        if (x0 < x1) { 
            x = x0;
            y = y0;
        }   
// Swap right most coordinate to starting point and left coordinate to the end point
        else { 
            x = x1;			
            y = y1;
            x1 = x0;
            y1 = y0;
        }
        // Set color of pixel to black
        if (((x >= 0) && (y >= 0)) && ((x < 1000) && (y < 1000))) 
        canvas.setRGB(x, y,Color.BLACK.getRGB());
        while (x < x1) {
            if (e < 0)
                e += inc1;
            else {
                if (y < y1) {
                    y++;
                    e += inc2;
                } else {
                    y--;
                    e += inc2;
                }
            }
            x++;
            if (((x >= 0) && (y >= 0)) && ((x < 1000) && (y < 1000)))  {
                canvas.setRGB(x, y,Color.BLACK.getRGB());
            }
        } // while
    } // if
    // Else our slope is greater than 1
    else { 
        int e = (dx << 1) - dy;
        int inc1 = dx << 1;
        int inc2 = (dx - dy) << 1;
        // Set the left most coordinate to be the starting point
        if (y0 < y1) { 
            x = x0;
            y = y0;
        }
        // Set the rightmost coordinate to be the starting point and 
        // the leftmost coordinate to be the end point.
        else { 
            x = x1;			
            y = y1;
            y1 = y0;
            x1 = x0;
        }
        // Set the color of the pixel to black.
        if ((x >= 0) && (y >= 0))
        canvas.setRGB(x, y,Color.BLACK.getRGB());
        while (y < y1) {
            if (e < 0)
                e += inc1;
            else {
                if (x > x1){
                    x--;
                    e += inc2;
                } else {
                    x++;
                    e += inc2;
                }
            }
            y++;
            // Set the color of the pixel to black.
            if ((x >= 0) && (y >= 0))
            canvas.setRGB(x, y,Color.BLACK.getRGB());
        } // while
    } // else
    } // Bresenham's line drawing algorithm
    

    /*
     * Displays (i.e., scan-converts) ‘datalines’ containing `num' lines by coloring in pixels of 
     * certain lines.
     */
    public void displayPixels(List<Line> dataLines, int num) {
    //User input for # of lines and algorithm to use...
    int x1, y1, x2, y2;
    // upper bounds for generating random numbers / coordinates
    // Start the timer
    //long start = System.currentTimeMillis();
    for (int i = 0; i < num; i++)
    {
        // x and y coordinates determined by a random number generator
        x1 = dataLines.get(i).getx1();
        y1 = dataLines.get(i).gety1();
        x2 = dataLines.get(i).getx2();
        y2 = dataLines.get(i).gety2();
        // print out the x and y coordinates of each lines
        //System.out.println("Line"  + (i+1) + ":(" + x1 + "," + y1 + ")" + "(" + x2 + "," + y2 + ")");
        bresenhamLine(x1, y1, x2, y2);     
        } // for
        // End the timer
        //long finish = System.currentTimeMillis();
        //long duration = (finish - start);
       //System.out.println("Line drawing algorithm finished in " + duration + " milliseconds.");
    }

 /*
  * inputLines, stores the text file values into a Line object and returns the list
  * of lines. List of lines stored in datalines.
  */
public static ArrayList<Line> inputLines(List<String> datalines, int num) {
    ArrayList<Line> inputLines = new ArrayList<>();
    for (int idx = 0; idx < num; idx++) {
        Line temp = new Line(0,0,0,0);
        String s = datalines.get(idx);
        String split[] = s.split(" ");
        temp.setx1(Integer.parseInt(split[0]));
        temp.sety1(Integer.parseInt(split[1]));
        temp.setx2(Integer.parseInt(split[2]));
        temp.sety2(Integer.parseInt(split[3]));
        inputLines.add(idx,temp);
    }
    return inputLines;
}

/*
 * Helper method for inputLines.
 */
public static int getInput() throws FileNotFoundException, IOException {
    System.out.println("Enter the name of a text file that is located in the same directory as Drawing.java");
    Scanner keyboard = new Scanner(System.in);
    String filename = keyboard.nextLine();
    ogFile = filename;
    File file = new File(filename);
    Scanner inputFile = new Scanner(file);
    int numOfLines = 0;
    List<String> lines = new ArrayList<>();
    while(inputFile.hasNextLine()){
        String line = inputFile.nextLine();
        lines.add(numOfLines, line);
        //System.out.println("line " + numOfLines + " :" + line);
        numOfLines++;
    }
    dataLines = (inputLines(lines,numOfLines));
    inputFile.close();
    return numOfLines;
} //input

/*
 * Reload the original text file.
 */
public static int reloadOriginal() throws IOException, FileNotFoundException {
    File file = new File(ogFile);
    Scanner inputFile = new Scanner(file);
    int numOfLines = 0;
    List<String> lines = new ArrayList<>();
    while(inputFile.hasNextLine()){
        String line = inputFile.nextLine();
        lines.add(numOfLines, line);
        //System.out.println("line " + numOfLines + " :" + line);
        numOfLines++;
    }
    dataLines = (inputLines(lines,numOfLines));
    inputFile.close();
    return numOfLines;
} //input

/*
 * Outputs ‘datalines’ containing `num' lines to an external file (name of file is provided
 * by the user). }
 */
public void outputLines(ArrayList<Line> datalines, int num) throws IOException, FileNotFoundException {
    Line dataToAppend;
    int x1,x2,y1,y2;
    String input;
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter a name for the txt file you wish to export: ");
    input = keyboard.nextLine();
    Writer wr = null;
    wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input), "utf-8")); 
    for(int i = 0; i < num; i++) {
        dataToAppend = datalines.get(i);
        x1 = dataToAppend.getx1();
        y1 = dataToAppend.gety1();
        x2 = dataToAppend.getx2();
        y2 = dataToAppend.gety2();
        if (i == (num-1)) {
            wr.write(x1 + " " + y1 + " " + x2 + " " + y2);
        } else {
            wr.write(x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
        }
}
wr.flush();
wr.close();
}

/*
 * Translation - `Tx' is the horizontal and `Ty' is the vertical displacements
 */
public static ArrayList<Line> basicTranslate(int Tx, int Ty, ArrayList<Line> datalines, int num) {
    double[][] T = { {1,0,0},{0,1,0},{Tx,Ty,1} };
    ArrayList<Line> dLines = new ArrayList<>();

    for (int i = 0; i < num; i++) {
        int x1 = datalines.get(i).getx1();
        int y1 = datalines.get(i).gety1();
        double[][] line1 = { {x1, y1, 1} };
        double[][] result1 = Matrix.multiply(line1, T);
        double xpoint1 = result1[0][0];
        double ypoint1 = result1[0][1];

        int x2 = datalines.get(i).getx2();
        int y2 = datalines.get(i).gety2();
        double[][] line2 = { {x2, y2, 1} };
        double[][] result2 = Matrix.multiply(line2, T);
        double xpoint2 = result2[0][0];
        double ypoint2 = result2[0][1];

        Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
        dLines.add(i, temp);
    }
    return dLines;
}

/*
 * Scale - `Sx' and `Sy' are the horizontal and vertical scaling factors
 * center of scale is at the origin of the Coordinate System
 */
public static ArrayList<Line> basicScale(double Sx,double Sy, ArrayList<Line> dlines, int num ) {
    double[][] T = { {Sx,0,0},{0,Sy,0},{0,0,1} };
    ArrayList<Line> datalines = new ArrayList<>();

    for (int i = 0; i < num; i++) {
        double x1 = (double) dlines.get(i).getx1();
        double y1 = (double) dlines.get(i).gety1();
        double[][] line1 = { {x1, y1, 1} };
        double[][] result1 = Matrix.multiply(line1, T);
        double xpoint1 = round(result1[0][0], 1);
        double ypoint1 = round(result1[0][1],1);

        double x2 = (double) dlines.get(i).getx2();
        double y2 = (double) dlines.get(i).gety2();
        double[][] line2 = { {x2, y2, 1} };
        double[][] result2 = Matrix.multiply(line2, T);
        double xpoint2 = round(result2[0][0], 1);
        double ypoint2 = round(result2[0][1],1);

        Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
        datalines.add(i, temp);
}
    return datalines;
}

/*
 * Helper method to round doubles to a single integer.
 */
private static double round (double value, int precision) {
    int scale = (int) Math.pow(10, precision);
    return (double) Math.round(value * scale) / scale;
}

/*
 * Rotation - angle of rotation is `angle' degrees (clockwise); Center of 
 * rotation is at the origin.
 */
public static ArrayList<Line> basicRotate(double degrees, ArrayList<Line> lines, int num ) {
        ArrayList<Line> datalines = new ArrayList<>();
		degrees = Math.toRadians(degrees);
		double cosAngle = Math.cos(degrees);
		double sinAngle = Math.sin(degrees);
        double[][] T = { {cosAngle,-sinAngle,0.00}, {sinAngle,cosAngle,0.00}, {0.00,0.00,1.00} };

	    for (int i = 0 ; i < num; i++) {
            double x1 = (double) lines.get(i).getx1();
            double y1 = (double) lines.get(i).gety1();;
            double[][] line1 = { {x1, y1, 1} };
            double[][] result1 = Matrix.multiply(line1, T);
            double xpoint1 = round(result1[0][0], 1);
            double ypoint1 = round(result1[0][1], 1);

            System.out.print("(" + xpoint1);
            System.out.print("," + ypoint1 + ")");

            double x2 = (double) lines.get(i).getx2();
            double y2 = (double) lines.get(i).gety2();
            double[][] line2 = { {x2, y2, 1} };
            double[][] result2 = Matrix.multiply(line2, T);
            double xpoint2 = round(result2[0][0],1);
            double ypoint2 = round(result2[0][1],1);
            System.out.print("(" + xpoint2);
            System.out.print("," + ypoint2 + ")");
            System.out.println();
            //Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
            Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
            datalines.add(i, temp);
    }
    return datalines;
}
   
/*
 * Scale - `Sx' and `Sy' are the horizontal and vertical scaling factors; center of scale is 
 * at Cx, Cy
 */
public static ArrayList<Line> Scale(double Sx, double Sy, int Cx, int Cy, ArrayList<Line> datalines, int num) {
//translate first
ArrayList<Line> lines = new ArrayList<>();
double[][] Tmatrix = { {1,0,0},{0,1,0},{-Cx,-Cy,1} };
double[][] Smatrix = { {Sx,0,0},{0,Sy,0},{0,0,1} };
double[][] result = Matrix.multiply(Tmatrix, Smatrix);

double[][] Tmatrix2 = { {1,0,0},{0,1,0},{Cx,Cy,1} };
double[][] overall = Matrix.multiply(result, Tmatrix2);

for (int i = 0; i < num; i++) {
    int x1 = datalines.get(i).getx1();
    int y1 = datalines.get(i).gety1();
    double[][] line1 = { {x1, y1, 1} };
    double[][] result1 = Matrix.multiply(line1, overall);
    double xpoint1 = round(result1[0][0], 1);
    double ypoint1 = round(result1[0][1],1);

    int x2 = datalines.get(i).getx2();
    int y2 = datalines.get(i).gety2();
    double[][] line2 = { {x2, y2, 1} };
    double[][] result2 = Matrix.multiply(line2, overall);
    double xpoint2 = round(result2[0][0], 1);
    double ypoint2 = round(result2[0][1],1);

    Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
    System.out.println("(" + (int)xpoint1 + "," + (int)ypoint1 + "," + (int)xpoint2 + "," + (int)ypoint2 + ")");
    //temp.myString();
    lines.add(i, temp);
}
return lines;
}

/* 
 * Rotation - angle of rotation is `angle' degrees (clockwise); Center of rotation is 
 * at Cx, Cy.
 */
public static ArrayList<Line> Rotate(double degrees, int Cx, int Cy, ArrayList<Line> dlines, int num ) {
    ArrayList<Line> lines = new ArrayList<>();
    degrees = Math.toRadians(degrees);
    double cosAngle = Math.cos(degrees);
    double sinAngle = Math.sin(degrees);
    
    double[][] Tmatrix = { {1,0,0},{0,1,0},{-Cx,-Cy,1} };
    double[][] Rmatrix = { {cosAngle,-sinAngle,0.00}, {sinAngle,cosAngle,0.00}, {0.00,0.00,1.00} };
    double[][] result = Matrix.multiply(Tmatrix, Rmatrix);

    double[][] Tmatrix2 = { {1,0,0},{0,1,0},{Cx,Cy,1} };
    double[][] overall = Matrix.multiply(result, Tmatrix2);
    
    for (int i = 0; i < num; i++) {
        int x1 = dlines.get(i).getx1();
        int y1 = dlines.get(i).gety1();
        double[][] line1 = { {x1, y1, 1} };
        double[][] result1 = Matrix.multiply(line1, overall);
        double xpoint1 = round(result1[0][0], 1);
        double ypoint1 = round(result1[0][1], 1);;

        int x2 = dlines.get(i).getx2();
        int y2 = dlines.get(i).gety2();   
        double[][] line2 = { {x2, y2, 1} };
        double[][] result2 = Matrix.multiply(line2, overall);
        double xpoint2 = round(result2[0][0], 1);
        double ypoint2 = round(result2[0][1],1);
        
        Line temp = new Line((int)xpoint1,(int)ypoint1,(int)xpoint2,(int)ypoint2);
        temp.myString();
        lines.add(i, temp);
        }
    return lines;
}

/*
 * Main method of execution responsible for user input and calling functions to display 2D
 * line transformations to the screen.
 */
    public static void main(String Args[]) throws IOException, FileNotFoundException {
    //init
        Scanner keyboard = new Scanner(System.in);
        JFrame frame = new JFrame("Geometric Transformations");
        Drawing panel = new Drawing(1000, 1000);
        // Calls inputLines after reading in the text file with line coordinates
        //panel.setNumLines(getInput());
        boolean hasFile = false;
        boolean isRunning = true;

        while (isRunning) {  
            System.out.println("Which operation would you like to perform?");
            System.out.println("0: Import text file.");
            System.out.println("1: Draw Lines");
            System.out.println("2: Basic Translate");
            System.out.println("3: Basic Scale");
            System.out.println("4: Basic Rotate");
            System.out.println("5: Scale");
            System.out.println("6: Rotate");
            System.out.println("7: Export Text File");
            System.out.println("8: Exit");
            int operation = keyboard.nextInt();
            while ((hasFile == false) && (operation != 0)) {
                System.out.println("You must import a text file before performing this operation.(0)");
                operation = keyboard.nextInt();
                if ((operation == 0) || (operation == 8)) {
                    break;
                }
            }
        switch (operation) {
            case 0:
            panel.setNumLines(getInput());
            System.out.println("Text file successfully loaded.");
            hasFile = true;
            break;
            case 1:
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 2:
            System.out.println("Please enter the amount of pixels to shift the x-coordinates.");
            int tx = keyboard.nextInt();
            System.out.println("Please enter the amount of pixels to shift the y-coordinates.");
            int ty = keyboard.nextInt();
            dataLines = basicTranslate(tx, ty, dataLines, numOfLines);
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 3:
            System.out.println("Please enter the factor of scale for the x-coordinates.");
            double bscalex = keyboard.nextDouble();
            System.out.println("Please enter the factor of scale for the y-coordinates.");
            double bscaley = keyboard.nextDouble();
            dataLines = basicScale(bscalex, bscaley, dataLines, numOfLines);
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 4:
            System.out.println("Please enter the degrees you want to rotate the lines.");
            int bdegree = keyboard.nextInt();
            dataLines = basicRotate(bdegree, dataLines, numOfLines);
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 5:
            System.out.println("Please enter the factor of scale for the x-direction");
            double scalex = keyboard.nextDouble();
            System.out.println("Please enter the factor of scale for the y-direction.");
            double scaley = keyboard.nextDouble();
            System.out.println("Please enter the center of scale x-coordinate");
            int centerx = keyboard.nextInt();
            System.out.println("Please enter the center of scale y-coordinate");
            int centery = keyboard.nextInt();
            dataLines = Scale(scalex, scaley, centerx, centery, dataLines, numOfLines);
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 6:
            System.out.println("Please enter the center of rotation for the x-coordinates");
            int rotatex = keyboard.nextInt();
            System.out.println("Please enter the center of rotation for the  y-coordinates");
            int rotatey = keyboard.nextInt();
            System.out.println("Please enter the degrees you want to rotate the point around the axis of...");
            int degree = keyboard.nextInt();
            dataLines = Rotate(degree, rotatex, rotatey, dataLines, numOfLines);
            panel.displayPixels(dataLines,numOfLines);
            frame.add(panel);
            frame.validate();
            frame.pack();
            frame.setVisible(true);
            break;
            case 7:
            panel.outputLines(dataLines, numOfLines);
            break;
            case 8:
            System.out.println("Goodbye!");
            System.exit(0);
            break;
            default:
            System.out.println("Invalid input.");
    }        
}
keyboard.close();
    } //main

} // Drawing.java
