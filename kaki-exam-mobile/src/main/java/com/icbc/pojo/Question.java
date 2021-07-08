package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 题目实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_exam_question")
public class Question implements Serializable {

    private static final long serialVersionUID = -831588229842049L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="content")
    private String content;

    @Column(name="type")
    private String type;

    @Column(name="answer")
    private String answer;

    @Column(name = "reason")
    private String reason;

    @Column(name="score")
    private Double score;
    //选项，map字符串形式
    @Column(name = "option_map_string")
    private String optionMapString;

    /** 与前端交互的map*/
    @Transient
    private HashMap<String,Object> optionMap;

}
