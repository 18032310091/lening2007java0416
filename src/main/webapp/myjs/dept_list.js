var vm = new Vue({
    el:'#deptdiv',
    data:{
        deptlist:[],
        pageNum:1,
        pageSize:5,
        page:{},
        searchEntity: {}
    },
    methods:{
        getDeptListConn:function () {
            var _this = this;
            axios.post("../dept/getDeptListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.deptlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        }
    }
});
vm.getDeptListConn();