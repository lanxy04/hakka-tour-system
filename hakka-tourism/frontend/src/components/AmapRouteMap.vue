<template>
  <div class="map-container">
    <div id="amap-container" class="map-wrapper"></div>
    <div class="map-legend" v-if="markers.length > 0">
      <el-card>
        <template #header>
          <span>图例</span>
        </template>
        <div class="legend-item" v-for="(route, index) in routes" :key="index">
          <div class="legend-color" :style="{ backgroundColor: colors[index % colors.length] }"></div>
          <span>第{{ route.day }}天 - {{ route.region }}</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'

export default {
  name: 'AmapRouteMap',
  props: {
    routeData: {
      type: String,
      default: ''
    },
    height: {
      type: String,
      default: '600px'
    }
  },
  setup(props) {
    const map = ref(null)
    const markers = ref([])
    const routes = ref([])
    const colors = ['#FF5722', '#2196F3', '#4CAF50', '#FF9800', '#9C27B0', '#00BCD4']

    const initMap = () => {
      if (!window.AMap) {
        console.error('高德地图API未加载')
        return
      }

      map.value = new AMap.Map('amap-container', {
        zoom: 11,
        center: [116.397428, 39.90923],
        viewMode: '2D',
        mapStyle: 'amap://styles/normal'
      })

      AMap.plugin(['AMap.ToolBar', 'AMap.Scale'], () => {
        map.value.addControl(new AMap.ToolBar())
        map.value.addControl(new AMap.Scale())
      })
    }

    const parseRouteData = (routeData) => {
      try {
        const data = JSON.parse(routeData)
        routes.value = data
        return data
      } catch (error) {
        console.error('解析路线数据失败', error)
        return []
      }
    }

    const geocodeAddress = async (address) => {
      return new Promise((resolve, reject) => {
        const geocoder = new AMap.Geocoder()
        geocoder.getLocation(address, (status, result) => {
          if (status === 'complete' && result.geocodes.length) {
            resolve({
              lng: result.geocodes[0].location.lng,
              lat: result.geocodes[0].location.lat
            })
          } else {
            resolve(null)
          }
        })
      })
    }

    const addMarkers = async () => {
      if (!map.value || !props.routeData) return

      const routeData = parseRouteData(props.routeData)
      if (routeData.length === 0) return

      const allMarkers = []
      const bounds = new AMap.Bounds()

      for (let routeIndex = 0; routeIndex < routeData.length; routeIndex++) {
        const route = routeData[routeIndex]
        const color = colors[routeIndex % colors.length]

        if (!route.resources || route.resources.length === 0) continue

        const path = []
        const resourceMarkers = []

        for (let i = 0; i < route.resources.length; i++) {
          const resource = route.resources[i]

          const location = await geocodeAddress(resource.address || resource.region)
          if (location) {
            path.push([location.lng, location.lat])
            bounds.extend([location.lng, location.lat])

            const marker = new AMap.Marker({
              position: [location.lng, location.lat],
              title: `${route.day}.${i + 1} ${resource.name}`,
              label: {
                content: `<div style="padding:2px 5px;background:${color};color:white;border-radius:3px;font-size:12px;">${route.day}.${i + 1}</div>`,
                direction: 'top'
              }
            })

            const infoWindow = new AMap.InfoWindow({
              content: `
                <div style="padding:10px;min-width:200px;">
                  <h4 style="margin:0 0 10px 0;">${resource.name}</h4>
                  <p style="margin:5px 0;color:#666;font-size:12px;">${resource.address || ''}</p>
                  <p style="margin:5px 0;color:#666;font-size:12px;">${resource.description || '暂无描述'}</p>
                  <p style="margin:10px 0 0 0;color:#f56c6c;font-weight:bold;">价格: ¥${resource.price}</p>
                </div>
              `,
              offset: new AMap.Pixel(0, -30)
            })

            marker.on('click', () => {
              infoWindow.open(map.value, marker.getPosition())
            })

            resourceMarkers.push(marker)
            allMarkers.push(marker)
          }
        }

        if (path.length >= 2) {
          const polyline = new AMap.Polyline({
            path: path,
            strokeColor: color,
            strokeWeight: 4,
            strokeOpacity: 0.8,
            strokeStyle: 'solid'
          })
          map.value.add(polyline)
        }

        resourceMarkers.forEach(marker => map.value.add(marker))
      }

      markers.value = allMarkers

      if (allMarkers.length > 0) {
        map.value.setBounds(bounds)
      }
    }

    const clearMarkers = () => {
      if (map.value) {
        map.value.clearMap()
        markers.value = []
      }
    }

    watch(() => props.routeData, (newVal) => {
      if (newVal) {
        clearMarkers()
        addMarkers()
      }
    }, { immediate: true })

    onMounted(() => {
      setTimeout(() => {
        initMap()
        if (props.routeData) {
          addMarkers()
        }
      }, 500)
    })

    onBeforeUnmount(() => {
      clearMarkers()
      if (map.value) {
        map.value.destroy()
        map.value = null
      }
    })

    return {
      markers,
      routes,
      colors
    }
  }
}
</script>

<style scoped>
.map-container {
  position: relative;
  width: 100%;
}

.map-wrapper {
  width: 100%;
  height: v-bind(height);
  border-radius: 4px;
}

.map-legend {
  position: absolute;
  top: 20px;
  right: 20px;
  width: 220px;
  z-index: 100;
}

.legend-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  font-size: 14px;
}

.legend-color {
  width: 20px;
  height: 20px;
  border-radius: 4px;
  margin-right: 8px;
}
</style>
