package com.bear.crawler.gov.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.gov.GovConstant;
import com.bear.crawler.gov.pojo.dto.CategoryDto;
import com.bear.crawler.gov.pojo.dto.ServiceDirDto;
import com.bear.crawler.gov.pojo.dto.resp.CategoryRespDto;
import com.bear.crawler.gov.pojo.dto.resp.ServiceDirRespDto;
import com.bear.crawler.gov.util.GovOtherUtil;
import com.bear.crawler.webmagic.basic.http.OkHttp;
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

    public void syncPersonalAffairsByCategory() {
        List<CategoryDto> categoryDtos = fetchCategory();
        Map<CategoryDto, List<ServiceDirDto>> map = new LinkedHashMap<>();
        for (CategoryDto categoryDto : categoryDtos) {
            List<ServiceDirDto> serviceDirDtos = new ArrayList<>();
            syncTopicByCategory(categoryDto,  serviceDirDtos);
            map.put(categoryDto, serviceDirDtos);
        }
        print(map);
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
        ServiceDirRespDto respDto = okHttp.get(newUrl, null, null, ServiceDirRespDto.class);
        if (GovOtherUtil.checkGovRespDto(respDto, "GovService.syncPersonalAffairsByCategory()")) {
            List<ServiceDirDto> dirDtos = respDto.getData();
            if (CollectionUtil.isNotEmpty(dirDtos)) {
                serviceDirDtos.addAll(dirDtos);
                syncTopicByCategoryInternal(categoryName, url, pageNum + 1, serviceDirDtos);
            } else {
                log.info("syncTopicByCategoryInternal: load the {} to end", categoryName);
            }
        }
    }

    private void print(Map<CategoryDto, List<ServiceDirDto>> map) {

    }

    public void syncPersonalAffairsByDepartment() {
        syncDepartment();
        syncTopicByDepartment();
    }

    private void syncDepartment() {

    }

    private void syncTopicByDepartment() {

    }
}
