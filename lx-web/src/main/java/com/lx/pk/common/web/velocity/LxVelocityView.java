package com.lx.pk.common.web.velocity;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lx.pk.core.spring.context.properties.LxPropertyPlaceholderConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityView;

/**
 * @author 项目集成Velocity前端框架
 */
@SuppressWarnings("all")
public class LxVelocityView extends VelocityView {
    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        for (
                Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
            String attribute = (String) en.nextElement();
            if ("_csrf".equals(attribute)) {
                Object attributeValue = request.getAttribute(attribute);
                if (logger.isDebugEnabled()) {
                    logger.debug("Exposing request attribute '" + attribute + "' with value [" + attributeValue
                            + "] to model");
                }
                model.put(attribute, attributeValue);
            }
        }
        super.renderMergedTemplateModel(model, request, response);
    }

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);

        model.put("base_url", getPropString("base_url"));
        model.put("cdn_base_url", getPropString("cdn_base_url"));
        model.put("passport_url", getPropString("sso.passport.url"));
        model.put("project_model", getPropString("project.model"));     //应用模式(local本地 dev开发 test测试 pre预发 production线上)
    }

    private String getPropString(String key) {
        Object obj = LxPropertyPlaceholderConfigurer.getContextProperty(key);
        if (null != obj) {
            return ((String) obj).trim();
        }
        return "";
    }
}
