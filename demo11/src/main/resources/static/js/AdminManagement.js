let currentProductId;
let currentPage;
let currentCategoryId;
let currentPageCategory;
let currentUserId;
let currentUserPage;
function setCurrentUserId(id){
    currentUserId = id;
}
function setCurrentCategoryId(id){
    currentCategoryId = id;
    console.log(currentCategoryId)
}
function setCurrentProductId(id){
    currentProductId = id;
    console.log(currentProductId)
}

function product(){
    document.getElementById("product").style.display = "block";
    document.getElementById("category").style.display = "none";
    document.getElementById("user").style.display = "none";
}
function category(){
    document.getElementById("category").style.display = "block";
    document.getElementById("product").style.display = "none";
    document.getElementById("user").style.display = "none";
}

function user(){
    document.getElementById("user").style.display = "block";
    document.getElementById("category").style.display = "none";
    document.getElementById("product").style.display = "none";
}


function deleteProduct(){
    fetch('http://localhost:8080/product/' + currentProductId, {
        method : "delete"
    })
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            document.getElementById("deleteEmployeeModal").style.display = "none";
            document.querySelector(".modal-backdrop").style.display = "none";
            alert(data.message);
            loadProduct(currentPage)
        })
}


function loadProduct(page){
    currentPage = page;
    fetch('http://localhost:8080/product/search?page=' + page)
        .then((response) =>{
            return response.json();
        })
        .then((data) => {
            let html = '';
            for(let product of data.productList){
                html += `<tr>
                                <th><span class="custom-checkbox">
                           <input type="checkbox" id="checkbox1" name="option[]" value="1">
                           <label for="checkbox1"></label></th>
                                <th>${product.productId}</th>
                                <th>${product.productName}</th>
                                <th>${product.price}</th>
                                <th>${product.description}</th>
                                <th>
                                    <a onclick="setCurrentProductId(${product.productId}); getProductById(${product.productId});" href="#editEmployeeModal" class="edit" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
                                    </a>
                                    <a onclick="setCurrentProductId(${product.productId})" href="#deleteEmployeeModal" class="delete" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                    </a>
                                </th>
                            </tr>`
            }
            document.getElementById("listProduct").innerHTML = html;
            let html2 = '';
            for(let i = 1 ; i <= data.pageCount ; i++){
                html2 += `<li class="page-item "><a href="javascript:void(0);" onclick="loadProduct(${i})" class="page-link">${i}</a></li>`
            }
            document.getElementById("paging1").innerHTML = html2;
        })
}

function loadCategory(page){
    currentPageCategory = page;
    fetch('http://localhost:8080/category?page=' + page)
        .then((response) => {
            return  response.json();
        })
        .then((data) => {
            let html = '';
            for(let category of data.categories){
                html += `<tr>
                                <th><span class="custom-checkbox">
                          <input type="checkbox" id="checkbox1" name="option[]" value="1">
                          <label for="checkbox1"></label></th>
                                <th>${category.categoryId}</th>
                                <th>${category.categoryName}</th>
                                <th>${category.description}</th>
                                <th>
                                    <a href="#editCategory"  onclick="setCurrentCategoryId(${category.categoryId}); getCategoryById(${category.categoryId});" class="edit" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
                                    </a>
                                    <a href="#deleteCategory"  onclick="setCurrentCategoryId(${category.categoryId})" class="delete" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                    </a>
                                </th>
                            </tr>`
            }
            document.getElementById("listCategory").innerHTML = html;
            let html2 = '';
            for(let i = 1 ; i <= data.pageCount ; i++){
                html2 += `<li class="page-item "><a href="javascript:void(0);" onclick="loadCategory(${i})" class="page-link">${i}</a></li>`
            }
            document.getElementById("paging2").innerHTML = html2;
        })
}

function loadUser(page){
    currentUserPage = page
    fetch('http://localhost:8080/user?page=' + page )
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            let html = '';
            for(let user of data.users){
                html += `<tr>
                                <th><span class="custom-checkbox">
                          <input type="checkbox" id="checkbox1" name="option[]" value="1">
                          <label for="checkbox1"></label></th>
                                <th>${user.userId}</th>
                                <th>${user.username}</th>
                                <th>${user.email}</th>
                                <th>
                                    <a href="#deleteUser" onclick="setCurrentUserId(${user.userId})" class="delete" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                    </a>
                                </th>
                            </tr>`
            }
            document.getElementById("userList").innerHTML = html;
            let html2 = '';
            for(let i = 1 ; i <= data.pageCount ; i++){
                html2 += `<li class="page-item "><a href="javascript:void(0);" onclick="loadUser(${i})" class="page-link">${i}</a></li>`
            }
            document.getElementById("paging").innerHTML = html2;
        })
}

function getAllCategory(){
    fetch('http://localhost:8080/category/getAll')
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            let html = '';
            for(let category of data){
                html += ` <option value="${category.categoryName}">${category.categoryName}</option>`
            }
            document.getElementById("categorySelection").innerHTML = html;
            document.getElementById("editCategorySelection").innerHTML = html;
        })
}

function getCategoryById(id){
    console.log('ok')
    fetch('http://localhost:8080/category/' + id)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            document.getElementById('editCategoryName').value = data.categoryName
            document.getElementById('editCategoryDes').value = data.description
            document.getElementById('editCateBtn').disabled = true;
        })
}


function getProductById(id){
    fetch('http://localhost:8080/product/' + id)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            document.getElementById('editProductName').value = data.productName
            document.getElementById('editPrice').value = data.price
            document.getElementById('editProductDescription').value = data.description
            let option = document.getElementById('editCategorySelection');
            const category = data.category.categoryName;
            console.log(category)
            for(let i = 0 ; i < option.options.length ; i++){
                if(option.options[i].value === category){
                    option.selectedIndex = i;
                    break;
                }
            }
            const img = document.getElementById('editImg');
            img.setAttribute('src', `${data.image}`);
            document.getElementById('editProduct').disabled = true;

        })
}

function enable(){
    console.log('ok')
    let myButton = document.getElementById("editProduct");
    myButton.disabled = false;
}
function enable2(){
    console.log('ok')
    let myButton = document.getElementById("editCateBtn");
    myButton.disabled = false;
}


loadProduct(1);
loadCategory(1);
loadUser(1)
getAllCategory();
function getImg(){
    const file = document.getElementById("uploadImg").files[0];
    const urlFile = URL.createObjectURL(file);
    const img = document.getElementById('img');
    img.setAttribute('src', urlFile);
}
function getImg2(){
    enable()
    const file = document.getElementById("editUploadImg").files[0];
    const urlFile = URL.createObjectURL(file);
    const img = document.getElementById('editImg');
    img.setAttribute('src', urlFile);
}

document.getElementById('editCateBtn').addEventListener('click' , (e) => {
    e.preventDefault();
    const categoryName = document.getElementById('editCategoryName').value
    const description = document.getElementById('editCategoryDes').value
    fetch('http://localhost:8080/category/' + currentCategoryId , {
        method : "put",
        headers : {
            "Content-Type": "application/json",
        },
        body : JSON.stringify({
            'categoryName' : categoryName,
            'description' : description
        })
    }).then((response) => {
        return response.json()
    }).then((data) => {
        if(data.message === undefined){
            document.getElementById("editCategory").style.display = "none";
            document.querySelector(".modal-backdrop").style.display = "none";
            loadCategory(currentPage)
        }
        else{
            document.getElementById('editCategoryError').innerText = data.message;
        }
    })
})


document.getElementById('deleteCategoryBtn').addEventListener('click' , (e) => {
    e.preventDefault();
    console.log(currentCategoryId)
    fetch('http://localhost:8080/category/' + currentCategoryId , {
        method : 'delete'
    }).then((response) => {
        return response.json()
    }).then((data) => {
        alert(data.message)
        document.getElementById("deleteCategory").style.display = "none";
        document.querySelector(".modal-backdrop").style.display = "none";
        loadCategory(currentPage)
    })
})




