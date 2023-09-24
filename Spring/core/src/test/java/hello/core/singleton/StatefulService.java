package hello.core.singleton;

public class StatefulService {

    private  int price; //상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + "price= " + price);
        this.price = price; //여기가 문제!
        //빈에서 내부 메서드로 내부 변수를 변경하려 할때를 말하는 듯
    }

    public int getPrice() {
        return price;
    }
}
