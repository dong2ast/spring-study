package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) //request때 생성되지만, proxy를 통해 껍데기를 미리 넣어놓음(가짜)ㄸ
//자동 주입되어서 싱글톤 빈이 됨
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) { //requestURL 처음에 알 수 없음
        this.requestURL = requestURL;
    }

    public void log(String message) { //log 남기는 기능
        System.out.println("["+uuid+"]"+"["+requestURL+"] "+message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString(); //시작할 때 고유의 아이디 생성
        System.out.println("["+uuid+"] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("["+uuid+"] request scope bean close:" + this);
    }


}
