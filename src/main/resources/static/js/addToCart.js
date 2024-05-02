function getCookieValue(cookieName) {
  let cookieValue = document.cookie
    .split("; ")
    .find((row) => row.startsWith(cookieName + "="))
    ?.split("=")[1];
  return cookieValue;
}

document.addEventListener("DOMContentLoaded", function () {
  var addToCartBtn = document.getElementById("addToCartBtn");

  addToCartBtn.addEventListener("click", function () {
    var quantity = document.getElementById("quantity").value;
    var productId = document.getElementById("productId").innerText;
    // var userId = localStorage.getItem("userId");
    var userId = getCookieValue("userId");

    if (!userId) {
      window.location.href = "/login";
      return;
    }

    var data = {
      userId: userId,
      productId: productId,
      quantity: quantity,
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/rest/cart/addToCart", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
        var responseText = xhr.responseText;
        if (responseText.includes("Success")) {
          alert("Product added to cart successfully!");
          window.location.href = "/";
        } else {
          alert("Error adding product to cart. Please try again.");
        }
      } else {
        console.error("Error:", xhr.responseText);
        alert("Server Error");
      }
    };
    xhr.onerror = function () {
      alert("Server Error");
    };
    xhr.send(JSON.stringify(data));
  });
});