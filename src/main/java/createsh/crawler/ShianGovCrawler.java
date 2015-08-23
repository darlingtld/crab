package createsh.crawler;

import createsh.pojo.Product;
import createsh.pojo.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by darlingtld on 2015/5/30 0030.
 */
public class ShianGovCrawler {
    private static final Logger logger = LoggerFactory.getLogger(ProductCrawler.class);

    private ThreadPoolExecutor threadPoolExecutor;
    private ConcurrentHashMap<String, Product> productMap;

    public ShianGovCrawler(ThreadPoolExecutor threadPoolExecutor, ConcurrentHashMap<String, Product> productMap) {
        this.productMap = productMap;
        this.threadPoolExecutor = threadPoolExecutor;
    }

//    public static void main(String[] args) {
//        new ShianGovCrawler(null, null).execute();
//    }

    public void execute() {
        try {
            String fileName = "d:\\product_shian.txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                final String finalLine = line;
                threadPoolExecutor.execute(new Thread() {
                    @Override
                    public void run() {
                        String[] eles = finalLine.split("\\s+");
                        Product product = new Product();
                        product.setName(eles[0]);
                        product.setPrice(Double.parseDouble(eles[1]));
                        product.setUnit("千克");
                        product.setType(Type.SHUCAISHUIGUO);
                        product.setOnsale(0);
                        try {
                            ImageCrawler.getProductImg(product, product.generatePicurlHash());
                            productMap.putIfAbsent(product.getName(), product);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}