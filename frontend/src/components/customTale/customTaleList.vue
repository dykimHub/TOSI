<template>
  <div class="talelistContainer">
    <div>
      <div class="title">친구들의 동화 보기</div>
    </div>
    <div class="taleContainer">
      <ul
        v-for="customTale in currentPageBoardList"
        :key="customTale.customTaleId"
      >
        <div class="oneTale">
          <RouterLink :to="`/customtale/${customTale.customTaleId}`">
            <img class="thumbnail" :src="customTale.thumbnail"
          /></RouterLink>
          <br />
          <RouterLink :to="`/customtale/${customTale.customTaleId}`" class="titleOfTale">{{
            customTale.title
          }}</RouterLink>
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
            <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
              page
            }}</a>
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
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
const customTaleStore = useCustomTaleStore();

//page
const perPage = 9;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(customTaleStore.customTalesList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageBoardList = computed(() => {
  return customTaleStore.customTalesList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
}); //page

onMounted(() => {
  customTaleStore.getCustomTalesList(true);
});
</script>

<style scoped>
.titleOfTale {
  font-size: larger;
}
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
  box-shadow: inset 0 -20px 0 #d3e4ff;
  font-size: 40px;
  margin: 30px 0px 30px 50px;
  line-height: 1;
  text-align: left;
}
.talelistContainer {
  background-color: white;
  border-radius: 50px;
  margin-top: 35px;
  opacity: 0.95;
  padding: 40px 60px;
  border: 5px solid #cee8e8;
  width: 80vw;
  margin-bottom: 30px;
}
.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 2em;
}

.selecSort {
  display: flex;
  flex-direction: column;
  margin: 1em;
}

/* .topOfTaleList{
  display: flex;
  flex-direction: row;
  justify-content: space-around;
} */

a {
  text-decoration: none !important;
  color: black !important;
}

.flex {
  -webkit-box-flex: 1;
  -ms-flex: 1 1 auto;
  flex: 1 1 auto;
}

/* page */
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
