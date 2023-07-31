package etu1918.framework.mapping;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Root
public class ModelView {
    String view;
    @ElementMap(entry = "element", key = "key", attribute = true, inline = true)
    HashMap<String, Object> data;
    HashMap<String, Object> sessionToAdd;
    boolean isJson = false;
    boolean isXml = false;
    boolean invalidateSession = false;
    List<String> sessionToRemove;

    public ModelView() {
        this.data = new HashMap<>();
        this.sessionToAdd = new HashMap<>();
        this.sessionToRemove = new ArrayList<>();
    }

    public ModelView(String view) {
        this.view = view;
        this.data = new HashMap<>();
        this.sessionToAdd = new HashMap<>();
        this.sessionToRemove = new ArrayList<>();
    }

    public boolean isXml() {
        return isXml;
    }

    public void setXml(boolean xml) {
        isXml = xml;
    }

    public void InvalidateSession() {
        this.invalidateSession = true;
    }

    public boolean isSessionInvalidate() {
        return invalidateSession;
    }

    public List<String> getSessionToRemove() {
        return sessionToRemove;
    }

    public void setSessionToRemove(List<String> sessionToRemove) {
        this.sessionToRemove = sessionToRemove;
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

    public void removeSession(String key){
        this.sessionToRemove.add(key);
    }

}
