package com.bear.crawler.gov.pojo.dto.resp;

import com.bear.crawler.gov.pojo.dto.CategoryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryRespDto extends GovRespDto<List<CategoryDto>> {

}
