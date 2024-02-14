<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
      <div class="title">전체 책 보기</div>
      <div class="searchNsort">
        <div class="searchContainer">
          <input v-model="searchQuery" type="text" @keyup.enter="searchTaleByTitle"/>
          <button @click="searchTaleByTitle">검색</button>
        </div>
        <div class="selecSort">
          <label>정렬 기준</label>
          <select v-model="sortOption">
            <option value="likeCnt">인기순</option>
            <option value="title">이름순</option>
            <option value="random">랜덤</option>
          </select>
        </div>
      </div>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in currentPageBoardList" :key="tale.taleId">
        <div class="oneTale">
          <RouterLink :to="`/tales/${tale.taleId}`"
            ><img class="thumbnail" :src="tale.thumbnail"
          /></RouterLink>
          <br />
          <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
          <br />
          재생 시간: {{ tale.time }}분
        </div>
      </ul>
    </div>
    <div>
      <nav aria-label="Page navigation" style="padding: 15px">
        <ul
          class="pagination d-flex justify-content-center flex-wrap pagination-rounded-flat pagination-success"
        >
          <li class="page-item">
            <a
              class="page-link"
              :class="{ disabled: currentPage <= 1 }"
              href="#"
              @click.prevent="currentPage--"
              >&lt;</a
            >
          </li>
          <li
            :class="{ active: currentPage === page }"
            class="page-item"
            v-for="page in pageCount"
            :key="page"
          >
            <a class="page-link" href="#" @click.prevent="clickPage(page)">{{ page }}</a>
          </li>
          <li class="page-item">
            <a
              class="page-link"
              :class="{ disabled: currentPage >= pageCount }"
              href="#"
              @click.prevent="currentPage++"
              >&gt;</a
            >
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { useTaleStore } from "@/stores/taleStore";
import { onMounted, ref, watch, computed } from "vue";
import { useRouter } from "vue-router";

const Talestore = useTaleStore();
const sortOption = ref("likeCnt");
const sortedTaleList = ref([]);
const router = useRouter();
const searchResults = ref([]);

const title = ref("");
const sortTaleList = () => {
  switch (sortOption.value) {
    case "title":
      sortedTaleList.value = Talestore.taleList.sort((a, b) => a.title.localeCompare(b.title));
      break;
    case "likeCnt":
      sortedTaleList.value = Talestore.taleList.sort((a, b) => b.likeCnt - a.likeCnt);
      break;
    case "random":
      sortedTaleList.value = Talestore.taleList.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

//page
const perPage = 9;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(Talestore.taleList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageBoardList = computed(() => {
  return Talestore.taleList.slice((currentPage.value - 1) * perPage, currentPage.value * perPage);
}); //page

const searchQuery = ref("");
const searchTaleByTitle = async () => {
  try {
    title.value = searchQuery.value;

    const response = await Talestore.searchTaleByTitle(title.value);
    console.log('Response:', response);
    searchResults.value = response.data;
    // 라우터로 이동
    router.push({ name: "search", query: { title: title.value } });
    
  } catch (error) {
    console.error('검색 오류:', error);
  }
};

onMounted(async() => {
  Talestore.getTaleList();
  watch(() => Talestore.taleList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);

  const route = router.currentRoute.value;
  
  if (route.query.title) {
    title.value = route.query.title.toString();
    let value = await Talestore.searchTaleByTitle(title.value);
    searchResults.value = value.data;
    console.log("TEST", JSON.stringify(searchResults.value));
  }

});
</script>

<style scoped>

.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  margin-left: 5vw;
}

.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #f1a8bc;
  font-size: 40px;
  margin: 30px 0px 0px 50px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  opacity: 0.95;
  padding: 40px 60px;
  border: 5px solid #cee8e8;
  width: 80vw;
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 3em;
}

.selecSort {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 0.5em;
}
.searchContainer {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.topOfTaleList {
  display: flex;
  justify-content: space-between;
  /* margin-left: 10%;
    margin-right: 10%; */
}
a {
  text-decoration: none;
  color: black;
}

select {
  margin-left: 1em;
}

input {
  width: 7em;
  height: 2em;
}

button {
  width: 3em;
  margin-left: 1em;
  height: 2em;
  /* padding: 10px 25px; */
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}


.pagination,
.jsgrid .jsgrid-pager {
  display: flex;
  padding-left: 0;
  list-style: none;
  border-radius: 0.25rem;
}

.page-link {
  color: black;
}

.pagination.pagination-rounded-flat .page-item {
  margin: 0 0.3rem;
}

.pagination-success .page-item.active .page-link {
  background: #d8eef2;
}

.pagination.pagination-rounded-flat .page-item .page-link {
  border: none;
  border-radius: 50px;
}
</style>
