package com.bear.crawler.gov.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bear.crawler.gov.GovConstant;
import com.bear.crawler.gov.pojo.dto.CategoryDto;
import com.bear.crawler.gov.pojo.dto.ServiceDirDto;
import com.bear.crawler.gov.pojo.dto.ServiceItemDto;
import com.bear.crawler.gov.util.GovOtherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ServiceInfoFileManager {

    private static final String INDENT = "    ";
    private static final String DIVIDER = "*=============================================================================*\n";

    @Value("${gov.fetchServiceInfoDir}")
    private String fetchServiceInfoDir;

    public void saveServiceInfo(Map<CategoryDto, List<ServiceDirDto>> infoMap) {
        File tempFile = FileUtil.file(fetchServiceInfoDir, "个人办事服务内容_Temp.md");
        for (Map.Entry<CategoryDto, List<ServiceDirDto>> entry : infoMap.entrySet()) {
            StringBuilder builder = new StringBuilder();
            CategoryDto categoryDto = entry.getKey();
            List<ServiceDirDto> serviceDirDtos = entry.getValue();
            builder.append(categoryDto.getName()).append("\n");
            for (ServiceDirDto serviceDirDto : serviceDirDtos) {
                collectServiceDirDto(serviceDirDto, builder, "");
            }
            builder.append(DIVIDER);
            FileUtil.appendString(builder.toString(), tempFile, "utf-8");
        }
        FileUtil.rename(tempFile, "个人办事服务内容.md", true);
    }

    public void saveServiceDirListJsonStr(Map<CategoryDto, List<ServiceDirDto>> infoMap) {
        Collection<List<ServiceDirDto>> values = infoMap.values();
        File jsonFile = FileUtil.file(fetchServiceInfoDir, "个人办事服务内容Json.md");
        String json = JSONUtil.parse(values).toStringPretty();
        FileUtil.writeUtf8String(json, jsonFile);
    }

    public List<List<ServiceDirDto>> readServiceDirListJsonStr() {
        File jsonFile = FileUtil.file(fetchServiceInfoDir, "个人办事服务内容Json.md");
        String json = FileUtil.readUtf8String(jsonFile);
        return JSONUtil.parse(json).toBean(new TypeReference<>() {});
    }

    private void collectServiceDirDto(ServiceDirDto serviceDirDto, StringBuilder builder, String indent) {
        String dirName = serviceDirDto.getDirName();
        if (StrUtil.isNotEmpty(dirName)) {
            builder.append(indent).append(dirName).append("\n");
            indent = indent + INDENT;
        }
        List<ServiceDirDto> serviceDirDtos = serviceDirDto.getRspDirectoryApasService();
        if (CollectionUtil.isEmpty(serviceDirDtos)) {
            List<ServiceItemDto> serviceItemDtos = serviceDirDto.getApasServiceSimpleList();
            if (CollectionUtil.isNotEmpty(serviceItemDtos)) {
                for (ServiceItemDto serviceItemDto : serviceItemDtos) {
                    String hrefStr = createHrefStr(serviceItemDto.getServicename(), serviceItemDto.getUnid());
                    builder.append(indent).append(hrefStr).append("\n");
                }
            }
        } else {
            for (ServiceDirDto dirDto : serviceDirDtos) {
                collectServiceDirDto(dirDto, builder, indent);
            }
        }
    }

    private String createHrefStr(String content, String unid) {
        String url = GovOtherUtil.getNewUrlByParams(GovConstant.ITEM_DETAIL_URL, MapUtil.of(GovConstant.UNID, unid));
        return "<a href=\"" + url + "\">" + content + "</a>";
    }

    public void saveFilterServiceItemInfo(Map<String, List<ServiceItemDto>> infoMap) {
        File tempFile = FileUtil.file(fetchServiceInfoDir, "个人办事服务内容Rule_Temp.md");
        for (Map.Entry<String, List<ServiceItemDto>> entry : infoMap.entrySet()) {
            StringBuilder builder = new StringBuilder();
            builder.append(entry.getKey()).append("\n");
            List<ServiceItemDto> serviceItemDtos = entry.getValue();
            for (ServiceItemDto serviceItemDto : serviceItemDtos) {
                String hrefStr = createHrefStr(serviceItemDto.getServicename(), serviceItemDto.getUnid());
                builder.append(hrefStr).append("\n");
            }
            builder.append(DIVIDER);
            FileUtil.appendString(builder.toString(), tempFile, "utf-8");
        }
        FileUtil.rename(tempFile, "个人办事服务内容Rule.md", true);
    }
}
