<!--	vue-->
window.onload = function () {
    var app = new Vue({
        el:"#app",
        data:{
            // //波浪图-录音
            // drawRecordId:null,
            // oCanvas : null,
            // ctx : null,
            // //波浪图-播放
            // drawPlayId:null,
            // pCanvas : null,
            // pCtx : null,
            nextPageDisable:true,//true表示禁用
            nextPageDisable2:true,
            pageIndex:0,//0：题目页；1：问题回顾第一页 2：问题回顾第二页 3：问卷页
            audio:'',//音频的url
            audio2:'',//音频的url
            recordingFlag:'',
            recordingFlag2:'',
            recorder:Recorder,
            recorder2:Recorder,
            isQuestionView:true,
            questionindex:0,//当前题目编号
            qanswerentity:{},//题目答案的实体类与q_answer表对应
            qAnswerList:[],//链表，链表中元素是qanswerentity
            temp_startTime:'',
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
                //开始倒计时
                this.countdown();
            };

            this.recorder = new Recorder({
                sampleBits: 16,         // 采样位数，支持 8 或 16，默认是16
                sampleRate: 16000,      // 采样率，支持 11025、16000、22050、24000、44100、48000，根据浏览器默认值，我的chrome是48000
                numChannels: 1,         // 声道，支持 1 或 2， 默认是1
                compiling: false       // 是否边录边转换，默认是false
            });
            this.recorder2 = new Recorder({
                sampleBits: 16,         // 采样位数，支持 8 或 16，默认是16
                sampleRate: 16000,      // 采样率，支持 11025、16000、22050、24000、44100、48000，根据浏览器默认值，我的chrome是48000
                numChannels: 1,         // 声道，支持 1 或 2， 默认是1
                compiling: false       // 是否边录边转换，默认是false
            });
            //this.startCanvas();
            this.temp_startTime = new Date();

            console.log(this.temp_startTime);
        },
        updated(){//更新数据时的钩子
            this.checked = (this.ids.length == this.dataList.length);
            this.addchecked = (this.addids.length == this.adddataList.length);
        },
        methods: {

            // /**
            //  * 波浪图配置
            //  * */
            // startCanvas(){
            //     //录音波浪
            //     this.oCanvas = document.getElementById('canvas');
            //     this.ctx = this.oCanvas.getContext("2d");
            //     //播放波浪
            //     this.pCanvas = document.getElementById('playChart');
            //     this.pCtx = this.pCanvas.getContext("2d");
            // },
            // /**
            //  * 绘制波浪图-录音
            //  * */
            // drawRecord () {
            //     // 用requestAnimationFrame稳定60fps绘制
            //     this.drawRecordId = requestAnimationFrame(this.drawRecord);
            //
            //     // 实时获取音频大小数据
            //     let dataArray = this.recorder.getRecordAnalyseData(),
            //         bufferLength = dataArray.length;
            //
            //     // 填充背景色
            //     this.ctx.fillStyle = 'rgb(200, 200, 200)';
            //     this.ctx.fillRect(0, 0, this.oCanvas.width, this.oCanvas.height);
            //
            //     // 设定波形绘制颜色
            //     this.ctx.lineWidth = 2;
            //     this.ctx.strokeStyle = 'rgb(0, 0, 0)';
            //
            //     this.ctx.beginPath();
            //
            //     var sliceWidth = this.oCanvas.width * 1.0 / bufferLength, // 一个点占多少位置，共有bufferLength个点要绘制
            //         x = 0;          // 绘制点的x轴位置
            //
            //     for (var i = 0; i < bufferLength; i++) {
            //         var v = dataArray[i] / 128.0;
            //         var y = v * this.oCanvas.height / 2;
            //
            //         if (i === 0) {
            //             // 第一个点
            //             this.ctx.moveTo(x, y);
            //         } else {
            //             // 剩余的点
            //             this.ctx.lineTo(x, y);
            //         }
            //         // 依次平移，绘制所有点
            //         x += sliceWidth;
            //     }
            //
            //     this.ctx.lineTo(this.oCanvas.width, this.oCanvas.height / 2);
            //     this.ctx.stroke();
            // },
            recorderBegin() {
                if(this.recorder!=null){
                    this.recorder.start().then(() => {
                        // 开始录音
                        console.log("recording...");
                        //setTimeout(this.showCanvas, 500);
                        this.recordingFlag='recording...';
                        //this.drawRecord();//开始绘制图片
                    }, (error) => {
                        // 出错了
                        this.recordingFlag='请检查录音设备';
                        console.log(`${error.name} : ${error.message}`);
                    });
                }
                else{
                    console.log("recorder为空");
                }
            },
            recorderStop(id) {//可以不带参数，先不改了

                //id = "recording";//先随便写一个写死的,如果加入多录音123...
                //recorder.stop();
                if(this.recordingFlag=='recording...'){
                    this.nextPageDisable=false;
                    this.recorder.stop();
                    this.recorder.play()
                    //document.getElementById("recording").innerHTML=("<h4 align='center'> </h4>");
                    this.recordingFlag='';
                    //var blob = recorder.getPCMBlob();
                    var blob = this.recorder.getWAVBlob();
                    //alert(blob);
                    var url = URL.createObjectURL(blob);
                    //alert(url);
                    //alert(blob.size);
                    var answernum_name=this.paperEntity.id+"-"+id+".wav";

                    if(blob.size<3*1024*1024){
                        this.uploadBlob(blob, answernum_name,id);
                    }
                    // 销毁录音实例，置为null释放资源，fn为回调函数，
                    this.recorder.destroy().then(function() {
                        this.recorder = null;
                    });
                    //history.go(0);
                }
            },
            recorderBegin2(){
                if(this.recorder2!=null){
                    this.recorder2.start().then(() => {
                        // 开始录音
                        console.log("recording...");
                        //setTimeout(this.showCanvas, 500);
                        this.recordingFlag2='recording...';
                        //this.drawRecord();//开始绘制图片
                    }, (error) => {
                        // 出错了
                        this.recordingFlag2='请检查录音设备';
                        console.log(`${error.name} : ${error.message}`);
                    });
                }
                else{
                    console.log("recorder为空");
                }
            },
            recorderStop2(id) {
                //id = "recording";//先随便写一个写死的,如果加入多录音123...
                //recorder.stop();
                if(this.recordingFlag2=="recording..."){

                    this.nextPageDisable2=false;
                    this.recorder2.stop();
                    this.recorder2.play()
                    //document.getElementById("recording").innerHTML=("<h4 align='center'> </h4>");
                    this.recordingFlag2='';
                    //var blob = recorder.getPCMBlob();
                    var blob = this.recorder2.getWAVBlob();
                    //alert(blob);
                    var url = URL.createObjectURL(blob);
                    //alert(url);
                    //alert(blob.size);
                    var answernum_name=this.paperEntity.id+"-"+id+".wav";

                    if(blob.size<3*1024*1024){
                        this.uploadBlob(blob, answernum_name,id);
                    }
                    // 销毁录音实例，置为null释放资源，fn为回调函数，
                    this.recorder2.destroy().then(function() {
                        this.recorder2 = null;
                    });
                    //history.go(0);
                }

            },
            uploadBlob(blob, filename,id){
                // create a blob here for testing
                var fd = new FormData();
                filename=this.questionindex+"-"+filename;
                fd.append('name', 'my_file');//'filename'
                fd.append('id', id);
                fd.append('audio_file', blob);
                fd.set('filename', filename);
                axios({
                    method : "post", // 请求方式
                    url : "http://localhost:9001/webqanswer/upload", // 请求url
                    data : fd, // 请求参数(文件类型的请求参数)
                    headers : {"Content-Type" : "multipart/form-data"} // 请求头
                }).then((response)=>{
                    // 获取响应数据 {status : 200, url : ''}
                    if (response.data.status == 200){
                        // 上传成功，获取音频的url
                        if(response.data.id==1)
                            this.audio = response.data.url;
                        else
                            this.audio2 = response.data.url;

                    }else{
                        alert("上传失败！");
                    }
                });

            },
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
                            this.$forceUpdate();
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
            // userAnswerListSave(id,answer){//题目id,选择的结果
            //     //alert(id+"----"+answer);
            //     this.userAnswerList.push({"id":id,"answer":answer});
            // },
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
                console.log("this.questionList.length:")
                console.log(this.questionList.length)
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
                // let num= 0;
                // for (let v = 0; v < questionList.length; v++) {
                //     num+="";
                //     document.getElementById(num).style.display = 'block';
                //     num= parseInt(num);
                //     num++;
                // }

                //将此次考试存到历史考试数据库
                this.seveOrUpdate();

                //禁用提交按钮
                this.isdisabled = true;
            },


            countdown(){   //倒计时

                this.timeScends-=1;
                if(this.timeScends<=0){
                    clearTimeout(this.myTimer);
                    document.getElementById("remainDays").innerHTML=("<h4 align='right'>考试已结束</h4>");
                    alert("答题时间已结束");
                }
                else{

                    var minute=parseInt(this.timeScends/60);
                    var second=parseInt(this.timeScends%60);
                    var str = minute+"分"+second+"秒";

                    document.getElementById("remainDays").innerHTML=("<h4 align='right'>剩余时间：<font color=red>"+str+"</font></h4>");

                    this.myTimer = setTimeout(this.countdown, 1000);

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
                alert("qqqqqqqqqqqqqq");
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
            reviewQuestion(){//新加的，转到回顾界面题目条件是什么？已知是什么？未知是什么？;并记录答案
                //记录做题结束时间
                let click_time=new Date();

                //this.isQuestionView = false;
                this.pageIndex++;

                let id=this.questionList[this.questionindex].id;
                let inputs = document.getElementsByName(id);
                let select =false;
                let selectOption=[];
                for (let i = 0; i < inputs.length; i++) {
                    if (inputs[i].checked) {
                        selectOption.push(inputs[i].value);
                        select=true;
                    }
                }
                if(select==false){//根本没选
                    this.userAnswerList.push({"id":id,"answer":' ',"endTime":click_time });
                    console.log(this.userAnswerList);
                    console.log(click_time);
                }
                else{
                    let str = '';
                    for(let i = 0;i < selectOption.length - 1; i++){
                        str += selectOption[i]+'&';
                    }
                    str += selectOption[selectOption.length-1];
                    this.userAnswerList.push({"id":id,"answer":str,"endTime":click_time });
                    console.log(this.userAnswerList);
                }

                // var url="question-review.html?index="+index;
                // window.location.href = url;
            },
            nextPage(index){//index=1 为题目回顾1界面的下一页，index=2 为题目回顾2界面的下一页，
                this.pageIndex++;
                this.$forceUpdate();
                // if(index==1){
                //     if(this.recordingFlag='recording...'){
                //         this.recorderStop(1);
                //     }
                // }
                // else if(index==2){
                //     if(this.recordingFlag2='recording...'){
                //         this.recorderStop2(2);
                //     }
                // }
            },
            submitAQuestionAnswer(){//提交一道题目到数据库
                //显示下一题
                // var url="test.html";//?index="+index
                // window.location.href = url;
                this.nextPageDisable=true;
                this.nextPageDisable2=true;
                if(this.recorder!=null){
                    //alert("请录音");
                }
                //记录信心等

                let Confidence = document.getElementsByName('Confidence');
                for (let i = 0; i < Confidence.length; i++) {
                    if (Confidence[i].checked) {
                        this.qanswerentity.l1=i+1;
                    }
                }

                let emotion = document.getElementsByName('emotion');
                for (let i = 0; i < emotion.length; i++) {
                    if (emotion[i].checked) {
                        this.qanswerentity.l2=i+1;
                    }
                }

                let easy = document.getElementsByName('easy');
                for (let i = 0; i < easy.length; i++) {
                    if (easy[i].checked) {
                        this.qanswerentity.l3=i+1;
                    }
                }
                console.log("this.userAnswerList");
                console.log(this.userAnswerList);
                if(this.questionList.length<=0){
                    alert("该卷纸没填加任何题目");
                    return;
                }
                let questionMap= this.questionList[this.questionindex];
                console.log("questionList");
                console.log(this.questionList);
                console.log("questionMap");
                console.log(questionMap);
                //装配qanswerentity
                this.qanswerentity.pid=this.paperEntity.id;
                this.qanswerentity.qid = questionMap.id;//问题id
                this.qanswerentity.answer =this.userAnswerList[this.userAnswerList.length-1].answer;
                this.qanswerentity.endTime =this.userAnswerList[this.userAnswerList.length-1].endTime;
                this.qanswerentity.review1_1=this.audio;
                this.qanswerentity.review2_1=this.audio2;
                this.qanswerentity.startTime=this.temp_startTime;
                //放到链表中
                this.qAnswerList.push(this.qanswerentity);
                let click_submit_time=new Date();//保存点击时间
                //保存的请求
                axios.post("/webqanswer/save",this.qanswerentity).then((res)=>{
                    if(res.data){
                        //alert("提交题目成功");
                        // this.$message({
                        //     type:"success",
                        //     message:'本题记录已保存！！'
                        // });
                        //制空表单
                        this.qanswerentity={};
                        this.temp_startTime = click_submit_time;
                    }else {
                        alert("提交题目失败");
                        this.$message.error("题目记录保存失败！");
                    }
                })
                if(this.questionindex<this.questionList.length-1){

                    this.questionindex+=1;
                    //this.isQuestionView=true;
                    this.pageIndex=0;
                    let myQuestion=this.questionList[this.questionindex];
                    this.$forceUpdate();
                }
                else{
                    alert("您已完成试卷");
                    window.location.href="paper.html";
                }
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
