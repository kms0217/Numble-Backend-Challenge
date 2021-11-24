window.onload = calcPrice;
const rocketUser = JSON.parse($("#rocketUser").attr("value"));
let rocketShippingPrice = 0;
let rocketPrice = 0;
let generalShippingPrice = 0;
let generalPrice= 0;

function orderReq(count, productId) {
  let data = {
    "productId" : productId,
    "addressId" : $(".address").attr("addressId"),
    "count" : count,
    "phoneNumber" : $(".buyer-phone-number").val()
  }

  $.ajax({
    type: "post",
    url: "/order",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: data => {
      alert("주문 성공")
      window.location.href = "/"
    },
    error: data => {
      alert("주문 실패")
    }
  })
}


function cartOrder() {
  let data = {
    "addressId" : $(".address").attr("addressId"),
    "phoneNumber" : $(".buyer-phone-number").val()
  }

  $.ajax({
    type: "post",
    url: "/order/cart",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: data => {
      alert("주문 성공")
      window.location.href = "/"
    },
    error: data => {
      alert("주문 실패")
    }
  })
}

function calcPrice() {
  $(".rocket-prod-order .order-item-box").each(function (index, item){
    let price = Number($(item).attr("price"))
    let goldBox = JSON.parse($(item).attr("goldBox"))
    let count = Number($(item).attr("count"))
    $(item).children(".prod-before-sale-price").text(price * count + " 원")
    if (goldBox && rocketUser) {
      $(item).children(".prod-after-sale-price").text(price * count * 0.95 * 0.9 + " 원")
      rocketPrice += price * count * 0.95 * 0.9
    } else {
      $(item).children(".prod-after-sale-price").text(price * count * 0.95 + " 원")
      rocketPrice += price * count * 0.95
    }
  })
  if (!rocketUser && rocketPrice < 19800) {
    rocketShippingPrice = 3000
    $("rocket-prod-shipping-price").text("배송비 : 3000원")
  } else {
    $("rocket-prod-shipping-price").text("배송비 무료")
  }

  $(".general-prod-order .order-item-box").each(function (index, item){
    let price = Number($(item).attr("price"))
    let count = Number($(item).attr("count"))
    $(item).children(".prod-before-sale-price").text(price * count + " 원")
    $(item).children(".prod-after-sale-price").text(price * count * 0.95 + " 원")
    generalShippingPrice += 3000
    generalPrice += price * count * 0.95
  })
  $(".general-prod-shipping-price").text("배송비 : " + generalShippingPrice + " 원")
  let productPrice = rocketPrice + generalPrice
  let shippingPrice = rocketShippingPrice + generalShippingPrice
  let total = productPrice + shippingPrice
  $(".total-prod-price").text("상품 가격 : " + productPrice + " , 배송비 : "+shippingPrice+"  , 총 가격 : " + total)
}
