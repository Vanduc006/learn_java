package course.Interface;

public interface Farm {
    public void feed();
    public void sleep();

    default public void kill() {
        System.out.println("Killing...");
    }
} 