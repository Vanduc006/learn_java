package course.Interface;

public class Pig extends LivingThing implements Animal, Farm{
    @Override
    public void breath() {

    }

    @Override
    public void animalSound() {
        System.out.println("sound Pig class");
    }

    @Override
    public void sleep() {
        System.out.println("sleep Pig class");
    }   

    @Override
    public void feed() {
        System.out.println("Feeding");
    }
}
