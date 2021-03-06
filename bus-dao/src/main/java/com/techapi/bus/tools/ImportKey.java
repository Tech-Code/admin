package com.techapi.bus.tools;

import com.techapi.bus.data.ImportUserKeyService;
import com.techapi.bus.util.ExcelUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 6/25/14.
 */
public class ImportKey {
    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportUserKeyService importUserKeyService = context
            .getBean(ImportUserKeyService.class);

    public static void main(String[] args) {

        new ImportKey().importUserKeyService.importUserKey(ExcelUtils.readKey());
    }
}
