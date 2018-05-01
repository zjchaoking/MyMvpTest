package com.kaicom.dao;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by jc on 2016/4/14 0014.
 */
public class Main {


    private static int BIZ_DB_VERSION = 1;

    public static void main(String[] args) throws Exception {
        createBizDB();//创建业务数据库
    }



    private static void createBizDB() throws Exception {
        Schema schema = new Schema(BIZ_DB_VERSION, "com.kaicom.mymvptest.datasource.entity");
        schema.setDefaultJavaPackageDao("com.kaicom.mymvptest.datasource.gen");
        schema.enableKeepSectionsByDefault();
        BizGenerator.main(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

}
