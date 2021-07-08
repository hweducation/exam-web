<!--	vue-->
window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            dataList:[],//当前页数据
            entity:{},//添加或 修改的表单提交数据
            addentity:{},//添加题目时的对象
            pagetotal:0,//总页数
            addpagetotal:0,//添加题目列表的总页数
            curPage:1,//当前页
            addcurPage:1,//添加题目列表的当前页
            searchEntity:{
                deptFlag:''
            },//搜索条件封装
            ids:[],//多选框数组
            addids:[],//添加题目列表中的多选框数组
            checked:false,//全选框是否选中
            addchecked:false,//添加题目列表中全选框是否选中
            deptList:[],
            adddataList:[] //添加题目列表
        },
        created(){
            this.findPage(this.curPage);

            /*this.findDept();*/
        },
        updated(){//更新数据时的钩子
            this.checked = (this.ids.length == this.dataList.length);
            this.addchecked = (this.addids.length == this.adddataList.length);
        },
        methods: {
            //分页查询数据
            findPage(curPage){
                axios.get("/webpaper/findByPage?curPage="+curPage,{params : this.searchEntity}).then((res)=>{
                    if (res.data){
                        //当前页数据列表
                        this.dataList = res.data.rows;
                        //总页数
                        this.pagetotal= res.data.pages;
                        //设置当前页码
                        this.curPage= curPage;
                        //翻页自动清空
                        this.checked = [];
                    } else {
                        this.$message.error("查询失败！");
                    }
                }).catch((e)=>{

                    alert("系统异常。。。"+e)
                });
            },
            //新建商品或者修改的方法
            seveOrUpdate(){
                var url = "seve";
                if(this.entity.id){
                    url = "update";
                }

                if(this.entity.name && this.entity.score &&this.entity.deptFlag &&  this.entity.time){
                    //保存的请求
                    axios.post("/paper/"+url,this.entity).then((res)=>{
                        if(res.data){
                            this.$message({
                                type:"success",
                                message:'保存成功！！'
                            });
                            //制空表单
                            this.entity={} ;
                            //刷新页面
                            this.findPage(this.curPage);

                        }else {
                            this.$message.error("保存失败！");
                        }
                    })
                }else {
                    this.$message.error("商品名称或首字母不能为空！");
                }



            },

            //添加题目到卷纸中的保存按钮
            addseveOrUpdate(e){

                var tempMap = {};
                tempMap.paperId = this.addentity.id;
                tempMap.questionidList =  this.addids;

                if(this.addentity.id && this.addids.length>0){
                    //保存的请求
                    axios.post("/paperQuestion/seve",tempMap).then((res)=>{
                        if(res.data){
                            this.$message({
                                type:"success",
                                message:'保存成功！！'
                            });
                            //制空表单
                            this.entity={} ;
                            //刷新页面
                            this.findPage(this.curPage);

                        }else {
                            this.$message.error("保存失败！");
                        }
                    })
                }else {
                    this.$message.error("商品名称或首字母不能为空！");
                }



            },
            findDept(){//部门下拉列表
                axios.get("/dept/findAll").then((res)=>{
                    if (res.data){

                        this.deptList = res.data;

                    } else {
                        this.$message.error("部门列表查询失败！");
                    }
                }).catch((e)=>{

                    alert("系统异常。。。"+e)
                });


            },

            //回显的方法
            gotoTest(entity){
                //console.log(entity);
                //解除双向绑定
                var jsonData = JSON.stringify(entity);
                this.entity = JSON.parse(jsonData);

                location.href= "test_mobile.html?&paperEntity=" +encodeURI(JSON.stringify(entity));
            },
            showQuestion(addcurPage,e){
                //解除双向绑定
                if(e!=null &&e!=undefined&& e!=''){
                    var jsonData = JSON.stringify(e);
                    this.addentity = JSON.parse(jsonData);
                }


                axios.get("/question/findByPage?curPage="+addcurPage,{params : this.searchEntity}).then((res)=>{
                    if (res.data){
                        //当前页数据列表
                        this.adddataList = res.data.rows;
                        //总页数
                        this.addpagetotal= res.data.pages;
                        //设置当前页码
                        this.addcurPage= addcurPage;
                        //翻页自动清空
                        this.checked = [];
                    } else {
                        this.$message.error("查询失败！");
                    }
                }).catch((e)=>{

                    alert("系统异常。。。"+e)
                });


                axios.get("/paperQuestion/findByPaperId?id="+this.addentity.id).then((res)=>{
                    if (res.data){


                        if(this.addids.length>0){

                        }else {

                            this.addids = res.data;
                        }


                    } else {
                        this.$message.error("查询失败！");
                    }
                }).catch((e)=>{

                    alert("系统异常。。。"+e)
                });




            },
            temp(){
                this.addids =[];
            },

            //全选复选框
            checkAll(e){
                //清空全选数组
                this.ids =[];
                if(e.target.checked){
                    //如果复选框被选中，那本页的复选框将全部被选中dataList就是当前页的数据数组
                    for(var i=0;i<this.dataList.length;i++){
                        //把本页的每个id放到这个ids数组里，就可以提交到后面了
                        //v-model="ids"双向绑定自动打对号
                        this.ids.push(this.dataList[i].id)
                    }
                }
            },
            //添加题目时的全选复选框
            addcheckAll(e){
                //清空全选数组
                this.addids =[];
                if(e.target.checked){
                    //如果复选框被选中，那本页的复选框将全部被选中dataList就是当前页的数据数组
                    for(var i=0;i<this.adddataList.length;i++){
                        //把本页的每个id放到这个ids数组里，就可以提交到后面了
                        //v-model="ids"双向绑定自动打对号
                        this.addids.push(this.adddataList[i].id)
                    }
                }
            },
            //删除的方法
            del(){

                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //执行删除操作
                    axios.post("/paper/delete",{ids:this.ids}).then((res)=>{
                        if(res.data){
                            this.$message({
                                type: 'success',
                                message: "删除成功"
                            });
                            //判断是否是最后一页，并且全选，有没有选中
                            var pageNumber = (this.checked && this.curPage ==this.pagetotal)?this.curPage-1:this.curPage;


                            //进行删除后分页
                            this.findPage(pageNumber);

                        }else {
                            this.$message.error("删除失败");
                        }
                    }).catch((r)=>{
                        this.$message.error(r);
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
    });
};

