package etu1918.framework.mapping;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String, Object> data;
    HashMap<String, Object> sessionToAdd;
    boolean isJson = false;

    public ModelView() {
        this.data = new HashMap<>();
        this.sessionToAdd = new HashMap<>();
    }

    public ModelView(String view) {
        this.view = view;
        this.data = new HashMap<>();
        this.sessionToAdd = new HashMap<>();
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public HashMap<String, Object> getSessionToAdd() {
        return sessionToAdd;
    }

    public void setSessionToAdd(HashMap<String, Object> sessionToAdd) {
        this.sessionToAdd = sessionToAdd;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void addItem(String key, Object o){
        this.data.put(key, o);
    }

    public void addSession(String key, Object o){
        this.sessionToAdd.put(key, o);
    }

}
