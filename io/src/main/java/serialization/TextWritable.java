package serialization;

import org.apache.hadoop.io.Text;

public class TextWritable {
    public static void main(String[] args) {

        // Text
        Text t = new Text("hadoop");
        System.out.println("Length: " +  t.getLength());
        System.out.println("Byte Length: " + t.getBytes().length);
        System.out.println("t.charAt(2): " + t.charAt(2));
        System.out.println("(int) d: " + (int) 'd');
        System.out.println("Find a substring: " + t.find("do"));
        System.out.println("Finds 'o' from position 4 or later: " + t.find("o", 4));
        System.out.println("No match: " + t.find("pig"));

        // Mutability
        System.out.println("Text change: hadoop -> pig");
        t.set("pig");
        System.out.println("Length: " +  t.getLength());
        System.out.println("Byte Length: " + t.getBytes().length);

        // Resorting to String
        String text = t.toString();
        System.out.println("String text: " + text);
    }
}
