package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @desc 学习资料实体类
 * @author Kaki Nakajima
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_exam_article")
public class Article implements Serializable {

    private static final long serialVersionUID = -93158822984244049L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name = "columnid")
    private String columnId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "content")
    private String content;

    @Column(name = "createtime")
    private Date createtime;

    @Column(name = "updatetime")
    private Date updatetime;

    @Column(name = "ispublic")
    private String ispublic;

    @Column(name = "istop")
    private String istop;

    @Column(name = "visits")
    private int visits;

    @Column(name = "thumbup")
    private int thumbup;

    @Column(name = "comment")
    private int comment;

    @Column(name = "state")
    private String state;

    @Column(name = "channelid")
    private String channelid;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;

}
