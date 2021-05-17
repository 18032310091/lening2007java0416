var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        pageNum:1,
        pageSize:5,
        page:{},
        searchEntity: {},
        entity:{},
        dlist:[{postids:[]}],
        deptids:[]
    },
    methods:{
        getUserListConn:function () {
            var _this = this;
            axios.post("../user/getUserListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.userlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getUserListConn();
        },
        toUserDept:function (id) {
            var _this = this;
            axios.get("../user/getUserVoById.do?id="+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                _this.deptids = response.data.deptids;
                document.getElementById("userdeptdiv").style.display="block";
            });
        },
        saveUserDept:function () {
            //把用户id和deptids传到后台，在service中，先删除后添加
            var _this = this;
            axios.post("../user/saveUserDept.do?id="+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    document.getElementById("userdeptdiv").style.display="none";
                    _this.getUserListConn();
                }else{
                    alert(response.data.msg);
                }
            });
        },
        toUserPost:function (id) {
            var _this = this;
            axios.get("../user/getUserInfo.do?id="+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                document.getElementById("userpostdiv").style.display="block";
            });
        },
        saveUserPost:function () {
            //alert(this.dlist);
            this.entity.dlist = this.dlist;
            var _this =this;
            axios.post('../user/saveUserPost.do',_this.entity).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userpostdiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            });
        },
        deleteUserById:function (id) {
            var _this = this;
            axios.get("../user/deleteUserById.do?id="+id).then(function (response) {
                if(response.data.flag){
                    _this.getUserListConn(_this.id);
                }else {
                    alert(response.data.msg);
                }
                location.reload();
            });
        }
    }
});
vm.getUserListConn();
