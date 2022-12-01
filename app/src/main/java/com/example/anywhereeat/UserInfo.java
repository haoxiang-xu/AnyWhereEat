package com.example.anywhereeat;

public class UserInfo {

    String variable;
    String value;
    int icon;

    public UserInfo(String variable, String value, int icon) {
        this.variable = variable;
        this.value = value;
        this.icon = icon;
    }

    public String getVariable() {
        return variable;
    }

    public String getValue() {
        return value;
    }

    public int getIcon() {
        return icon;
    }
}
