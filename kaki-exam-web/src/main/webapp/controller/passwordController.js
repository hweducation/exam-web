window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            entity:{

            },
            oldPassword:"",
            newPassword:"",
            new2Password:"",
            deptList:[]
        },
        created(){
            this.findLoginName();
        },

        methods: {
            sevePassword(){
                var url = "sevePassword";

                //校验
                if(!(this.newPassword === this.new2Password)){
                    this.$message({
                        type:"error",
                        message:'两次输入的新密码不一致'
                    });
                    return;
                }


                this.entity.oldPassword = this.oldPassword;
                this.entity.newPassword = this.newPassword;

                //保存的请求
                axios.post("/examuser/"+url,this.entity).then((res)=>{
                    if(res.data.flag){
                        //父页面重新加载
                        if (self.frameElement && self.frameElement.tagName == "IFRAME") {
                            parent.location.href="/logout";
                            //parent.location.reload();//父页面重新加载
                        }
                    }else {
                        this.$message.error("修改失败！");
                    }
                })

            },
            findLoginName () { // 获取登录用户名
                // 发送异步请示
                axios.get("/web/findLoginName")
                    .then((response)=>{
                        // 获取响应数据
                        this.loginName = response.data.loginName;

                        this.entity = response.data.user;


                    });
            },
            sleep(n){ //睡眠
                var start = new Date().getTime();
                console.log('休眠前：' + start);
                while (true) {
                    if (new Date().getTime() - start > n) {
                        break;
                    }
                }
                console.log('休眠后：' + new Date().getTime());
            }
        }
    });
};

