public class Line {

    private int x1,x2,y1,y2;
	private double x,y,x0,y0;
    
    public Line(int x1, int y1, int x2, int y2) 
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
    }//DataLine

	public Line(double x, double y, double x0, double y0) 
	{
		this.x = x;
		this.y = y;
		this.x0 = x0;
		this.y0 = y0;
    }//DataLine

    //getters and setters
    public int getx1() 
	{
		return x1;
    }//getx1

    public void setx1(int x) 
	{
		x1 = x;
    }//setx1

    public int gety1() 
	{
		return y1;
    }//gety1

    public void sety1(int y) 
	{
		y1 = y;
    }//sety1

    public int getx2() 
	{
		return x2;
    }//getx2

    public void setx2(int x) 
	{
		x2 = x;
    }//setx2

    public int gety2() 
	{
		return y2;
    }//gety2

    public void sety2(int y) 
	{
		y2 = y;
    }//sety1
	public void myString() {
		System.out.println("(" + this.getx1() + "," + this.gety1() + ")" + "(" + this.getx2() + "," + this.gety2() + ")");
	}
    
}// Line
    