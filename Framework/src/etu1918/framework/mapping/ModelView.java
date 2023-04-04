package etu1918.framework.mapping;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String, Object> data;

    public ModelView() {
        this.data = new HashMap<>();
    }

    public ModelView(String view) {
        this.view = view;
        this.data = new HashMap<>();
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
}
