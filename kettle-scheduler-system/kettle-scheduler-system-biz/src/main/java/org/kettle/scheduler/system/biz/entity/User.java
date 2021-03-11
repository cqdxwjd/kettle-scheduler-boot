package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 *
 * @author lyf
 */
@Entity
@Data
@Table(name = "K_USER")
@EqualsAndHashCode(callSuper = true)
public class User extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEQ_USER", strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 用户昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 用于电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 用户账号
     */
    @Column(name = "account")
    private String account;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;


}