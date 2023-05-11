function getCommentByProductId(id){
    fetch('http://localhost:8181/comment?productId=' + id)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)
            let html = ''
            if(data.length != 0){

                for(let cm of data){
                    html += `<div class="col-md-8"  >
                                                <div class="d-flex flex-column comment-section">
                                                    <div class="bg-white p-2">
                                                        <div class="d-flex flex-row user-info"><img class="rounded-circle" src="https://img.myloview.com/posters/man-avatar-graphic-sign-anonymous-male-profile-sign-in-the-circle-isolated-on-white-background-vector-illustration-700-250607736.jpg" width="40">
                                                            <div class="d-flex flex-column justify-content-start ml-2"><span class="d-block font-weight-bold name">${cm.user.username}</span><span class="date text-black-50">Shared publicly - Mar 2023</span></div>
                                                        </div>
                                                        <div class="mt-2">
                                                            <p class="comment-text">${cm.content}</p>
                                                        </div>
                                                    </div>
                                                   
                                                    
                                                </div>
                                            </div>`
                }
            }

            document.getElementById("comment").innerHTML = html;

        })
}

function addComment( productId , userId){
    if(userId === 'null'){
        window.location.href = '/login'
    }
    let content = document.getElementById("commentArea").value
    if(content !== ''){
        fetch('http://localhost:8181/comment' , {
            method : "post",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify({
                "content" : content,
                "productId" : productId,
                "userId" : userId
            })
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                document.getElementById("comment").innerHTML += `<div class="col-md-8"  >
                                                <div class="d-flex flex-column comment-section">
                                                    <div class="bg-white p-2">
                                                        <div class="d-flex flex-row user-info"><img class="rounded-circle" src="https://img.myloview.com/posters/man-avatar-graphic-sign-anonymous-male-profile-sign-in-the-circle-isolated-on-white-background-vector-illustration-700-250607736.jpg" width="40">
                                                            <div class="d-flex flex-column justify-content-start ml-2"><span class="d-block font-weight-bold name">${data.user.username}</span><span class="date text-black-50">Shared publicly - Mar 2023</span></div>
                                                        </div>
                                                        <div class="mt-2">
                                                            <p class="comment-text">${data.content}</p>
                                                        </div>
                                                    </div>
                                                   
                                                    
                                                </div>
                                            </div>`
            })
    }
}