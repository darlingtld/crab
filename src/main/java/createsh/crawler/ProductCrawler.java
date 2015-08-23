package createsh.crawler;

import createsh.pojo.Product;
import createsh.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by tangld on 2015/5/26.
 */
@Service
public class ProductCrawler {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductCrawler.class);
    private static ConcurrentHashMap<String, Product> productMap = new ConcurrentHashMap<>();
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static final int TIME_TO_WAIT_FOR_RESULTS = 45;

    public void crawl() {
        productMap = new ConcurrentHashMap<>();
        logger.info("Crawling vegetables and fruits");
        new VegFruitCrawler(threadPoolExecutor, productMap).execute();

        logger.info("Crawling from shian");
        new ShianGovCrawler(threadPoolExecutor, productMap).execute();

        new Thread() {
            @Override
            public void run() {
                long upTime = 0;
                while (true) {
                    try {
                        Thread.sleep(5000);
                        upTime += 5;
                        System.out.println(upTime + " seconds");
                        if (upTime >= TIME_TO_WAIT_FOR_RESULTS) {
                            for (String key : productMap.keySet()) {
                                System.out.println(key + " --> " + productMap.get(key));
                                productService.upsert(productMap.get(key));

                            }
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

}
