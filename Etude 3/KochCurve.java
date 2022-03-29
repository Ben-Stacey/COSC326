
public class KochCurve
{
  private SketchPad myPaper;
  private DrawingTool myPencil;

  public KochCurve()
  {
    myPaper = new SketchPad(600,600);
    myPencil = new DrawingTool(myPaper);
  }

  public void draw()
  {
    drawKochCurve(6, 300);
  }

  private void drawKochCurve(double level, double sideLength)
  {
    if(level < 1)
       myPencil.forward(sideLength);

    else
    {
      drawKochCurve(level - 1, (sideLength)/3);
      myPencil.turnLeft(60);
      drawKochCurve(level - 1, (sideLength)/3);
      myPencil.turnRight(120);
      drawKochCurve(level - 1, (sideLength)/3);
      myPencil.turnLeft(60);   
      drawKochCurve(level - 1, (sideLength)/3);
    }
  }
}