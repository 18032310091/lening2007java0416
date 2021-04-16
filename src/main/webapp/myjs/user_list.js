var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        pageNum:1,
        pageSize:5,
        page:{},
        searchEntity: {}
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
        }
    }
});
vm.getUserListConn();
