package createsh.util;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

public class MySQL5LocalDialect extends MySQL5Dialect {
    public MySQL5LocalDialect() {
        super();
        registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using ?2)"));
    }
}