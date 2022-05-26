package com.cjy.crown.issue.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/24 1:51
 * @description：
 */
@Entity
@Data
@Document(indexName = "issue")
public class ESIssue {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Field(type = FieldType.Keyword)
    private String id;
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String title;
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String markdown;
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String mainText;
    @Field(type = FieldType.Keyword)
    private String holder;
    @Field(type = FieldType.Keyword)
    private String rootId;
    @Field(type = FieldType.Keyword)
    private String fatherId;
    @Field(type = FieldType.Integer)
    private Integer tier;
    @Field(type = FieldType.Keyword)
    private String creater;
    @Field(type = FieldType.Keyword)
    private String updater;
    @Field(type = FieldType.Date)
    private Date createTime;
    @Field(type = FieldType.Date)
    private Date updateTime;
    @Field(type = FieldType.Integer)
    private Integer State;
}
