<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
      <div class="box1"></div>
      <div class="box2">
        <h2>전체 책 보기</h2>
      </div>
      <div class="box3">
        <div class="searchContainer">
          <input v-model="searchQuery" type="text" />
          <button @click="searchTales">검색</button>
          <!-- <router-link :to="{ path: '/search', query: { title: searchQuery } }">검색</router-link> -->
        </div>
        <div class="selecSort">
          <label>정렬 기준</label>
          <select v-model="sortOption">
            <option value="title">이름순</option>
            <option value="likeCnt">인기순</option>
            <option value="random">랜덤</option>
          </select>
        </div>
      </div>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in currentPageBoardList" :key="tale.taleId">
        <div class="oneTale">
          <RouterLink :to="`/tales/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
          <br />
          <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
          <br />
          재생 시간: {{ tale.time }}분
        </div>
      </ul>
    </div>
    <div>
      <nav aria-label="Page navigation" style="padding: 15px;">
      <ul class="pagination d-flex justify-content-center flex-wrap pagination-rounded-flat pagination-success ">
        <li class="page-item">
          <a class="page-link" :class="{ disabled: currentPage <= 1 }" href="#" @click.prevent="currentPage--">&lt;</a>
        </li>
        <li :class="{ active: currentPage === page }" class="page-item" v-for="page in pageCount" :key="page">
          <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
            page
          }}</a>
        </li>
        <li class="page-item">
          <a class="page-link" :class="{ disabled: currentPage >= pageCount }" href="#"
            @click.prevent="currentPage++">&gt;</a>
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
const sortOption = ref("title");
const sortedTaleList = ref([]);
const router = useRouter();

const searchQuery = ref("");
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
  return Talestore.taleList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
}); //page

// const searchAndUpdateList = () => {
//   if (searchQuery.value.trim() !== "") {
//     sortedTaleList.value = Talestore.taleList.filter((tale) =>
//       tale.title.toLowerCase().includes(searchQuery.value.toLowerCase())
//     );
//   } else {
//     sortTaleList();
//   }
// };

const searchTales = () => {
  if (searchQuery.value.trim() !== "") {
    sortedTaleList.value = Talestore.taleList.filter((tale) =>
      tale.title.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  } else {
    sortTaleList();
  }

  router.push({ path: "/search", query: { title: searchQuery.value } });
};

onMounted(() => {
  Talestore.getTaleList();
  watch(() => Talestore.taleList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);

  // Extract search query from URL
  const route = router.currentRoute.value;
  if (route.query.title) {
    searchQuery.value = route.query.title.toString();
  }

  // Filter and display search results
  if (searchQuery.value.trim() !== "") {
    sortedTaleList.value = Talestore.taleList.filter((tale) =>
      tale.title.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  } else {
    sortTaleList();
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
  justify-content: space-around;
}

ul {
margin: 0;
padding: 0;
}

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  /* padding-top: 40px; */
  opacity: 0.95;
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
}

.selecSort {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.searchContainer {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.topOfTaleList {
  display: flex;
  flex-direction: row;
  /* justify-content: space-around; */
}

a {
  text-decoration: none;
  color: black;
}

.box1, .box2, .box3 {
  flex: 1;
}

input {
  width: 100px;
}

.box2 {
  display: flex;
  justify-content: center;
}

.box3{
  display: flex;
  flex-direction: column;
  /* justify-content: end; */
}

.pagination,
 .jsgrid .jsgrid-pager {
     display: flex;
     padding-left: 0;
     list-style: none;
     border-radius: 0.25rem
 }
 
 .page-link {
     color: black
 }

 .pagination.pagination-rounded-flat .page-item {
     margin: 0 .30rem
 }
 

 .pagination-success .page-item.active .page-link
  {
     background: #d8eef2;
 }
 
 .pagination.pagination-rounded-flat .page-item .page-link{
    border: none;
    border-radius: 50px;
}

</style>