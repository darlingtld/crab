package crab;

import crab.util.ImageUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tangld on 2015/6/8.
 */
public class ImageUtilTest {

    @Test
    public void testDownloadImg() throws IOException {
        String imgUrl = "http://www.guoensi.com/UploadFiles/2009/12/15/jpg/200912151130225053.jpg";
        ImageUtil.download("qingcaitest",imgUrl);
    }
}
