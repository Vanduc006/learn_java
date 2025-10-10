package course.Inheritance;

public class Car extends Vehicle{ // subclass
    private String modelName = "911";
    public static void main(String[] args) {
        Car car = new Car();
        Vehicle vehicle = new Vehicle();

        System.out.println(vehicle.brandName);
        
        car.honk(); // call method honk() from vehicle
        System.out.println(car.brandName + car.modelName);
    }

}
