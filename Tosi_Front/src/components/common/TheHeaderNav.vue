<template>
    <header>
        <div></div>
        <div>
            <RouterLink to="/tosi">
                <img src="@/assets/logo.png" class="logo" alt="Logo"/>
            </RouterLink>   
        </div>
        <div>
            <div>
                <RouterLink to="/tosi">홈</RouterLink>
                <br>
                <a>로그아웃</a>
            </div>
            <div>검색</div>
        </div>
    </header>
</template>

<script setup>

</script>

<style scoped>
header {
    display: flex; 
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
}

img {
    width: 200px;
}

.logo {
    width: 300px;
    margin-top: 25px;
}


a {
  text-decoration: none;
  color: black;
}

</style>
