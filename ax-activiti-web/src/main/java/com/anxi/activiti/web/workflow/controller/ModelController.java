package com.anxi.activiti.web.workflow.controller;

import com.anxi.activiti.service.api.ActRepositoryService;
import com.anxi.activiti.vo.ActModelPageQuery;
import com.anxi.activiti.vo.ActModelVO;
import com.anxi.activiti.vo.CreateModelDTO;
import com.anxi.activiti.web.conf.FormPostParam;
import com.anxi.activiti.web.workflow.util.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

/**
 * 流程模型控制器
 *
 * @author henryyan
 */
@Slf4j
@Controller
@RequestMapping(value = "/workflow/model")
public class ModelController {

    @Resource
    private ActRepositoryService actRepositoryService;

    /**
     * 模型列表
     */
    @RequestMapping(value = "list")
    public String modelList(@FormPostParam ActModelPageQuery actModelPageQuery, Model model) {
        PageInfo<ActModelVO> pageInfo = actRepositoryService.pageFindModels(actModelPageQuery);
        Page<ActModelVO> page = new Page<>(actModelPageQuery.getPageNum(), actModelPageQuery.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
        model.addAttribute("queryParam", actModelPageQuery);
        model.addAttribute("page", page);
        return "/modules/act/actModelList";
    }

    /**
     * 创建模型视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(HttpServletRequest request, HttpServletResponse response) {
        return "/modules/act/actModelCreate";
    }

    /**
     * 创建模型
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@FormPostParam CreateModelDTO createModelDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
            ActModelVO model = actRepositoryService.createModel(createModelDTO);
            response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + model.getActModelId());
        } catch (Exception e) {
            log.error("创建模型失败：", e);
        }
    }

    /**
     * 查询模型图片
     */
    @RequestMapping(value = "showModelPicture")
    public void showModelPicture(HttpServletResponse response, String modelId) throws Exception {
        ActModelVO modelData = actRepositoryService.getModel(modelId);
        ObjectNode modelNode = null;
        try {
            modelNode = (ObjectNode) new ObjectMapper().readTree(actRepositoryService.getModelEditorSource(modelData.getActModelId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(model,
                "png",
                Collections.<String>emptyList(), Collections.<String>emptyList(),
                "WenQuanYi Micro Hei", "WenQuanYi Micro Hei",
                null, null, 1.0);
        OutputStream out = response.getOutputStream();
        for (int b = -1; (b = inputStream.read()) != -1; ) {
            out.write(b);
        }
        out.close();
        inputStream.close();
    }

    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "deploy/{modelId}")
    public String deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
        actRepositoryService.modelDeploy(modelId);
        return "redirect:/workflow/model/list";
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequestMapping(value = "export")
    public void export(@RequestParam("modelId") String modelId, @RequestParam(value = "type", defaultValue = "bpmn") String type, HttpServletResponse response) {
        try {
            ActModelVO modelData = actRepositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = actRepositoryService.getModelEditorSource(modelData.getActModelId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if (type.equals("bpmn")) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if (type.equals("json")) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }

    @RequestMapping(value = "delete/{modelId}")
    public String delete(@PathVariable("modelId") String modelId) {
        actRepositoryService.deleteModel(modelId);
        return "redirect:/workflow/model/list";
    }

}
