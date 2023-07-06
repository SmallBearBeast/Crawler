package com.bear.crawler.gov.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.gov.GovConstant;
import com.bear.crawler.gov.manager.ServiceInfoFileManager;
import com.bear.crawler.gov.pojo.dto.CategoryDto;
import com.bear.crawler.gov.pojo.dto.ServiceDirDto;
import com.bear.crawler.gov.pojo.dto.ServiceItemDto;
import com.bear.crawler.gov.pojo.dto.resp.CategoryRespDto;
import com.bear.crawler.gov.pojo.dto.resp.ServiceInfoRespDto;
import com.bear.crawler.gov.util.GovOtherUtil;
import com.bear.crawler.basic.http.OkHttp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GovService {

    @Autowired
    private OkHttp okHttp;

    @Autowired
    private ServiceInfoFileManager serviceInfoFileManager;

    public void syncPersonalAffairsByCategory() {
        List<CategoryDto> categoryDtos = fetchCategory();
        Map<CategoryDto, List<ServiceDirDto>> map = new LinkedHashMap<>();
        for (CategoryDto categoryDto : categoryDtos) {
            List<ServiceDirDto> serviceDirDtos = new ArrayList<>();
            syncTopicByCategory(categoryDto, serviceDirDtos);
            map.put(categoryDto, serviceDirDtos);
        }
        serviceInfoFileManager.saveServiceInfo(map);
        serviceInfoFileManager.saveServiceDirListJsonStr(map);
    }

    private List<CategoryDto> fetchCategory() {
        CategoryRespDto respDto = okHttp.get(GovConstant.ALL_CATEGORY_URL, null, null, CategoryRespDto.class);
        if (GovOtherUtil.checkGovRespDto(respDto, "GovService.syncPersonalAffairsByCategory()")) {
            return respDto.getData();
        }
        return new ArrayList<>();
    }

    private void syncTopicByCategory(CategoryDto categoryDto, List<ServiceDirDto> serviceDirDtos) {
        String value = categoryDto.getValue();
        String url = GovOtherUtil.getNewUrlByParams(GovConstant.ITEM_BY_CATEGORY_URL, MapUtil.of(GovConstant.SMALL_TYPE, value));
        String categoryName = categoryDto.getName();
        syncTopicByCategoryInternal(categoryName, url, 1, serviceDirDtos);
    }

    private void syncTopicByCategoryInternal(String categoryName, String url, int pageNum, List<ServiceDirDto> serviceDirDtos) {
        String newUrl = GovOtherUtil.getNewUrlByParams(url, MapUtil.of(GovConstant.PAGE_NUM, pageNum));
        ServiceInfoRespDto respDto = okHttp.get(newUrl, null, null, ServiceInfoRespDto.class);
        if (GovOtherUtil.checkGovRespDto(respDto, "GovService.syncPersonalAffairsByCategory()")) {
            List<ServiceDirDto> dirDtos = respDto.getData();
            if (CollectionUtil.isNotEmpty(dirDtos)) {
                serviceDirDtos.addAll(dirDtos);
                syncTopicByCategoryInternal(categoryName, url, pageNum + 1, serviceDirDtos);
            } else {
                log.info("syncTopicByCategoryInternal: load the {} service info to end", categoryName);
            }
        }
    }

    public void syncPersonalAffairsByDepartment() {
        syncDepartment();
        syncTopicByDepartment();
    }

    private void syncDepartment() {

    }

    private void syncTopicByDepartment() {

    }

    // Key是rule name，value是json文件提取的关键字，以,作为分隔符。
    private static final Map<String, String> FILTER_ITEM_MAP = new LinkedHashMap<>();
    {
        FILTER_ITEM_MAP.put("身份证", "身份证");

        FILTER_ITEM_MAP.put("户口", "户口");
        FILTER_ITEM_MAP.put("户口迁移", "户口迁移");
        FILTER_ITEM_MAP.put("居住证", "居住证");
        FILTER_ITEM_MAP.put("出生登记", "出生登记");
        FILTER_ITEM_MAP.put("户口登记", "户口登记");
        FILTER_ITEM_MAP.put("户口注销", "户口注销");
        FILTER_ITEM_MAP.put("姓名变更,改名字,改姓名,改名,姓名", "姓名变更");

        FILTER_ITEM_MAP.put("社会保险,社保", "社会保险");
        FILTER_ITEM_MAP.put("参保记录,参保查询,社保记录,社保查询", "参保缴费记录查询");
        FILTER_ITEM_MAP.put("教师,教师资格", "教师,教师资格");
        FILTER_ITEM_MAP.put("执业许可", "执业许可");
        FILTER_ITEM_MAP.put("公证员", "公证员");
        FILTER_ITEM_MAP.put("基层法律", "基层法律");
        FILTER_ITEM_MAP.put("海域使用", "海域使用");
        FILTER_ITEM_MAP.put("水域滩涂", "水域滩涂");
        FILTER_ITEM_MAP.put("养殖,养殖证", "养殖,养殖证");
        FILTER_ITEM_MAP.put("船员,船舶船员", "船员,船舶船员");
        FILTER_ITEM_MAP.put("水生,水生野生动物", "水生,水生野生动物");
        FILTER_ITEM_MAP.put("水产,苗种,水产苗种", "水产,苗种,水产苗种");
        FILTER_ITEM_MAP.put("捕捞,渔业捕捞", "捕捞,渔业捕捞");
        FILTER_ITEM_MAP.put("船舶国籍,船舶国籍登记", "船舶国籍,船舶国籍登记");
        FILTER_ITEM_MAP.put("特种设备", "特种设备");
        FILTER_ITEM_MAP.put("华侨", "华侨");
        FILTER_ITEM_MAP.put("港澳台", "港澳台");
        FILTER_ITEM_MAP.put("定居", "定居");
        FILTER_ITEM_MAP.put("回国定居", "回国定居");
        FILTER_ITEM_MAP.put("林草种子", "林草种子");
        FILTER_ITEM_MAP.put("林草植物", "林草植物");
        FILTER_ITEM_MAP.put("陆生,陆生野生动物", "陆生,陆生野生动物");
        FILTER_ITEM_MAP.put("林木采伐", "林木采伐");
        FILTER_ITEM_MAP.put("残疾儿童,康复救助", "残疾儿童,康复救助");
        FILTER_ITEM_MAP.put("光荣牌", "光荣牌");
        FILTER_ITEM_MAP.put("技工学校", "技工学校");
        FILTER_ITEM_MAP.put("培训机构", "培训机构");
    }

    public void filterPersonalAffairs() {
        List<List<ServiceDirDto>> list = serviceInfoFileManager.readServiceDirListJsonStr();
        List<ServiceDirDto> allServiceDirDtos = new ArrayList<>();
        for (List<ServiceDirDto> value : list) {
            allServiceDirDtos.addAll(value);
        }
        Map<String, List<ServiceItemDto>> filterServiceItemMap = filterServiceDirInfo(allServiceDirDtos);
        serviceInfoFileManager.saveFilterServiceItemInfo(filterServiceItemMap);
    }

    private Map<String, List<ServiceItemDto>> filterServiceDirInfo(List<ServiceDirDto> serviceDirDtos) {
        Map<String, List<ServiceItemDto>> resultMap = new LinkedHashMap<>();
        for (String filter : FILTER_ITEM_MAP.values()) {
            Map<String, ServiceItemDto> serviceItemDtoMap = new LinkedHashMap<>();
            String[] splits = filter.split(",");
            if (splits.length > 0) {
                for (String split : splits) {
                    filterAndCollect(split, serviceDirDtos, serviceItemDtoMap);
                }
            } else {
                filterAndCollect(filter, serviceDirDtos, serviceItemDtoMap);
            }
            resultMap.put(filter, new ArrayList<>(serviceItemDtoMap.values()));
        }
        return resultMap;
    }

    private void filterAndCollect(String filter, List<ServiceDirDto> serviceDirDtos, Map<String, ServiceItemDto> serviceItemDtoMap) {
        for (ServiceDirDto serviceDirDto : serviceDirDtos) {
            filterAndCollect(filter, serviceDirDto, serviceItemDtoMap);
        }
    }

    private void filterAndCollect(String filter, ServiceDirDto filterServiceDirDto, Map<String, ServiceItemDto> collectServiceItemDtoMap) {
        String dirName = filterServiceDirDto.getDirName();
        if (dirName != null && dirName.contains(filter)) {
            collectServiceItemDto(filterServiceDirDto, collectServiceItemDtoMap);
            return;
        }
        List<ServiceItemDto> serviceItemDtos = filterServiceDirDto.getApasServiceSimpleList();
        if (CollectionUtil.isNotEmpty(serviceItemDtos)) {
            for (ServiceItemDto serviceItemDto : serviceItemDtos) {
                String itemName = serviceItemDto.getServicename();
                if (itemName != null && itemName.contains(filter)) {
                    collectServiceItemDtoMap.put(serviceItemDto.getUnid(), serviceItemDto);
                    return;
                }
            }
        }
        List<ServiceDirDto> serviceDirDtos = filterServiceDirDto.getRspDirectoryApasService();
        if (CollectionUtil.isNotEmpty(serviceDirDtos)) {
            for (ServiceDirDto serviceDirDto : serviceDirDtos) {
                filterAndCollect(filter, serviceDirDto, collectServiceItemDtoMap);
            }
        }
    }

    private void collectServiceItemDto(ServiceDirDto collectServiceDirDto, Map<String, ServiceItemDto> collectServiceItemDtoMap) {
        List<ServiceItemDto> serviceItemDtos = collectServiceDirDto.getApasServiceSimpleList();
        if (CollectionUtil.isNotEmpty(serviceItemDtos)) {
            for (ServiceItemDto serviceItemDto : serviceItemDtos) {
                collectServiceItemDtoMap.put(serviceItemDto.getUnid(), serviceItemDto);
            }
        }
        List<ServiceDirDto> serviceDirDtos = collectServiceDirDto.getRspDirectoryApasService();
        if (CollectionUtil.isNotEmpty(serviceDirDtos)) {
            for (ServiceDirDto serviceDirDto : serviceDirDtos) {
                collectServiceItemDto(serviceDirDto, collectServiceItemDtoMap);
            }
        }
    }
}
