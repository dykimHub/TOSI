import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router'
import axios from 'axios'

export const useTaleStore = defineStore('tale', () => {
  //동화 목록
  const taleList = ref([])
  const getTaleList = function () {
    axios.get(`/tales`)
      .then((response) => {
        console.log(response.data)
        taleList.value = response.data
      })
      .catch((error) => {
        console.error('Error fetching tales:', error)
      })
  }

  const searchTaleByTitle = function (title) {
    axios.get(`http://localhost:8080/search`, {
      params: title
    })
      .then((response) => {
        taleList.value = response.data
    })
  }

  return { getTaleList, searchTaleByTitle }
})
