package etu1918.framework.mapping;

public class ModelView {
    String view;
    //HashMap data

    public ModelView() {
    }

    //addItem dans data

    public ModelView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
