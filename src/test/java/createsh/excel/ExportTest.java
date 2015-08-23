package createsh.excel;

import createsh.service.OrderService;
import createsh.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tangl9 on 2015-07-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class ExportTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void exportProduct() {
        ExcelFactory.exportProducts("菜品列表.xlsx", productService.getAll(), productService.getTypeMap(), productService.getProcurement());
    }

    @Test
    public void exportOrders() {
        ExcelFactory.exportOrders("订单列表.xlsx", orderService.getAll(), orderService.getStatusList());
    }

    @Test
    public void exportDispatches() {
        ExcelFactory.exportDispatches("配送列表.xlsx", orderService.getDispatchList());
    }
}
