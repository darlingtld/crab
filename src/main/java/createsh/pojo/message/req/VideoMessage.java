package createsh.pojo.message.req;

/**
 * ��Ƶ��Ϣ
 */
public class VideoMessage extends BaseMessage {
    // ��Ƶ��Ϣý��id
    private String MediaId;
    // ��Ƶ��Ϣ����ͼ��ý��id
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
