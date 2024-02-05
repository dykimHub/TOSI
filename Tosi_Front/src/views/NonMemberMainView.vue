<template class="nonMemberContainer">
    <img src="@/assets/logo.png" class="logo" alt="Logo"/>
    <div>
        <div>
            <div class="sign">
                <button>회원가입</button>
                <button>로그인</button>
            </div>
            <div class="toMenus">                   
                <img class="icon" src="@/assets/talelist.png" />
                <img class="icon" src="@/assets/favorite.png" />
                <img class="icon" src="@/assets/custom.png" />
                <p>토씨의 특별한 기능에 대해서 알아보려면 누르세요</p>
            </div>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>

.logo {
    margin-top: 70px;
    width: 400px;
}

.icon {
    width: 100px;
    margin: 50px 80px 20px 80px;
}

.sign {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.nonMemberContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
}

button {
    margin: 10px;
}

p {
    text-shadow: -1px -1px 2px white, 1px -1px 2px white, -1px 1px 2px white, 1px 1px 2px white;
}
</style>