import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "@/util/http-common";
import router from "@/router";
import { useS3Store } from "@/stores/S3Store";

export const useFavoriteStore = defineStore("favorite", () => {
    //나의책장 - 일반동화
    const myFavoriteTalesList = ref([]);
    const getMyFavoriteTalesList = function () {
        axios.get(`/favorites`, { withCredentials: true }).then((response) => {
            console.log(response.data);
            myFavoriteTalesList.value = response.data;
            console.log("즐겨찾기목록:" + myFavoriteTalesList.value);
        });
    };

    //즐겨찾기 등록
    const postMyFavoriteTale = (favoriteInfo) => {
        axios
            .post(`/favorite`, favoriteInfo)
            .then((response) => {
                console.log(response.data);
                console.log(userInfo);
            })
            .catch((error) => {
                console.error("에러 발생:", error);
            });
    };

    //즐겨찾기 삭제
    const deleteMyFavoriteTale = function () {
        axios({
            method: "delete",
            url: `/favorites`,
            withCredentials: true,
        }).then(() => {});
    };

    return {
        getMyFavoriteTalesList,
        myFavoriteTalesList,
        postMyFavoriteTale,
        deleteMyFavoriteTale,
    };
});
