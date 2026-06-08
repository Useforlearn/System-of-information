<template>
  <div>
    <div class="card p-4">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mt-4">
      <div class="card p-4">
        <div id="bar" style="height: 400px"></div>
      </div>

      <div class="card p-4">
        <div id="pie" style="height: 400px"></div>
      </div>

      <div class="card p-4">
        <div id="bar1" style="height: 400px"></div>
      </div>
    </div>

    <div class="mt-4">
      <div class="card p-4">
        <div class="mb-4 text-xl font-bold">公告列表</div>
        <el-timeline reverse>
          <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
            <el-popover
                placement="right"
                width="200"
                trigger="hover"
                :content="item.content">
              <span slot="reference">{{ item.title }}</span>
            </el-popover>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

const barOption = {
  title: {
    text: '各类活动报名数量统计',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: [],
    axisLabel: {
      interval: 0,
      rotate: -45,
      margin: 10
    }
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'bar',
      itemStyle: {
        normal: {
          color: function(params) {
            let colorList = ['#ffaa2e','#32C5E9','#fa4c4c','#08b448','#FFDB5C','#ff9f7f','#fb7293','#E062AE','#E690D1','#e7bcf3']
            return colorList[params.dataIndex];
          }
        }
      },
    }
  ]
}

const barOption1 = {
  title: {
    text: '体检报告详细统计',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['血压', '血糖', '血脂'],
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: ['血压高', '血压中', '血压正常', '血糖高', '血糖中', '血糖正常', '血脂高', '血脂中', '血脂正常'],
    axisLabel: {
      interval: 0,
      rotate: -45,
      margin: 10
    }
  },
  yAxis: {
    type: 'value',
    name: '百分比'
  },
  series: [
    {
      name: '百分比人数',
      type: 'bar',
      data: [60, 30, 10, 40, 35, 25, 55, 35, 10],
      itemStyle: {
        normal: {
          color: function(params) {
            // 根据索引设置不同颜色，高、中、正常分别使用红、黄、绿
            const colorList = [
              '#fa4c4c', '#ffaa2e', '#08b448', // 血压
              '#fa4c4c', '#ffaa2e', '#08b448', // 血糖
              '#fa4c4c', '#ffaa2e', '#08b448'  // 血脂
            ];
            return colorList[params.dataIndex];
          }
        }
      }
    }
  ]
}

const pieOption = {
  title: {
    text: '各类服务预约统计',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '服务类型',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      },
      label: {
        formatter: '{b|{b}: }{c}  {per|{d}%}',
        rich: {
          per: {
            color: '#eee',
            backgroundColor: '#334455',
            padding: [2, 4],
            borderRadius: 2
          }
        }
      }
    }
  ]
}

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  },
  mounted() {
    // 初始化三个图表
    const barChart = echarts.init(document.getElementById('bar'))
    const pieChart = echarts.init(document.getElementById('pie'))
    const barChart1 = echarts.init(document.getElementById('bar1'))

    // 加载活动报名数据
    this.$request.get('activitySign/selectCount').then(res => {
      barOption.xAxis.data = res.data?.map(v => v.name) || []
      barOption.series[0].data = res.data?.map(v => v.value) || []
      barChart.setOption(barOption)
    })

    // 加载预约统计数据
    this.$request.get('reserve/selectCount').then(res => {
      pieOption.series[0].data = res.data || []
      pieChart.setOption(pieOption)
    })

    // 设置体检报告图表
    barChart1.setOption(barOption1)

    // 监听窗口大小变化，重新调整图表
    window.addEventListener('resize', () => {
      barChart.resize()
      pieChart.resize()
      barChart1.resize()
    })
  },
  beforeDestroy() {
    // 销毁图表释放资源
    echarts.getInstanceByDom(document.getElementById('bar'))?.dispose()
    echarts.getInstanceByDom(document.getElementById('pie'))?.dispose()
    echarts.getInstanceByDom(document.getElementById('bar1'))?.dispose()
  }
}
</script>