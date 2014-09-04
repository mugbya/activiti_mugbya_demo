package com.mugbya.core.common;


import com.mugbya.core.utils.PackageUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-20.
 */
public class WildcardSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private final static Logger log = Logger.getLogger(WildcardSqlSessionFactoryBean.class);

    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        List<String> packageList = PackageUtils.getPackages(typeAliasesPackage);
        StringBuilder absoluteTypeAliasesPackageBuilder = new StringBuilder();
        for(int idx = 0; idx < packageList.size(); idx++) {
            if(idx > 0) {
                absoluteTypeAliasesPackageBuilder.append(",");
            }
            String packageString = packageList.get(idx);
            absoluteTypeAliasesPackageBuilder.append(packageString);
        }
        String absoluteTypeAliasesPackage = absoluteTypeAliasesPackageBuilder.toString();
        super.setTypeAliasesPackage(absoluteTypeAliasesPackage);
    }
}
