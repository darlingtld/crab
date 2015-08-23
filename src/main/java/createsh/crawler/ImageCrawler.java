package createsh.crawler;

import createsh.pojo.Product;
import createsh.service.ProductService;
import createsh.util.ImageUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by darlingtld on 2015/5/30 0030.
 */
@Component
public class ImageCrawler {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductCrawler.class);

    private static final int CONNECTION_TIME_OUT = 60 * 1000;
    private static final double PIC_PROPORTION = 1.5;

    public static void getProductImg(Product product, String picUUID) throws IOException {
        String googleImgUrl = "https://image.glgoo.com/search?site=imghp&tbm=isch&q=%s&gws_rd=cr";
        Document doc;
        System.out.println(String.format("fetching image for %s", product.getName()));
        String url = String.format(googleImgUrl, URLEncoder.encode(product.getName(), "utf-8"));
        doc = Jsoup.connect(url).userAgent("Mozilla").timeout(CONNECTION_TIME_OUT).get();
        Elements eles = doc.select("img");
        TreeMap<Double, String> sizeImgUrlMap = new TreeMap<>();
        for (Element element : eles) {
//                System.out.println(element);
            double height = Double.parseDouble(element.attr("height"));
            double width = Double.parseDouble(element.attr("width"));
            String src = element.attr("src");
            Double size = height / width < PIC_PROPORTION ? PIC_PROPORTION - height / width : height / width - PIC_PROPORTION;
            sizeImgUrlMap.put(size, src);
        }
//            System.out.println(sizeImgUrlMap);
        String imgUrl = sizeImgUrlMap.firstEntry().getValue();

        String imgLocation = ImageUtil.download(picUUID, imgUrl);
        System.out.println("images/" + imgLocation);
        product.setPicurl("images/" + imgLocation);

    }

    public void fullfillImages() {
        logger.info("Start to fullfill images at {}", new Date());
        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            if (null != product.getPicurl() && product.getPicurl().contains("pic")) {
                continue;
            }
            String picUUID = product.generatePicurlHash();
            try {
                ImageCrawler.getProductImg(product, picUUID);
                product.setPicurl("images/" + picUUID + ".jpg");
                productService.upsert(product);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
