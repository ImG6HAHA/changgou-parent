package com.changgou.content.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-14:52
 */
//@ApiModel 在实体类上边使用，标记类时swagger的解析类


@ApiModel(description = "Content",value = "Content")
@Table(name = "tb_content")
public class Content implements Serializable {
    @ApiModelProperty(value = "广告id",required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//广告id
    @ApiModelProperty(value = "分类名称",required = false)
    @Column(name = "category_id")
    private Long categoryId;//分类名称
    @ApiModelProperty(value = "标题",required = false)
    @Column(name = "title")
    private String title;//标题
    @ApiModelProperty(value = "URL",required = false)
    @Column(name = "url")
    private String url;//URL
    @ApiModelProperty(value = "PIC",required = false)
    @Column(name = "pic")
    private String pic;//PIC
    @ApiModelProperty(value = "状态",required = false)
    @Column(name = "status")
    private String status;//状态
    @ApiModelProperty(value = "排序",required = false)
    @Column(name = "sort_order")
    private Integer sortOrder;//排序


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                '}';
    }
}


