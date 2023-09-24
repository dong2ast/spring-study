package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String viewName; // viewPath
    private Map<String, Object> model = new HashMap<>(); //model (임시 저장소)

    public ModelView(String viewName) { //생성자로 jsp로 이동하는 경로 획득 viewPath
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
