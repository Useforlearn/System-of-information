<template>
  <div style="width: 60%; margin: 10px auto">
    <div style="display: flex; grid-gap: 20px">
      <div style="flex: 1">
        <img :src="nurseHouse.img" alt="" style="width: 100%">
      </div>
      <div style="flex: 1">

        <div style=" margin: 20px 0; display: flex; align-items: center">
          <div style="font-size: 22px; font-weight: bold; flex: 1">{{ nurseHouse.name }}</div>
          <div style="color: #666; font-size: 13px">{{ nurseHouse.count }}浏览</div>
        </div>

        <div style="margin: 10px 0">
          <el-tag style="margin-right: 10px" v-for="item in JSON.parse(nurseHouse.tags || '[]')" :key="item">{{ item }}</el-tag>
        </div>
        <div style="margin: 10px 0; color: #ffa400; font-size: 18px; font-weight: bold">￥{{ nurseHouse.price }}</div>
        <div style="margin: 20px 0; color: #666">{{ nurseHouse.address }}</div>

        <div style="display: flex; margin: 10px 0;">
          <div style="width: 200px"><span style="color: #666">机构性质：</span>{{ nurseHouse.nature }}</div>
          <div style="flex: 1"><span style="color: #666">机构类型：</span>{{ nurseHouse.type }}</div>
        </div>
        <div style="display: flex; margin: 10px 0;">
          <div style="width: 200px"><span style="color: #666">成立时间：</span>{{ nurseHouse.date }}</div>
          <div style="flex: 1"><span style="color: #666">床位数量：</span>{{ nurseHouse.beds }}</div>
        </div>
        <div style="display: flex; margin: 10px 0;">
          <div style="width: 200px"><span style="color: #666">机构面积：</span>{{ nurseHouse.area }}</div>
        </div>

        <div style="margin: 20px 0">
          电话：<span style="font-size: 20px; font-weight: bold; color: orangered">{{ nurseHouse.phone }}</span>
        </div>

      </div>
    </div>

    <div style="margin: 30px 0">
      <div style="font-weight: bold; font-size: 22px; text-align: center; margin-bottom: 30px">详细信息</div>

      <div style="margin: 20px 0">
        <div id="container" style="height: 400px; margin-bottom: 30px"></div>
      </div>

      <div style="line-height: 26px" class="w-e-text">
        <div v-html="nurseHouse.content"></div>
      </div>
    </div>
  </div>
</template>

<script>
import L from 'leaflet'
export default {
  name: "HouseDetail",
  data() {
    return {
      id: this.$route.query.id,
      nurseHouse: {},
      map: null
    }
  },
  created() {
    this.$request.put('nurseHouse/updateCount/' + this.id).then(res => {
      this.load()
    })
  },
  methods: {
    load() {
      this.$request.get('/nurseHouse/selectById/' + this.id).then(res => {
        this.nurseHouse = res.data || {}
        const lat = Number(this.nurseHouse.latitude)
        const lng = Number(this.nurseHouse.longitude)
        // Leaflet 初始化地图
        this.map = L.map('container').setView([lat, lng], 16)
        // 加载开源底图
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; OpenStreetMap contributors'
        }).addTo(this.map)
        // 创建标记点
        const marker = L.marker([lat, lng]).addTo(this.map)
        // 弹窗内容
        const popContent = `<b>地址</b>：${this.nurseHouse.address}<br/>`
        marker.bindPopup(popContent).openPopup()
      })
    }
  }
}
</script>

<style scoped>

</style>