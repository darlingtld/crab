package crab.pojo.button;

/**
 * Created by darlingtld on 2015/2/20.
 */

/**
 * ���Ӱ�ť������ť��
 */
public class ComplexButton extends Button {
    private CommonButton[] sub_button;

    public CommonButton[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(CommonButton[] sub_button) {
        this.sub_button = sub_button;
    }
}
