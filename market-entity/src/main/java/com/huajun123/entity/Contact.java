package com.huajun123.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="contact")
public class Contact {
    @Id
    private Long guid;
    private String wechat;
    private String importtime;
    private String updatetime;
    private String updator;
    private String email;
    private String phone;
    private String name;
    private String location;
    private String birthday;
    private String source;
    private String creator;
}
