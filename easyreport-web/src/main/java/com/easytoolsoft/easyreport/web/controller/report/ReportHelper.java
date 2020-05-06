package com.easytoolsoft.easyreport.web.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.form.QueryParamFormView;
import com.easytoolsoft.easyreport.common.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.common.util.DateUtils;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.domain.metadata.service.impl.ReportService;
import com.easytoolsoft.easyreport.domain.report.impl.TableReportService;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import com.easytoolsoft.easyreport.domain.metadata.vo.QueryParameter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class ReportHelper {
    private static ReportService reportService;
    private static TableReportService tableReportService;

    @Autowired
    public ReportHelper(ReportService reportService, TableReportService tableReportService) {
        ReportHelper.reportService = reportService;
        ReportHelper.tableReportService = tableReportService;
    }

    public static Report getReportMetaData(String uid) {
        return reportService.getByUid(uid);
    }

    public static JSONObject getDefaultChartData() {
        return new JSONObject(6) {
            {
                put("dimColumnMap", null);
                put("dimColumns", null);
                put("statColumns", null);
                put("dataRows", null);
                put("msg", "");
            }
        };
    }

    public static ReportDataSource getReportDataSource(Report report) {
        return reportService.getReportDataSource(report.getDsId());
    }

    public static ReportParameter getReportParameter(Report report, Map<?, ?> parameters) {
        return tableReportService.getReportParameter(report, parameters);
    }

    public static void renderByFormMap(String uid, ModelAndView modelAndView, HttpServletRequest request) {
        Report report = reportService.getByUid(uid);
        ReportOptions options = reportService.parseOptions(report.getOptions());
        Map<String, Object> buildInParams = tableReportService.getBuildInParameters(request.getParameterMap(), options.getDataRange());
        Map<String, HtmlFormElement> formMap = tableReportService.getFormElementMap(report, buildInParams, 1);
        modelAndView.addObject("formMap", formMap);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
    }

    public static void renderByTemplate(String uid, ModelAndView modelAndView, QueryParamFormView formView,
                                        HttpServletRequest request) {
        Report report = reportService.getByUid(uid);
        //布局列、统计列 展现形式，  展现数据间隔时间
        ReportOptions options = reportService.parseOptions(report.getOptions());
        
        //列信息元数据
        List<ReportMetaDataColumn> metaDataColumns = reportService.parseMetaColumns(report.getMetaColumns());
        
        //系统内置参数   内置参数  与  request 参数做差减 
        Map<String, Object> buildInParams = tableReportService.getBuildInParameters(request.getParameterMap(), options.getDataRange());
        
        //控件参数 加载
        List<HtmlFormElement> dateAndQueryElements = tableReportService.getDateAndQueryParamFormElements(report, buildInParams);
        
        //
        HtmlFormElement statColumnFormElements = tableReportService.getStatColumnFormElements(metaDataColumns, 0);
        System.out.println("statColumHtmlText:"+ formView.getFormHtmlText(statColumnFormElements));;
        //
        List<HtmlFormElement> nonStatColumnFormElements = tableReportService.getNonStatColumnFormElements(metaDataColumns);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
        modelAndView.addObject("comment", report.getComment().trim());
        modelAndView.addObject("formHtmlText", formView.getFormHtmlText(dateAndQueryElements));
        modelAndView.addObject("statColumHtmlText", formView.getFormHtmlText(statColumnFormElements));
        modelAndView.addObject("nonStatColumHtmlText", formView.getFormHtmlText(nonStatColumnFormElements));
    }

    public static void generate(String uid, JSONObject data, HttpServletRequest request) {
        generate(uid, data, request.getParameterMap());
    }

    public static void generate(String uid, JSONObject data, Map<?, ?> parameters) {
        generate(uid, data, new HashMap<>(0), parameters);
    }

    /**
     * 
     * @param uid       报表ID
     * @param data      json对象
     * @param attachParams  内置的参数
     * @param parameters http请求的form表单
     */
    public static void generate(String uid, JSONObject data, Map<String, Object> attachParams, Map<?, ?> parameters) {
        if (StringUtils.isBlank(uid)) {
            data.put("htmlTable", "uid参数为空导致数据不能加载！");
            return;
        }
        ReportTable reportTable = generate(uid, attachParams, parameters);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
        data.put("metaDataColumnCount", reportTable.getMetaDataColumnCount());
    }

    public static void generate(Queryer queryer, ReportParameter reportParameter, JSONObject data) {
        ReportTable reportTable = tableReportService.getReportTable(queryer, reportParameter);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static void generate(ReportMetaDataSet metaDataSet, ReportParameter reportParameter, JSONObject data) {
        ReportTable reportTable = tableReportService.getReportTable(metaDataSet, reportParameter);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static ReportTable generate(String uid, Map<?, ?> parameters) {
        return generate(uid, new HashMap<>(0), parameters);
    }

    
    /***
     * 对多参数，非代码内替换型SQL，判断 参数是否为空值，如果非空值进行参数附加
     * @param uid
     * @param attachParams 并未使用
     * @param parameters   从request获取的请求参数
     * @return
     */
    public static ReportTable generate(String uid, Map<String, Object> attachParams, Map<?, ?> parameters) {
    	
        Report report = reportService.getByUid(uid);
        ReportOptions options = reportService.parseOptions(report.getOptions());
        
        //表单构建
        Map<String, Object> formParams = tableReportService.getFormParameters(parameters, options.getDataRange());        
        //where query,后调整添加
        Map<String, Object> queryParamsMap = tableReportService.getQueryAppendParameters(parameters, options.getDataRange());
        
        if (MapUtils.isNotEmpty(attachParams)) {
            for (Entry<String, Object> es : attachParams.entrySet()) {
                formParams.put(es.getKey(), es.getValue());
            }
        }
        return tableReportService.getReportTable(report, formParams,queryParamsMap);
    }

    public static void exportToExcel(String uid, String name, String htmlText, HttpServletRequest request,
                                     HttpServletResponse response) {
        htmlText = htmlText.replaceFirst("<table>", "<tableFirst>");
        htmlText = htmlText.replaceAll("<table>",
                "<table cellpadding=\"3\" cellspacing=\"0\"  border=\"1\" rull=\"all\" style=\"border-collapse: collapse\">");
        htmlText = htmlText.replaceFirst("<tableFirst>", "<table>");
        try (OutputStream out = response.getOutputStream()) {
            String fileName = name + "_" + DateUtils.getNow("yyyyMMddHHmmss");
            fileName = new String(fileName.getBytes(), "ISO8859-1") + ".xls";
            if ("large".equals(htmlText)) {
                Report report = reportService.getByUid(uid);
                ReportOptions options = reportService.parseOptions(report.getOptions());
                Map<String, Object> formParameters = tableReportService.getFormParameters(request.getParameterMap(), options.getDataRange());
                
                //where query
                Map<String, Object> queryParamsMap = tableReportService.getQueryAppendParameters(request.getParameterMap(), options.getDataRange());

                ReportTable reportTable = tableReportService.getReportTable(report, formParameters,queryParamsMap);
                htmlText = reportTable.getHtmlText();
            }
            response.reset();
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.addCookie(new Cookie("fileDownload", "true"));
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
            //out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}); // 生成带bom的utf8文件
            out.write(htmlText.getBytes());
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
