package com.coupang.numble.user.entity;

import com.coupang.numble.common.entity.Base;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MemberAddress extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String telephone;

    private String homePhone;

    @Column(nullable = false)
    private String address;

    private boolean main;

    @Enumerated(EnumType.STRING)
    private Place place;

    public enum Place {
        직접받고부재시문앞,
        문앞,
        경비실
    }
}
