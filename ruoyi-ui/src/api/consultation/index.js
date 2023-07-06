import request from '@/utils/request'
//获取就诊列表
export function selectList(data) {
	return request({
		url: '/system/appointment/selectList',
		method: 'get',
		params:data
	})
}
//获取就诊人信息
export function selectByid(id){
	return request({
		url:'/system/visit/selectByid?id='+id,
		method: 'get',
	})
}
//编辑就诊信息
export function insert(data) {
	return request({
	  url: '/system/appointment/insert',
	  method: 'POST',
	  data
	})
  }
  //病情单提交
  export function insertDiseaseTableEntity(data) {
	return request({
	  url: '/system/disease/insertDiseaseTableEntity',
	  method: 'POST',
	  data
	})
  }
  //病情单查询
  export function selectDiseaseTableEntityList(data) {
	return request({
		url: '/system/disease/selectDiseaseTableEntityList',
		method: 'get',
		params:data
	}) 
}
//处方签查询
export function selectLabelTableEntityList(data) {
	return request({
		url: '/system/label/selectLabelTableEntityList',
		method: 'get',
		params:data
	}) 
}
//病情单修改完结状态
export function updateDiseaseTableEntity(id){
	return request({
		url:'/system/disease/updateDiseaseTableEntity?id='+id,
		method: 'get',
	})
}
//新增就诊大厅信息
export function insertHallRecordEntityList(data){
	return request({
		url:'/system/hallRecord/insertHallRecordEntityList',
		method: 'post',
		data
	})
}
//查询统计
export function listCount(){
	return request({
		url:'/system/disease/listCount',
		method: 'get',
	})
}
//查询评价列表
export function selectScoreList(data){
	return request({
		url:'/system/score/selectScoreList',
		method: 'get',
		params:data
	})
}
//修改评价
export function updateScore(data){
	return request({
		url:'/system/score/updateScore',
		method: 'post',
		data
	})
}
//删除评价
export function deleteScore(id){
	return request({
		url:'/system/score/deleteScore/'+id,
		method: 'delete',
	})
}