package course.ModifyAcess;

public class Case1 {
    public static void main(String[] args) {
        Level firstOject = new Level(); 
        firstOject.a = 1;
        firstOject.b = 2;
        firstOject.c = 3;
        // we can't use private d in anothor class
    }
}

// public -> same class; package; subclass, universe
// default -> same class; package
// private -> same class
// protected -> same class, package, subclass

