package com.coupang.numble.order.controller;

import com.coupang.numble.order.dto.CartDetailDto;
import com.coupang.numble.order.dto.CartDto;
import com.coupang.numble.order.service.CartService;
import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public String cartForm(@AuthenticationPrincipal Principal principal, Model model) {
        List<CartDetailDto> cartDetails = service.getCartItems(principal.getUser().getId());
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("cartItems", cartDetails);
        return "cart";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity addCart(
        @RequestBody @Valid CartDto req,
        BindingResult bindingResult,
        @AuthenticationPrincipal Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("validation fail");
        }
        return ResponseEntity.ok().body(service.addCart(req, principal.getUser().getId()));
    }

    @DeleteMapping("/{cartId}")
    @ResponseBody
    public HttpStatus deleteCart(@PathVariable Long cartId, @AuthenticationPrincipal Principal principal) {
        service.deleteCart(principal.getUser().getId(), cartId);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/product/{productId}/selected")
    @ResponseBody
    public HttpStatus changeCartItemSelectedOption(
        @PathVariable Long productId,
        @AuthenticationPrincipal Principal principal
    ) {
        service.toggleCartItemSelected(productId, principal.getUser().getId());
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/product/{productId}/count")
    @ResponseBody
    public HttpStatus changeCartItemSelectedOption(
        @PathVariable Long productId,
        @RequestParam int count,
        @AuthenticationPrincipal Principal principal
    ) {
        if (count < 1)
            return HttpStatus.NO_CONTENT;
        service.changeCartItemCount(productId, count, principal.getUser().getId());
        return HttpStatus.NO_CONTENT;
    }

}
