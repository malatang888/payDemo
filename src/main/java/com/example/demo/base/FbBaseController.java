/*
package com.example.demo.base;

import com.fbdata.fbstock.common.constants.PageConstant;
import com.fbdata.fbstock.common.model.Page;
import com.fbdata.fbstock.common.model.RestResponse;
import com.fbdata.fbstock.core.mylog4j.Logger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.orderbyhelper.OrderByHelper;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

*/
/**
 * @Description controller基类，其中map值实体属性键值对，filter指过滤条件键值对
 * @Author mh
 * @Date 2019/2/22
 *//*

@CacheConfig(cacheNames = "database.fbstock")
public class FbBaseController<E> {
    @Logger
    private static Log log ;

    @Autowired
    private FbBaseService<E> fbService;

    */
/**下划线转驼峰*//*

    public  String lineToHump(String str) {
        str = str.toLowerCase();
        Pattern linePattern = Pattern.compile("_(\\w)");
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    */
/**
     * @Description 获取数据库表的字段名和注释名，为了适配iviem，将结果定义成含title和key的map，同时为了赋值方便，将key转成驼峰
     * @Params
     * @Return
     * @Author mh
     * @Since 2019/3/29
     *//*

    @GetMapping("/getcol")
    public RestResponse getColumn() {
        List<Map> newList = new ArrayList<>();
        List<Map> retList = fbService.getColumn();
        for(int i=0;i<retList.size();i++){
            Map<String,String> m = retList.get(i);
            String oldval = m.get("key");
            String newval = lineToHump(oldval);
            m.put("key",newval);
            newList.add(m);
        }
        return new RestResponse(newList);
    }


    @Cacheable
    @GetMapping("/{id}")
    public RestResponse getById(@PathVariable String id) {
        if(log.isDebugEnabled()){
            log.debug(MessageFormat.format(
                    "/{id} - get - getById - (id = {0})", id));
        }
        E retList = fbService.getEntityById(id);
        return new RestResponse(retList);
    }


    */
/**
     * @Description get方式获取实体列表
     * @Params seccd,dt get请求方式中参数命名均为小写 (如果什么参数都不加，将会返回全部数据)
     * @Return RestResponse(retList);
     * @Author mh
     * @Since 2019/2/25
     *//*

    @Cacheable
    @GetMapping("/get")
    public RestResponse getResult(@RequestParam(required = false) String secCd, @RequestParam(required = false) String reportDt) {
        if(log.isDebugEnabled()){
            log.debug(MessageFormat.format(
                    "/get - get - getResult - (seccd = {0},dt={1})",
                    secCd, reportDt));
        }
        Map<String,String> filter = new HashMap<String,String>();
        filter.put("secCd",secCd);
        filter.put("reportDt",reportDt);
        List<E> retList = fbService.getEntityByMap(filter);
        return new RestResponse(retList);
    }

    */
/**
     * @Description 通过实体中的部分属性获取指定的实体
     * Post中的参数需写成驼峰命名方式
     * 只需要在请求的body里传json格式的属性键值对就可以自动转成实体
     * 其中的Date类型需要填成完整的时间字符串，例：
     * {
     * 	"secCd":"600309",
     * 	"reportDt":"2018-09-30 00:00:00"
     * }
     * @Author mh
     * @Since 2019/2/22
     *//*

    @Cacheable
    @PostMapping("/get")
    public RestResponse getByEntity(@RequestBody @Valid E entity) {
        if(log.isDebugEnabled()){
            log.debug("/get - Post - getByEntity");
        }
        List<E> retList = fbService.getEntityByEntity(entity);
        return new RestResponse(retList);
    }

    */
/**
     * @Description 返回字段名为中文的结果，通过list<Map>方式返回
     * @Params
     * @Return
     * @Author mh
     * @Since 2019/3/29
     *//*

    @Cacheable
    @PostMapping("/getwithcol")
    public RestResponse getWithColumn(@RequestBody @Valid E entity) {
        if(log.isDebugEnabled()){
            log.debug("/get - Post - getByEntity");
        }
        List<Map> retList = new ArrayList<>();
        retList = fbService.getWithColumn(entity);
        return new RestResponse(retList);
    }

    */
/**
     * 通过条件进行分页排序查询
     * @param pageNum -RequestParam
     * @param pageSize -RequestParam
     * @param sortField -RequestParam 字段名称，由于这里需要写数据库实际的字段名 如report_dt
     * @param sortOrder -RequestParam 排序方式，1为升序，其他为降序
     * @param map --实体类属性的map -RequestBody
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     *//*

    @Cacheable
    @PostMapping("/list")
    public RestResponse list(
            @RequestParam(required=false,defaultValue=PageConstant.DEFAULT_PAGE_NUM_STR) Integer pageNum,
            @RequestParam(required=false,defaultValue=PageConstant.DEFAULT_PAGE_SIZE_STR) Integer pageSize,
            @RequestParam(required=false) String sortField,
            @RequestParam(required=false) Integer sortOrder,
            @RequestBody(required=false) HashMap<String, Object> map){

        if(log.isDebugEnabled()){
            log.debug(MessageFormat.format(
                    "/list - post - list - (pageNum = {0},pageSize={1},sortField={2},sortOrder={3},filter={4})",
                    pageNum, pageSize, sortField, sortOrder, map));
        }
        PageHelper.startPage(pageNum, pageSize);

        if(sortField != null && sortOrder != null ){
            OrderByHelper.orderBy(sortField +" " + (sortOrder == 1 ? "asc" : "desc") );
        }

        List<E> datas = fbService.getEntityByMap(map);
        PageInfo<E> pageInfo = new PageInfo(datas);
        Page page = new Page();
        page.setParams(pageSize, pageNum, pageInfo.getTotal(), datas);
        return new RestResponse(page);
    }

    */
/**
     * @MethodName getByFilter
     * @Description 通过过滤条件查询 (如果什么参数都不加，将会返回全部数据)
     * @Params seccd,year,startdt,enddt
     * @Return RestResponse
     * @Author mh
     * @Since 2019/2/25
     *//*

    @Cacheable
    @GetMapping("/getbyfilter")
    public RestResponse getByFilter(@RequestParam(required = false) String secCd,
                                    @RequestParam(required = false) String year,
                                    @RequestParam(required = false) String startDt,
                                    @RequestParam(required = false) String endDt) {
        if(log.isDebugEnabled()){
            log.debug(MessageFormat.format(
                    "/get - get - getResult - (seccd = {0},year={1},start_dt={2},end_dt={3})",
                    secCd, year,startDt,endDt));
        }
        Map<String,String> filter = new HashMap<String,String>();
        filter.put("secCd",secCd);
        filter.put("year",year);
        filter.put("startDt",startDt);
        filter.put("endDt",endDt);
        List<E> retList = fbService.getEntityByFilter(filter);
        return new RestResponse(retList);
    }

    */
/**
     * 通过条件进行分页排序查询  (page相关是RequestParam，filter是RequestBody)
     * @param pageNum
     * @param pageSize
     * @param filter 过滤条件的map，含secCd,year,startDt,endDt(post方法的body参数需要使用驼峰命名方式)
     * @return RestResponse（page）
     * @throws InstantiationException
     * @throws IllegalAccessException
     *//*

    @Cacheable
    @PostMapping("/listbyfilter")
    public RestResponse listByFilter(
            @RequestParam(required=false,defaultValue=PageConstant.DEFAULT_PAGE_NUM_STR) Integer pageNum,
            @RequestParam(required=false,defaultValue=PageConstant.DEFAULT_PAGE_SIZE_STR) Integer pageSize,
            @RequestParam(required=false) String sortField,
            @RequestParam(required=false) Integer sortOrder,
            @RequestBody(required=false) HashMap<String, Object> filter){

        if(log.isDebugEnabled()){
            log.debug(MessageFormat.format(
                    "/list - post - list - (pageNum = {0},pageSize={1},sortField={2},sortOrder={3},filter={4})",
                    pageNum, pageSize, sortField, sortOrder, filter));
        }
        PageHelper.startPage(pageNum, pageSize);

        if(sortField != null && sortOrder != null ){
            OrderByHelper.orderBy(sortField +" " + (sortOrder == 1 ? "asc" : "desc") );
        }

        List<E> datas = fbService.getEntityByFilter(filter);
        PageInfo<E> pageInfo = new PageInfo(datas);
        Page page = new Page();
        page.setParams(pageSize, pageNum, pageInfo.getTotal(), datas);
        return new RestResponse(page);
    }

}
*/
