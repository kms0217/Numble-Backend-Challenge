function toCart() {

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
