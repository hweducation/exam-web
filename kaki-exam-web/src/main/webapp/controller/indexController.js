// 窗口加载完
window.onload = function () {
    var vue = new Vue({
        el : '#app', // 元素绑定
        data : { // 数据模型
            loginName : '', // 登录用户名
            entity:{
                avatar:''
            },
            avatar:'',
            table:false,
            //在js中写入src路径需要使用require来请求，不然无法加载图片，type=card将轮播风格设置成3D效果
            itemList:[
                "/img/temp/lunbo/22.png",
                "/img/temp/lunbo/3.png",
                "/img/temp/lunbo/4.png"
            ],
            iframepagename:'home2.html'
        },
        methods : { // 操作方法
            findLoginName : function () { // 获取登录用户名
                // 发送异步请示
                axios.get("/web/findLoginName")
                    .then((response)=>{
                    // 获取响应数据
                    this.loginName = response.data.loginName;
                    this.entity = response.data.user;

                    console.log("this.entity.avatar  "+this.entity.avatar);
                    if(this.entity.avatar === undefined || this.entity.avatar === null || this.entity.avatar === ""){
                        console.log("1"+this.entity.avatar);
                        this.avatar = "../img/temp/gaki.jpg";
                    }else {
                        console.log("2  "+this.entity.avatar);
                        this.avatar = this.entity.avatar;
                    }

                });
            },
            handleSelect(key, keyPath) { //标签页选中的页
                console.log(key, keyPath);
                switch (key) {
                    case '1':
                        this.iframepagename="home2.html";
                        break;
                    case '2-1':
                        this.iframepagename="paper.html";
                        break;
                    case '2-2':
                        this.iframepagename="history.html";
                        break;
                    case '3-1':
                        this.iframepagename="hikoki.html";
                        break;
                    case '4-1':
                        this.iframepagename="edit.html";
                        break;
                    case '4-2':
                        this.iframepagename="password.html";
                        break;

                }

            },
            uploadAvatar(){ //头像上传
                // 创建表单数据对象 html5
                var formData = new FormData();
                // 表单追加上传的文件
                // 第一个参数：请求参数名称
                // 第二个参数：<input type='file'/> dom对象
                formData.append("file", file.files[0]);

                // 异步上传文件
                axios({
                    method : "post", // 请求方式
                    url : "/upload", // 请求url
                    data : formData, // 请求参数(文件类型的请求参数)
                    headers : {"Content-Type" : "multipart/form-data"} // 请求头

                }).then((response)=>{
                    // 获取响应数据 {status : 200, url : ''}
                    if (response.data.status == 200){
                        // 上传成功，获取图片的url
                        this.entity.avatar = response.data.url;
                        //console.log(response.data);
                        this.avatar = response.data.url;
                    }else{
                        alert("上传失败！");
                    }
                });

            },
            seveOrUpdate(){
                var url = "seve";
                if(this.entity.username){
                    url = "update";
                }

                //保存的请求
                axios.post("/examuser/"+url,this.entity).then((res)=>{
                    if(res.data.flag){
                        this.$message({
                            type:"success",
                            message:'保存成功！！'
                        });
                    }else {
                        this.$message.error("保存失败！");
                    }
                })
            }
        },
        created : function () { // 创建生命周期
            // 调用获取登录用户名方法
            this.findLoginName();
        }
    });
};