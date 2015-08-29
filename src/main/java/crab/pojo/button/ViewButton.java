package crab.pojo.button;

/**
 * Created by tangld on 2015/3/30.
 */
public class ViewButton extends CommonButton {
    private String type = TYPE_VIEW;
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
