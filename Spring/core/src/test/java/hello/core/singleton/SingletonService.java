package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    //static: 메모리에 할당된다는 뜻, 여러 객체가 해당 메모리를 공유하게 됨
    //보통의 값은 static이 아닌 heap 영역에 존재하며 인스터스 생성시 할당됨, but static은 class 처럼 시작과 동시에 할당됨
    //final: 값 변경 불가

    public static SingletonService getInstance() {
        return instance;
    }

    //생성자를 private로 막아서 외부에서 new 키워드로 객체 인스턴스 생기는 상황 막음
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
