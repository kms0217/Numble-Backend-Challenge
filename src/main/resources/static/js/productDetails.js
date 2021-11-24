function toCart() {
  let data = {
    "productId" : $("#productId").attr("value"),
    "count" : $("#count-input").val(),
  }
  if ($(".option-form").val === "clothes"){
    data["size"] = $("#size-option").find(':selected').attr('size')
    data["color"] = $("#color-option").find(':selected').attr('color')
  } else {
    data["optionId"] = $("#optionSelect").find(':selected').attr('optionId')
  }
  $.ajax({
    type: "post",
    url: "/cart",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: data => {
      alert("장바구니에 담았습니다.")
    },
    error: data => {
      alert("실패했습니다.")
    }
  })
}

function optionChange() {
  const title = $('#optionSelect').find(':selected').attr('title');
  const price = $('#optionSelect').find(':selected').attr('price');
  $('#origin-price').empty()
  $('#origin-price').append(price + " 원")
  $(".price-div").empty()
  if ($(".price-div").attr("type") === "general")
    $(".price-div").append((price * 0.95) + " 원")
  if ($(".price-div").attr("type") === "rocket")
    $(".price-div").append((price * 0.95 * 0.9) + " 원")
}

function order() {
  const productId = $("#productId").attr("value")
  const count = $("#count-input").val()
  let queryParam = "?count=" + count;
  if ($(".option-form").val === "clothes"){
    queryParam += "&size=" + $("#size-option").find(':selected').attr('size') ;
    queryParam += "&color=" + $("#color-option").find(':selected').attr('color');
  } else {
    queryParam += "&optionId=" + $("#optionSelect").find(':selected').attr('optionId')
  }
  window.location.href = "/order/direct/" + productId + queryParam
}