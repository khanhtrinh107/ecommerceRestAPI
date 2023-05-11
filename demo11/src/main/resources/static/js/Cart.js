
function loadCart(){
    fetch('http://localhost:8181/cart')
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            console.log(data)
            if(data.message !== undefined){
                document.getElementById("checkCart").innerText = "Nothing In Shoping Cart"
            }
            else{
                let html = '';
                for(let cart in data){
                    html += `<tr id = "cart${data[cart].productId}">
                                    <td class="shoping__cart__item">
                                        <img style="height: 90px;" src="${data[cart].image}" alt="">
                                        <a href="/view/deltail/${data[cart].productId}"><h5>${data[cart].productName}</h5></a>
                                    </td>
                                    <td class="shoping__cart__price">
                                        $${data[cart].price}
                                    </td>
                                    <td class="shoping__cart__quantity">
                                        <div class="quantity">
                                            <div class="pro-qty">
                                                <input type="number" min="1"  id = "quantity${data[cart].productId}" onblur="updateCart('${data[cart].productId}', '${data[cart].productName}' , '${data[cart].image}' , '${data[cart].price}')"    value="${data[cart].quantity}">
                                            </div>
                                        </div>
                                    </td>
                                    <td id = "totalPrice${data[cart].productId}" class="shoping__cart__total">
                                        $${data[cart].quantity * data[cart].price}
                                    </td>
                                    <td class="shoping__cart__item__close">
                                        <span onclick="deleteCart(${data[cart].productId})" class="icon_close"></span>
                                    </td>
                                </tr>`
                    console.log(cart)
                }
                document.getElementById("cartList").innerHTML = html;
                fetch('http://localhost:8181/cart/display')
                    .then((response) => {
                        return response.json()
                    })
                    .then((data) => {
                        document.getElementById("cartPrice").innerText = '$' +  data.amount1;
                        document.getElementById("finalPrice").innerText =  '$' +  data.amount1;

                    })
            }
        })
}


function addToCart(productId,  productName, image,  price,  quantity){
    fetch('http://localhost:8181/cart', {
        method : "post",
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify({
            "productId" : productId,
            "productName" : productName,
            "image" : image,
            "price" : price,
            "quantity" : quantity
        })
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)
            document.getElementById("cartCount").innerText = data.count;
            document.getElementById("totalPrice").innerText = data.amount1;
        })
}

function displayCart(){
    fetch('http://localhost:8181/cart/display')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            document.getElementById("cartCount").innerText = data.count;
            document.getElementById("totalPrice").innerText = data.amount1;
        })
}

displayCart();

function updateCart(productId , productName , image , price){
    let count = document.getElementById("quantity" + productId).value;
    fetch('http://localhost:8181/cart',{
        method : "put" ,
        headers : {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify({
            "productId" : productId,
            "productName" : productName,
            "image" : image,
            "price" : price,
            "quantity" : count
        })
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let tmp = count * price
            document.getElementById("totalPrice" + productId).innerText = '$' +  tmp.toString()
            fetch('http://localhost:8181/cart/display')
                .then((response) => {
                    return response.json()
                })
                .then((data) => {
                    console.log(data)
                    document.getElementById("cartCount").innerText = data.count;
                    document.getElementById("totalPrice").innerText = data.amount1;
                    document.getElementById("cartPrice").innerText = '$' +  data.amount1;
                    document.getElementById("finalPrice").innerText =  '$'  + data.amount1;
                })
            console.log(data)
        })
}

function deleteCart(id){
    fetch('http://localhost:8181/cart/' + id , {
        method : "delete"
    } )
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            document.getElementById("cart" + id).style.display = "none";
            fetch('http://localhost:8181/cart/display')
                .then((response) => {
                    return response.json()
                })
                .then((data) => {
                    document.getElementById("cartCount").innerText = data.count;
                    document.getElementById("totalPrice").innerText = data.amount1;
                    document.getElementById("cartPrice").innerText = data.amount1;
                    document.getElementById("finalPrice").innerText = data.amount1;
                })
        })
}