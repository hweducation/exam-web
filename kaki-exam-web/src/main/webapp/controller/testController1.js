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
            adddataList:[], //添加题目列表
            paperEntity:{},  //题目相关信息
            questionList:[], //试卷题目集合
            userAnswerList:[],  //用户选择的答案
            maxScore:0,      //卷纸总分数
            userScore:''  ,    //用户得分
            timeScends:'' ,  //答题时间秒级别
            myTimer:'',     //定时器
            historyentity:{}, //保存考试记录的实体类
            isdisabled:false  //提交按钮

        },
        created(){
            //this.findPage(this.curPage);

            this.paperEntity = JSON.parse(decodeURI(getParameter("paperEntity")));
            //alert(this.paperEntity.id);
            //console.log(JSON.parse(decodeURI(getParameter("paperEntity"))));

            if(this.paperEntity.id){
                this.findPaper(this.paperEntity.id);
                this.timeScends=this.paperEntity.time*60;
            };

            //开始倒计时
            this.countdown();


        },
        updated(){//更新数据时的钩子
            this.checked = (this.ids.length == this.dataList.length);
            this.addchecked = (this.addids.length == this.adddataList.length);
        },
        methods: {

            findPaper(paperId){
                axios.get("/webpaper/findByPaperId?paperId="+paperId).then((res)=>{
                    if (res.data){
                        //当前页数据列表
                        this.questionList = res.data;

                        //计算满分
                        var count =0;
                        if(this.questionList.length>0){
                            for (let x = 0; x <this.questionList.length ; x++) {
                                let tempMap = this.questionList[x];
                                count+=tempMap.score;
                            }
                        }
                        this.maxScore = count;

                        console.log(this.questionList);
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

                //卷纸id
                this.historyentity.paperId = this.paperEntity.id;
                //卷纸名称
                this.historyentity.paperName = this.paperEntity.name;
                //原分数
                this.historyentity.srcScore = this.paperEntity.score;
                //用户得分
                this.historyentity.userScore = this.userScore;




                //保存的请求
                axios.post("/history/"+url,this.historyentity).then((res)=>{
                    if(res.data){
                        this.$message({
                            type:"success",
                            message:'考试记录已保存在“我的历史考试”！！'
                        });
                        //制空表单
                        this.historyentity={} ;

                    }else {
                        this.$message.error("考试记录保存失败！");
                    }
                })




            },
            findPage(){

            },
            submitAQuestionAnswer(index){//提交一道题目

            }
            userAnswerListSave(id,answer){//题目id,选择的结果
                //alert(id+"----"+answer);
                this.userAnswerList.push({"id":id,"answer":answer});
            },
            answerSubmit(questionList){//答题完毕提交答案

                //清空上一次的分数
                this.userScore = 0;

                if(this.userAnswerList.length<=0){
                    alert("您未做出任何选择");
                    return;
                }
                if(this.questionList.length<=0){
                    alert("该卷纸没填加任何题目");
                    return;
                }

                //总分数
                let totelScore = 0;
                for (let i = 0; i <this.userAnswerList.length; i++) {
                    let userAnswerMap = this.userAnswerList[i];
                    for (let j = 0; j < questionList.length; j++) {
                        let questionMap =  questionList[j];

                        //答对的情况
                        if(userAnswerMap.id ===questionMap.id && userAnswerMap.answer === questionMap.answer){
                            totelScore+=questionMap.score;
                            document.getElementById(questionMap.id+"").src='../img/temp/dui.jpg';
                        }

                        //答错的情况
                        if(userAnswerMap.id ===questionMap.id && userAnswerMap.answer !== questionMap.answer){
                            document.getElementById(questionMap.id+"").src='../img/temp/cuo.jpg';
                        }

                    }

                }
                //分数
                this.userScore =  totelScore;

                //删除定时器
                clearTimeout(this.myTimer);
                document.getElementById("remainDays").innerHTML=("<h4 align='right'>考试已结束</h4>");
                //公布答案
                let num= 0;
                for (let v = 0; v < questionList.length; v++) {
                    num+="";
                    document.getElementById(num).style.display = 'block';
                    num= parseInt(num);
                    num++;
                }

                //将此次考试存到历史考试数据库
                this.seveOrUpdate();

                //禁用提交按钮
                this.isdisabled = true;
                window.location.href='question-review.html';
            },


            countdown(){   //倒计时

                this.timeScends-=1;
                if(this.timeScends<=0){
                    clearTimeout(this.myTimer);
                    document.getElementById("remainDays").innerHTML=("<h4 align='right'>考试已结束</h4>");
                    alert("答题时间已结束");
                }
                var minute=parseInt(this.timeScends/60);
                var second=parseInt(this.timeScends%60);
                var str = minute+"分"+second+"秒";

                document.getElementById("remainDays").innerHTML=("<h4 align='right'>剩余时间：<font color=red>"+str+"</font></h4>");

                //500的目的是防止漏掉执行
                this.myTimer=setTimeout(this.countdown,5000);
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
            reviewQuestion(index){//新加的，转到回顾界面题目条件是什么？已知是什么？未知是什么？
                var url="question-review.html?index="+index;
                window.location.href = url;
            },
            review2Question(index){//新加的，转到回顾界面题目条件是什么？已知是什么？未知是什么？
                var url="question-review2.html?index="+index;
                window.location.href = url;
            },
            getQuestionbyIndex(index){
                entity = questionList[index];
            },
            //回显的方法
            gotoTest(entity){
                console.log(entity);
                //解除双向绑定
                var jsonData = JSON.stringify(entity);
                this.entity = JSON.parse(jsonData);
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

