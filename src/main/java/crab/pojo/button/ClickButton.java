package crab.pojo.button;

/**
 * Created by darlingtld on 2015/2/20.
 */

/**
 * ��ť�Ļ���
 */
public class ClickButton extends CommonButton {
    private String type = TYPE_CLICK;
    private String key;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
