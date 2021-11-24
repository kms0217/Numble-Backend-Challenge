package com.coupang.numble.order.service;

import com.coupang.numble.order.dto.OrderReqDto;
import com.coupang.numble.order.entity.Cart;
import com.coupang.numble.order.entity.Order;
import com.coupang.numble.order.entity.OrderDetails;
import com.coupang.numble.order.repository.CartRepository;
import com.coupang.numble.order.repository.OrderDetailsRepository;
import com.coupang.numble.order.repository.OrderRepository;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.repository.ProductRepository;
import com.coupang.numble.user.entity.MemberAddress;
import com.coupang.numble.user.entity.User;
import com.coupang.numble.user.repository.MemberAddressRepository;
import com.coupang.numble.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberAddressRepository memberAddressRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
        OrderDetailsRepository orderDetailsRepository,
        CartRepository cartRepository,
        ProductRepository productRepository,
        MemberAddressRepository memberAddressRepository,
        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.memberAddressRepository = memberAddressRepository;
        this.userRepository = userRepository;
    }

    public void createOrderWithOneProduct(User user, OrderReqDto req, boolean oneItem) {
        System.out.println(user.getId());
        User u = userRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
        MemberAddress address = memberAddressRepository.findById(req.getAddressId()).orElseThrow(RuntimeException::new);
        Order order = new Order();
        order.setUser(u);
        order.setAddress(address);
        order.setPhoneNumber(req.getPhoneNumber());
        req.setPlace(address.getPlace());
        if (req.getPlace() != null)
            order.setPlace(req.getPlace());
        if (oneItem) {
            Product product = productRepository.findById(req.getProductId()).orElseThrow(RuntimeException::new);
            OrderDetails orderDetails = createOrderDetails(user.isRocketMembership(), product, req.getCount());
            order.addDetails(orderDetails);
            orderDetails.setOrder(order);
            orderRepository.save(order).getId();
        } else {
            List<Cart> carts = cartRepository.findByUserIdAndSelected(user.getId(), true);
            carts.forEach(cart -> {
                OrderDetails orderDetails = createOrderDetails(u.isRocketMembership(), cart.getProduct(), cart.getCount());
                order.addDetails(orderDetails);
                orderDetails.setOrder(order);
            });
            orderRepository.save(order).getId();
            cartRepository.deleteAll(carts);
        }
    }

    public OrderDetails createOrderDetails(boolean rocketMembership, Product product, int count) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setVisible(true);
        orderDetails.setCount(count);
        orderDetails.setProduct(product);
        orderDetails.setArriveDate(LocalDateTime.now().plusDays(3));
        int price = (int)(product.getPrice() * 0.95);
        int shippingPrice = 0;
        if (product.isGoldBox() && rocketMembership) {
            price = (int)(price * 0.9);
        }
        if (product.isRocketShipping()) {
            orderDetails.setArriveDate(LocalDateTime.now().withHour(6).withMinute(0).withNano(0).withSecond(0));
        }
        if ((product.isRocketShipping() && !rocketMembership && price < 19800) || !product.isRocketShipping()) {
            shippingPrice = 3000;
        }
        orderDetails.setPrice(price + shippingPrice);
        return orderDetails;
    }

}
