package crab.controller;

import crab.pojo.Category;
import org.junit.Test;

/**
 * Created by darlingtld on 2015/5/16.
 */
public class EnumTest {

    @Test
    public void test(){
        Category category = Category.YECAILEI;
        System.out.println(category.getCategory());
        System.out.println(category.name());
    }
}
