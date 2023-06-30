package com.bear.crawler.gov.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class ServiceDirDto {

    @JsonAlias("dirGrade")
    private String dirGrade; // "1",

    @JsonAlias("dirParentUnid")
    private String dirParentUnid; // "B185BC2D31F0D3F5F92D90CF59E48B4B",

    @JsonAlias("directoryId")
    private String directoryId; // "8D01C2C6CDFF3053951DC14568262E1B",

    @JsonAlias("apasServiceSimpleList")
    private List<ServiceItemDto> apasServiceSimpleList;

    @JsonAlias("dirName")
    private String dirName; // "设立学前教育、义务教育阶段民办学校及其他文化教育民办学校的审批"
}
