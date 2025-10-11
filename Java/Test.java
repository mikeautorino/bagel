import bagel.Vector;
import java.io.File;

public class Test 
{
    public static void main(String[] args)
    {
        System.out.println("Hello, BAGEL!");
        System.out.println(new File(".").getAbsolutePath());
        System.out.println("testing Vector class");
        Vector v = new Vector(3, 4);
        System.out.println( v );
        System.out.println( v.getLength() );
        v.multiply(2);
        System.out.println( v );
        System.out.println( v.getLength() );
        System.out.println( v.getAngle() );
        v.setAngle(45);
        v.setLength(1.4142);
        System.out.println( v );
        

    }
    
    
}
