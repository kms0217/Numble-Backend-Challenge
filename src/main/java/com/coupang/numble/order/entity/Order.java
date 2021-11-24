package com.coupang.numble.order.entity;

import com.coupang.numble.common.entity.Base;
import com.coupang.numble.user.entity.MemberAddress;
import com.coupang.numble.user.entity.MemberAddress.Place;
import com.coupang.numble.user.entity.User;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_table")
public class Order extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private MemberAddress address;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Place place;

    @Formula("(select sum(od.price) from order_details od where od.order_id = id)")
    private int totalPrice;

    public void addDetails(OrderDetails details) {
        this.orderDetails.add(details);
    }
}
