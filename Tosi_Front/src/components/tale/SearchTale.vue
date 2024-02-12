<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
      <div class="box1"></div>
      <div class="box2">
        <h2>검색 결과</h2>
      </div>
      <div class="box3">
        <div class="searchContainer">
          <input v-model="searchQuery" type="text" />
          <button @click="searchTaleByTitle">검색</button>
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
const router = useRouter();

//page
const perPage = 9;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(Talestore.searchList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageBoardList = computed(() => {
  return Talestore.searchList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
}); //page

const searchTaleByTitle = async () => {
  try {

    title.value = searchQuery.value;
    const response = await Talestore.searchTaleByTitle(title.value);
    Talestore.searchList = response.data;

    // 검색 결과를 업데이트한 후 결과 페이지로 이동
    router.push({ name: "searchTale", query: { title: title.value } });
  } catch (error) {
    console.error("Error fetching search:", error);
  }
};

onMounted(() => {
  Talestore.getTaleList();
  watch(() => Talestore.searchList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);

  // Extract search query from URL
  const route = router.currentRoute.value;
  if (route.query.title) {
    title.value = route.query.title.toString();
  }

});

const searchTale = () => {
  router.push({ name: 'searchTale', query: { title: searchQuery.value } });

};

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

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  padding-top: 40px;
  opacity: 0.95;
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