package com.bear.crawler.gov.service;

import cn.hutool.core.collection.CollectionUtil;
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

    private static final String[] FILTER_ITEM = new String[]{
            "教师,教师资格",
            "公证员",
            "基层法律",
            "执业许可",
            "户口",
            "身份证",
    };

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
        for (String filter : FILTER_ITEM) {
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
