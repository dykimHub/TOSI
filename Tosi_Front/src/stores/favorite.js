import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router'
import axios from 'axios'

const REST_TOSI_API = `http://localhost:8080/favorites`

export const useFavoriteStore = defineStore('favorite', () => {
  //내가 만든 동화 목록
  const favoriteList = ref([])
  const getFavoriteList = function () {
    axios.get(`${REST_TOSI_API}/${userId}`)
      .then((response) => {
        favoriteList.value = response.data
      })
  }

 //TODO 삭제 추가

  return { getFavoriteList }
})
