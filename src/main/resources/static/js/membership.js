function membershipToggle() {
  $.ajax({
    type: "post",
    url: "/membership",
    success: data => {
      location.href="/membership"
    }
  })
}