package org.jeecgframework.tag.vo.easyui;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: TreeModel
 * @Description: TODO(树形模型设置类)
 * @author Fish
 * @date 2016-1-13 下午07:54:22
 *
 */
public class TreeModel implements java.io.Serializable {
    private String id;
    private String text;
    private String iconCls;
    private String checked;
    private String state;
    private Map<String, Object> attributes;
    private List<TreeModel> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }

}
