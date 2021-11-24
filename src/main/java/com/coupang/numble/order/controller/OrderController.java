package com.coupang.numble.order.controller;

import com.coupang.numble.order.dto.OrderReqDto;
import com.coupang.numble.order.service.CartService;
import com.coupang.numble.order.service.OrderService;
import com.coupang.numble.product.service.ProductService;
import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import com.coupang.numble.user.service.MemberAddressService;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberAddressService memberAddressService;
    private final CartService cartService;
    private final ProductService productService;

    public OrderController(OrderService orderService,
        MemberAddressService memberAddressService,
        CartService cartService, ProductService productService) {
        this.orderService = orderService;
        this.memberAddressService = memberAddressService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String mcOrderList(Model model, @AuthenticationPrincipal Principal principal) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("mcPage", "orderList");
        return "mycoupang/order/orderList";
    }

    @GetMapping("/direct/{productId}")
    public String orderPage(
        @AuthenticationPrincipal Principal principal,
        @RequestParam Map<String, String> item,
        @PathVariable Long productId,
        Model model
    ) {
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("address", memberAddressService.getMainAddress(principal.getUser()));
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("count", item.get("count"));
        model.addAttribute("rocketExcept",LocalDateTime.now().withHour(6).withMinute(0).withNano(0).withSecond(0));
        model.addAttribute("generalExcept", LocalDateTime.now().plusDays(3));
        return "order/orderDirect";
    }

    @GetMapping("/cart")
    public String cartOrderPage(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("address", memberAddressService.getMainAddress(principal.getUser()));
        model.addAttribute("rocketExcept",LocalDateTime.now().withHour(6).withMinute(0).withNano(0).withSecond(0));
        model.addAttribute("generalExcept", LocalDateTime.now().plusDays(3));
        model.addAttribute("products", cartService.getCheckedCartProduct(principal.getUser().getId()));
        return "order/orderCart";
    }

    @PostMapping
    @ResponseBody
    public HttpStatus createOrder(@AuthenticationPrincipal Principal principal, @RequestBody OrderReqDto req) {
        orderService.createOrderWithOneProduct(principal.getUser(), req, true);
        return HttpStatus.CREATED;
    }

    @PostMapping("/cart")
    @ResponseBody
    public HttpStatus createCartOrder(@AuthenticationPrincipal Principal principal, @RequestBody OrderReqDto req) {
        orderService.createOrderWithOneProduct(principal.getUser(), req, false);
        return HttpStatus.CREATED;
    }
}
