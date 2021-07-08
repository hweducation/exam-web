<!--	vue-->
window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            dataList:[],//当前页数据
            entity:{
               optionMap:{
                    A:'',
                    B:'',
                    C:'',
                    D:''
                }
            },//添加或 修改的表单提交数据
            optionMap:{'A':'',  //把entity中的optionmap单独提出来赋值
                'B':'',
                'C':'',
                'D':''},  //选项map
            pagetotal:0,//总页数
            curPage:1,//当前页
            searchEntity:{},//搜索条件封装
            ids:[],//多选框数组
            checked:false,//全选框是否选中
            msg:'题目编辑'
        },
        created(){
            this.findPage(this.curPage);

        },
        updated(){//更新数据时的钩子
            this.checked = (this.ids.length == this.dataList.length);

        },
        methods: {
            //分页查询数据
            findPage(curPage){
                axios.get("/question/findByPage?curPage="+curPage,{params : this.searchEntity}).then((res)=>{
                    if (res.data){
                        this.entity.optionMap = {"A":"A. ","B":"B. ","C":"C. ","D":"D."};
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

                if(this.entity.type && this.entity.content &&this.entity.answer &&  this.entity.score){
                    this.entity.optionMap = this.optionMap;
                    //保存的请求
                    axios.post("/question/"+url,this.entity).then((res)=>{
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
            //回显的方法
            show(entity){
                //解除双向绑定
                var jsonData = JSON.stringify(entity);
                this.entity = JSON.parse(jsonData);

                //选项
                var jsonDataOptionMap = JSON.stringify(entity.optionMap);
                this.optionMap = JSON.parse(jsonDataOptionMap);
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
            //删除的方法
            del(){

                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //执行删除操作
                    axios.post("/question/delete",{ids:this.ids}).then((res)=>{
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

