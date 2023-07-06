<template>
  <el-card shadow="hover">
    <template #header>
      <span>医院就诊人数(近一年)</span>
    </template>
    <div id="container" style="width: 100%; height: 283px"></div>
  </el-card>
</template>

<script>
// import { feedBackStatByDept } from '@/api/feedback'
import {listCount} from "@/api/consultation"
import * as echarts from 'echarts'
export default {
  data() {
    return {
      option: {
        legend: {
          data: ['总数'],
        },
        tooltip: {},
        dataset: {
          source: [],
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [
          { name: '总数', type: 'bar' },
        ],
      },
    }
  },
  created() {
    this.listCount()
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      var dom = document.getElementById('container')
      var myChart = echarts.init(dom)
      myChart.setOption(this.option)
    },
    async listCount() {
      listCount().then((res) => {
        console.log(res)
        res.rows.forEach((item) => {
          console.log(item)
        })
        let arr = Object.values(res.rows)
        this.option.dataset.source = arr
        this.init()
      })
    },
  },
}
</script>

<style></style>
