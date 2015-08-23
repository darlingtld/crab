package createsh.pojo;

/**
 * Created by darlingtld on 2015/5/16.
 */
public enum Type {
    MACHINE("机器"),
    CARD("卡券"),
    SHUCAISHUIGUO("蔬菜水果"),
    QINROUDANLEI("禽肉蛋类"),
    SHUICHANDONGHUO("水产冻货"),
    MIMIANLIANGYOU("米面粮油"),
    TIAOLIAOQITA("调料其他"),
    CANCHUYONGPIN("餐厨用品"),
    JIUSHUIYINLIAO("酒水饮料");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
