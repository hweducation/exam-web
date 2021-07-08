window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            entity:{},
            deptList:[]
        },
        created(){
            this.findLoginName();
        },

        methods: {
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

            },
            findLoginName () { // 获取登录用户名
                // 发送异步请示
                axios.get("/web/findLoginName")
                    .then((response)=>{
                        // 获取响应数据
                        this.loginName = response.data.loginName;

                        this.entity = response.data.user;
                        console.log(this.entity);
                        this.findDept();
                    });
            },
            findDept(){//部门下拉列表
                axios.get("/dept/findAll").then((res)=>{
                    if (res.data){
                        this.deptList = res.data;
                    } else {
                        this.$message.error("部门列表查询失败！");
                    }
                }).catch((e)=>{
                    this.$message({
                        type:"error",
                        message:"系统异常。。。"+e
                    });
                });
            }
        }
    });
};

