package com.bear.crawler.gov.pojo.dto.resp;

import com.bear.crawler.gov.pojo.dto.ServiceDirDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceInfoRespDto extends GovRespDto<List<ServiceDirDto>> {

}
