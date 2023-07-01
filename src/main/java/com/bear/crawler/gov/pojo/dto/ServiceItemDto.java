package com.bear.crawler.gov.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ServiceItemDto {

    @JsonAlias("serviceType")
    private Object serviceType; // null,

    @JsonAlias("unid")
    private String unid; // "D395CD074491675AEDB296AF9FD302EE",

    @JsonAlias("addtype")
    private String addtype; // "行政许可",

    @JsonAlias("linkPre")
    private Object linkPre; // null,

    @JsonAlias("webapplyurl")
    private Object webapplyurl; // null,

    @JsonAlias("srccode")
    private Object srccode; // null,

    @JsonAlias("subWebapplyurl")
    private Object subWebapplyurl; // null,

    @JsonAlias("infoprojid")
    private String infoprojid; // "003916081XK01302",

    @JsonAlias("checkMaterial")
    private String checkMaterial; // "S",

    @JsonAlias("deptAlias")
    private String deptAlias; // "东山县教育局",

    @JsonAlias("isLiandong")
    private Object isLiandong; // null,

    @JsonAlias("promiseday")
    private String promiseday; // "14",

    @JsonAlias("promisetype")
    private String promisetype; // "1",

    @JsonAlias("singleloginif")
    private Object singleloginif; // null,

    @JsonAlias("servicename")
    private String servicename; // "设立学前教育阶段民办学校的审批",

    @JsonAlias("contactphone")
    private Object contactphone; // null,

    @JsonAlias("caCheck")
    private boolean caCheck; // false,

    @JsonAlias("ydjFlag")
    private String ydjFlag; // "N",

    @JsonAlias("parentunid")
    private String parentunid; // "7366B95A5B645BA51A2BD73A1BAA8F9E",

    @JsonAlias("wsnum")
    private String wsnum; // "0",

    @JsonAlias("starproperty")
    private int starproperty; // 5,

    @JsonAlias("inproperty")
    private String inproperty; // "in04",

    @JsonAlias("deptname")
    private String deptname; // "东山县教育局",

    @JsonAlias("areaCode")
    private Object areaCode; // null,

    @JsonAlias("checkSelf")
    private Object checkSelf; // null,

    @JsonAlias("serviceflag")
    private Object serviceflag; // null,

    @JsonAlias("isgf")
    private Object isgf; // null,

    @JsonAlias("name")
    private Object name; // null,

    @JsonAlias("deptunid")
    private String deptunid; // "E4A6D93E4484D3CC68CF6ED86CFFA443",

    @JsonAlias("cknum")
    private String cknum; // "1",

    @JsonAlias("deptCode")
    private Object deptCode; // null
}
