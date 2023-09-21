package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String test;
    
    @JsonIgnore
    @OneToMany(mappedBy = "member") //mirror 채널 (주인에 의해서 값을 그저 불러오는 형태)
    private List<Order> orders = new ArrayList<>();

}
