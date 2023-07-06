<template>
  <el-card shadow="hover" style="width: 100%">
    <template #header>
      <span>日历</span>
    </template>
    <el-calendar v-model="value"> </el-calendar>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      value: new Date(),
    };
  },
};
</script>

<style>
.el-calendar-table .el-calendar-day {
  width: 60px;
  height: 69px;
}
</style>