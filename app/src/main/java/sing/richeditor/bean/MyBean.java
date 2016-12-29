package sing.richeditor.bean;

import java.io.Serializable;

public class MyBean implements Serializable {

    public String type;//0为文字，1为图片
    public String txt;

    public MyBean(String type, String txt) {
        this.type = type;
        this.txt = txt;
    }
}