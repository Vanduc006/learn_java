package course.Polymorphism;

public class BabyDog extends Dog{
    public void eat () {
        System.out.println("In BabyDog class");
    }
    public static void main(String[] args) {
        Animal dog = new Dog();
        dog.eat(); // call to method that class extends

        Animal[] animals = new Animal[5];
        animals[0] = new Animal();

        animals[0].eat();
        Animal babyDog = new BabyDog();
        babyDog.eat();
        // use method in superclass
    }
}
