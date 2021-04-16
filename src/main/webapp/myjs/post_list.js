var vm = new Vue({
    el:'#postdiv',
    data:{
        postlist:[],
        searchEntity:{},
        pageNum:1,
        pageSize:5,
        page:{}
    },
    methods:{
        getPostListConn:function () {
            var _this = this;
            axios.post("../post/getPostListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.pageNum = response.data.currentPage;
                _this.postlist = response.data.list;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            })
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getPostListConn();
        }
    }
});
vm.getPostListConn();