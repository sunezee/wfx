package com.blb.wfx_goods.entity;

import lombok.Data;

import java.util.List;

@Data
public class ModuleTreeNodes {

    public static class State{
        private boolean checked;


        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
    private String moduleId;
    private boolean checked;
    private State state=new State();
    private String text;
    private String href;
    private List<ModuleTreeNodes> nodes;




    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<ModuleTreeNodes> getNodes() {
        return nodes;
    }

    public void setNodes(List<ModuleTreeNodes> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "ModuleTreeNodes{" +
                "text='" + text + '\'' +
                ", href='" + href + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
