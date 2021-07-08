package com.icbc.pojo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author kaki
 */
@Table(name = "t_exam_user")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamUser implements Serializable {

    private static final long serialVersionUID = -66378626574886353L;

    @Id
    @Column(name="username")
    private String username;

    @Column(name="icbc_id")
    private String icbcId;

    @Column(name="nick_name")
    private String nickName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="dept_flag")
    private Integer deptFlag;

    @Column(name="avatar")
    private String avatar;

    @Column(name="create_time")
    private Date createTime;


    @Column(name="real_name")
    private String real_name;

    @Column(name="sex")
    private String sex;

    @Column(name="grade")
    private String grade;
}
