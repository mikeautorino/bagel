import bagel.Vector2;

public class Test 
{
    public static void main(String[] args)
    {
        System.out.println("Hello, BAGEL!");

        System.out.println("testing Vector class");
        Vector2 v = new Vector2(3, 4);
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
