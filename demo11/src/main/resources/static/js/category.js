function getAllCategories(){
    fetch('http://localhost:8080/category')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html ='';
            for(let category of data){
                html += `<li><a href="javascript:void(0);" onclick="getProductByCategory(${category.categoryId})">${category.categoryName}</a></li>`
            }
            document.getElementById("category").innerHTML = html
            document.getElementById("categories").innerHTML = html
        })
}

function getProductByCategory(id){
    fetch('http://localhost:8080/category/product/' + id)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html =''
            for(let product of data.productList){
                html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" data-setbg="${product.image}">
                                    <img src="${product.image}">
                                    <ul class="product__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${product.productId}', '${product.productName}',${product.image}, '${product.price}', 1);" href="javascript:void(0);" ><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6><a href="deltail/${product.productId}">${product.productName}</a></h6>
                                    <h5>$${product.price}</h5>
                                </div>
                            </div>
                        </div>`
            }
            document.getElementById("product").innerHTML = html
            let check = document.getElementById("product2").style.display = "none"
            let check2 = document.getElementById("pagination7").style.display = "none"
            let html2 = '';
            for(let i = 1 ; i <= data.pageCount ; i++){
                html2 += `<a href="javascript:void(0);" onclick="paging2(${id} , ${i})" >${i}</a>`
            }
            document.getElementById("pagination").innerHTML = html2
        })
}

function paging2(categoryId , page){
    fetch('http://localhost:8080/category/product/' + categoryId + '?page=' + page)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html =''
            for(let product of data.productList){
                html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" data-setbg="${product.image}">
                                    <img src="${product.image}">
                                    <ul class="product__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${product.productId}', '${product.productName}',${product.image}, '${product.price}', 1);" href="javascript:void(0);" ><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6><a href="deltail/${product.productId}">${product.productName}</a></h6>
                                    <h5>$${product.price}</h5>
                                </div>
                            </div>
                        </div>`
            }
            document.getElementById("product").innerHTML = html
            let check = document.getElementById("product2");
            if(check !== null){
                check.style.display = "none";
            }
        })
}

