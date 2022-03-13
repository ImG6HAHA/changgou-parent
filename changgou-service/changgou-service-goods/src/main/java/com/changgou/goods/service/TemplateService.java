package com.changgou.goods.service;

import com.changgou.goods.pojo.Template;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-14:45
 */
public interface TemplateService {

    Template findById(Integer id);



    void add(Template template);

    Template update(Template template);

    void delete(Integer id);


}
