import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from "@/util/http-common";

export const useTaleStore = defineStore('tale', () => {
  //동화 목록
  const taleList = ref([])
  const getTaleList = async function () {
    await axios.get(`/tales`, { withCredentials: true })
      .then((response) => {
        console.log(response.data)
        taleList.value = response.data
      })
      .catch((error) => {
        console.error('Error fetching tales:', error)
      })
  }

  //동화 검색
  const searchList = ref([])
  const searchTaleByTitle = function (title) {
    axios.get(`/search?title=${title}`, { withCredentials: true })
      .then((response) => {
        searchList.value = response.data
    }).catch((error) => {
      console.error('Error fetching search:', error)
    })
  }

  return {searchList, taleList, getTaleList, searchTaleByTitle }
})