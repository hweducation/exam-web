window.onload = function () {
    var vue = new Vue({
        el:'#app',
        data:{
            loginName:'',//显示的用户名
            time:''//最后登录时间
        },
        created(){
            this.findloginName();
        },
        methods:{
            //获取用户名
            findloginName(){
                axios.get("/user/findLongName").then((res)=>{
                    if (res.data){
                        this.loginName = res.data.loginName;
                        var date = new Date().getDate();
                        this.time = this.getFormatDate();
                    }else {
                        this.$message.error("获取用户名失败");
                    }
                }).catch((e)=>{
                    this.$message.error(e);
                })
            },
            getFormatDate() {
                var date = new Date();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                var currentDate = date.getFullYear() + "-" + month + "-" + strDate
                    + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                return currentDate;
            }
        }
    });
};