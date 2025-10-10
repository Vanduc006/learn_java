package course.Constructor;

public class Test {
    public static void main(String[] args) {

        // for (int i=0; i < args.length;i++) {
        //     System.out.println(args[i]);
        // }
        System.out.println("test Class student");
        Student st1 = new Student(); // constructor
        st1.name = "duc";
        st1.age = 19;
        st1.LearnJava();

        Student st2 = new Student("duc", 19);
        System.out.println("Birth year: " +st2.getBirthYear());
    }
}
