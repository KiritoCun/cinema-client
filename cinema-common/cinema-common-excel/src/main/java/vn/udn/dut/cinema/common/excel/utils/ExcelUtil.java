package vn.udn.dut.cinema.common.excel.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.core.utils.file.FileUtils;
import vn.udn.dut.cinema.common.excel.convert.ExcelBigNumberConvert;
import vn.udn.dut.cinema.common.excel.core.CellMergeStrategy;
import vn.udn.dut.cinema.common.excel.core.DefaultExcelListener;
import vn.udn.dut.cinema.common.excel.core.ExcelListener;
import vn.udn.dut.cinema.common.excel.core.ExcelResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel related processing
 *
 * @author HoaLD
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtil {

    /**
     * Synchronous import (suitable for small data volumes)
     *
     * @param is input stream
     * @return converted collection
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return EasyExcel.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
    }


    /**
     * Use validation listener Asynchronous import Synchronous return
     *
     * @param is         input stream
     * @param clazz      object type
     * @param isValidate Whether Validator inspection, the default is yes
     * @return converted collection
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, boolean isValidate) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>(isValidate);
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * Use a custom listener Asynchronous import Custom return
     *
     * @param is       input stream
     * @param clazz    object type
     * @param listener custom listener
     * @return converted collection
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, ExcelListener<T> listener) {
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * export excel
     *
     * @param list      export data set
     * @param sheetName worksheet name
     * @param clazz     Entity class
     * @param response  response body
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, false, os);
        } catch (IOException e) {
            throw new RuntimeException("Export excel exception");
        }
    }

    /**
     * export excel
     *
     * @param list      export data set
     * @param sheetName worksheet name
     * @param clazz     Entity class
     * @param merge     Whether to merge cells
     * @param response  response body
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, merge, os);
        } catch (IOException e) {
            throw new RuntimeException("Export excel exception");
        }
    }

    /**
     * export excel
     *
     * @param list      export data set
     * @param sheetName worksheet name
     * @param clazz     Entity class
     * @param os        output stream
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, OutputStream os) {
        exportExcel(list, sheetName, clazz, false, os);
    }

    /**
     * export excel
     *
     * @param list      export data set
     * @param sheetName worksheet name
     * @param clazz     Entity class
     * @param merge     Whether to merge cells
     * @param os        output stream
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, OutputStream os) {
        ExcelWriterSheetBuilder builder = EasyExcel.write(os, clazz)
            .autoCloseStream(false)
            // automatic adaptation
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            // Automatic conversion of large values ​​to prevent distortion
            .registerConverter(new ExcelBigNumberConvert())
            .sheet(sheetName);
        if (merge) {
            // merge processor
            builder.registerWriteHandler(new CellMergeStrategy(list, true));
        }
        builder.doWrite(list);
    }

    /**
     * Single table multiple data template export template format is {.property}
     *
     * @param filename     file name
     * @param templatePath template path The path under the resource directory includes the template file name
     *                     For example: excel/temp.xlsx
     *                     Important: The template file must be placed in the resource directory corresponding to the startup class
     * @param data         Data required by the template
     * @param response     response body
     */
    public static void exportTemplate(List<Object> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            resetResponse(filename, response);
            ServletOutputStream os = response.getOutputStream();
            exportTemplate(data, templatePath, os);
        } catch (IOException e) {
            throw new RuntimeException("Export excel exception");
        }
    }

    /**
     * Single table multiple data template export template format is {.property}
     *
     * @param templatePath template path The path under the resource directory includes the template file name
     *                     For example: excel/temp.xlsx
     *                     Important: The template file must be placed in the resource directory corresponding to the startup class
     * @param data         Data required by the template
     * @param os           output stream
     */
    public static void exportTemplate(List<Object> data, String templatePath, OutputStream os) {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        ExcelWriter excelWriter = EasyExcel.write(os)
            .withTemplate(templateResource.getStream())
            .autoCloseStream(false)
            // Automatic conversion of large values ​​to prevent distortion
            .registerConverter(new ExcelBigNumberConvert())
            .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isEmpty(data)) {
            throw new IllegalArgumentException("data is empty");
        }
        // Single table multiple data export template format is {.property}
        for (Object d : data) {
            excelWriter.fill(d, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * Multi-table multi-data template export template format is {key.property}
     *
     * @param filename     file name
     * @param templatePath template path The path under the resource directory includes the template file name
     *                     For example: excel/temp.xlsx
     *                     Important: The template file must be placed in the resource directory corresponding to the startup class
     * @param data         Data required by the template
     * @param response     response body
     */
    public static void exportTemplateMultiList(Map<String, Object> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            resetResponse(filename, response);
            ServletOutputStream os = response.getOutputStream();
            exportTemplateMultiList(data, templatePath, os);
        } catch (IOException e) {
            throw new RuntimeException("Export excel exception");
        }
    }

    /**
     * Multi-table multi-data template export template format is {key.property}
     *
     * @param templatePath template path The path under the resource directory includes the template file name
     *                     For example: excel/temp.xlsx
     *                     Important: The template file must be placed in the resource directory corresponding to the startup class
     * @param data         Data required by the template
     * @param os           output stream
     */
    public static void exportTemplateMultiList(Map<String, Object> data, String templatePath, OutputStream os) {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        ExcelWriter excelWriter = EasyExcel.write(os)
            .withTemplate(templateResource.getStream())
            .autoCloseStream(false)
            // Automatic conversion of large values ​​to prevent distortion
            .registerConverter(new ExcelBigNumberConvert())
            .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isEmpty(data)) {
            throw new IllegalArgumentException("Data is empty");
        }
        for (Map.Entry<String, Object> map : data.entrySet()) {
            // There is data after the setting list
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            if (map.getValue() instanceof Collection) {
                // Multi-table export must use FillWrapper
                excelWriter.fill(new FillWrapper(map.getKey(), (Collection<?>) map.getValue()), fillConfig, writeSheet);
            } else {
                excelWriter.fill(map.getValue(), writeSheet);
            }
        }
        excelWriter.finish();
    }

    /**
     * reset response body
     */
    private static void resetResponse(String sheetName, HttpServletResponse response) {
        String filename = encodingFilename(sheetName);
        FileUtils.setAttachmentResponseHeader(response, filename);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
    }

    /**
     * Parsing derived values ​​0=male, 1=female, 2=unknown
     *
     * @param propertyValue parameter value
     * @param converterExp  translation notes
     * @param separator     delimiter
     * @return parsed value
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(StringUtils.SEPARATOR);
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * Reverse analysis value male=0, female=1, unknown=2
     *
     * @param propertyValue parameter value
     * @param converterExp  translation notes
     * @param separator     delimiter
     * @return parsed value
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(StringUtils.SEPARATOR);
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * encoded filename
     */
    public static String encodingFilename(String filename) {
        return IdUtil.fastSimpleUUID() + "_" + filename + ".xlsx";
    }

}
