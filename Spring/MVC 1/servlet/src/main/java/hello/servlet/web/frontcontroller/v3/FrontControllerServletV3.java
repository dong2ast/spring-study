package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>(); //컨트롤러 포털

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI(); //요청이 온 url 주소 확인 가능

        ControllerV3 controller = controllerMap.get(requestURI); // 해당 url로 map에서 controller 호출
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap

        Map<String, String> paramMap = createParamMap(request); //request에 들어온 모든 param을 map으로 만듬
        ModelView mv = controller.process(paramMap); //컨트롤러를 호출해 paramMap을 넘기고 model정보가 포함된 view를 리턴

        String viewName = mv.getViewName(); //modelView에서 view의 논리 이름을 받음(jsp호출위한 경로의 핵심 이름)
        MyView myView = viewResolver(viewName); //viewResolver를 통해 논리 이름을 실제 경로로 변환해서 MyView타입으로 변환(jsp로 가기위한 view)

        myView.render(mv.getModel(), request, response); //jsp로 이동 [jsp render에서 model도 필요하기 때문에 같이 넘김 request set은 myview class 내에서 설정]

    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) { //모든 파라미터를 저장한 map 생성
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
