package com.anxi.activiti.web.conf;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * 配置装饰器
 *
 * @author linjing
 */
public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/workflow/*", "/workflow/index")
                .addExcludedPath("/workflow/index")
                .addExcludedPath("/plugin/*");
    }
}
