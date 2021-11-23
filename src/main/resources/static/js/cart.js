window.onload = calcPrice;
const rocketUser = $("#rocketUser").attr("value");
let rocketShippingPrice = 0;
let rocketPrice = 0;
let generalShippingPrice = 0;
let generalPrice= 0;

function changeCheckBox(prodType, cartId) {
  let price_el = $("#price-" + cartId);
  let count_el = $("#input-number-" + cartId);
  if (!$("#check-box-" + cartId).is(':checked')) {
    if (prodType === "rocket") {
      rocketPrice -= (price_el.attr("value") * count_el.val())
      if (!rocketUser && rocketPrice < 19800)
        rocketShippingPrice = 3000;
    } else {
      generalPrice -= (price_el.attr("value") * count_el.val())
      generalPrice -= 3000;
    }
  } else {
    if (prodType === "rocket") {
      rocketPrice += (price_el.attr("value") * count_el.val())
      if (rocketPrice >= 19800)
        rocketShippingPrice = 0;
    } else {
      generalPrice += (price_el.attr("value") * count_el.val())
      generalPrice += 3000;
    }
  }
  reRenderPrice(prodType);
}

function changeCount(prodType, cartId) {
  let price_el = $("#price-" + cartId);
  let curr_count = $("#input-number-" + cartId).val();
  let before_count = $("#input-number-" + cartId).attr("keep");
  $("#input-number-" + cartId).attr("keep", curr_count);
  if($("#check-box-" + cartId).is(':checked')){
    if (prodType === "rocket") {
      rocketPrice -= price_el.attr("value") * before_count
      rocketPrice += price_el.attr("value") * curr_count
      if (!rocketUser) {
        if (rocketPrice < 19800)
          rocketShippingPrice = 3000;
        else
          rocketShippingPrice = 0;
      }
    } else {
      generalPrice -= price_el.attr("value") * before_count
      generalPrice += price_el.attr("value") * curr_count
    }
    reRenderPrice(prodType);
  }
}

function reRenderPrice(prodType) {
  let el = $("#summary-" + prodType);
  let roP = rocketPrice + rocketShippingPrice;
  let geP = generalPrice + generalShippingPrice;
  let proP = rocketPrice + generalPrice;
  let shiP = rocketShippingPrice + generalShippingPrice;
  let total = roP + geP
  if (prodType === "rocket") {
    el.text(rocketShippingPrice + " + " + rocketPrice + " = " + roP + " 원")
  } else {
    el.text(generalShippingPrice + " + " + generalPrice + " = " + geP + " 원")
  }
  $("#summary-price").text("총 상품 가격 : " + proP + " 원")
  $("#summary-delivery-price").text("총 배송비 : " + shiP +  " 원")
  $("#summary-final-price").text("총 가격 : " + total + " 원")
}

function calcPrice() {
  $(".rocket-prod-cart .cart-item-box .cart-item-box").each(function (index, item){
    if ($(item).children(".prod-checkbox").is(':checked')) {
      let price = $(item).children(".prod-price").attr("value")
      let count = $(item).children(".prod-number").val()
      rocketPrice += price * count
    }
  })
  if (!rocketUser && rocketPrice < 19800)
    rocketShippingPrice = 3000

  $(".general-prod-cart .cart-item-box .cart-item-box").each(function (index, item){
    if ($(item).children(".prod-checkbox").is(':checked')) {
      let price = $(item).children(".prod-price").attr("value")
      let count = $(item).children(".prod-number").val()
      generalPrice += price * count
      generalShippingPrice += 3000
    }
  })
  reRenderPrice("rocket")
  reRenderPrice("general")
}

function deleteCart(cartId) {
  $.ajax({
    type: "delete",
    url: "/cart/" + cartId,
    success: data => {
      window.location.href = "/cart"
    },
    error: data => {
      alert("에러가 발생했습니다.")
    }
  })
}
