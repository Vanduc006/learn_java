package course.ModifyAcess;

public class Case2 extends Level{ // inheritance
    private void Test() {
        // Level object = new Level();
        super.c = 10;
    }
    public static void main(String[] args) {    
        Level object = new Level();
        object.c = 10;
        
    }
}
