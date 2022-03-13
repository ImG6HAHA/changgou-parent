package com.changgou.search.dao;

import com.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-23:33
 */
@Repository
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
