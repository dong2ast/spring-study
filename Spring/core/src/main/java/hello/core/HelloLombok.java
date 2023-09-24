package hello.core;


import lombok.Getter;
import lombok.Setter;

@Getter //롬복
@Setter
public class HelloLombok {
    
    private String name;
    private int age;
    
    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("ASdas");
        
        String name = helloLombok.getName();
        System.out.println("name = " + name);
    }
}
