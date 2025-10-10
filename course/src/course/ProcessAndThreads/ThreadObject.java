package course.ProcessAndThreads;

public class ThreadObject extends Thread {
    public void run() {
        System.out.println("Start new thread");
        long sum = 0L;
        for (long i=0L; i < 1000000000; i++) {
            sum += i;
        }
        System.out.println("End thread");
        System.out.println(sum);
    }
    public static void main(String[] args) {
        // (new ThreadObject()).start();
        int MAX = 10;
        for (int i=0; i < MAX; i++) {
            (new ThreadObject()).start();
        }

        System.out.println("OK");
    }
}
