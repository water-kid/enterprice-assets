package com.cj.project;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

/**
 * @author cc
 * @date 2024-07-20 11:02
 **/

public class GenerateCode {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql:///cc_shop", "root", "root1234")
                .globalConfig(builder -> builder
                        .author("qq:2577034655")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.cj.project.answer")
                        .entity("domain")
                        .mapper("mapper")
//                        .service("service")
                        .serviceImpl("service")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder.addInclude("t_sku").addTablePrefix("t_")
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
