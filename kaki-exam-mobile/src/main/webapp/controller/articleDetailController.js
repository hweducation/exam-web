<!--	vue-->
window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            entity:{
                id:""
            }
        },
        created(){
            //标题

            this.entity.id = getParameter("id");
            this.findById();

        },

        methods: {
            findById(){
                axios.get("/article/findById",{params : this.entity}).then((res)=>{
                   console.log(res);
                    if (res.data.code===0){
                        //当前页数据列表
                        this.entity = res.data.data;

                    } else {
                        this.$message.error("查询失败！");
                    }
                }).catch((e)=>{

                    alert("系统异常。。。"+e)
                });
            }
        }

    });
};

